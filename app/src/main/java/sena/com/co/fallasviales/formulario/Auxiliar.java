package sena.com.co.fallasviales.formulario;

import android.view.View;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import sena.com.co.fallasviales.Entidades.Usuario;
import sena.com.co.fallasviales.R;
import sena.com.co.fallasviales.commons.ConfiguracionGlobal;
import sena.com.co.fallasviales.commons.Utilidad;

/**
 * Created by Wilmer Fernandez on 15/02/2016.
 */
public class Auxiliar implements View.OnClickListener {
    static final Logger LOG = Logger.getLogger(Auxiliar.class
            .getSimpleName());
    private final  FormularioActivity datos_activity;
    private Usuario usuario;
    private String tipo;
    private String msjVacio;

    /**
     * constructor
     *
     * @param datos_activity
     */
    public Auxiliar(FormularioActivity datos_activity) {
        this.datos_activity = datos_activity;
    }

    @Override
    public void onClick(View view) {
        guardar();
    }

    /**
     * funcion crear usuario
     *
     * @return
     */
    private void crearUsuario() {
        LOG.info("[whilfer]**********Crear Usuario*************");
        seleccionarTipo();
        Usuario usuario = new Usuario();
        usuario.setCordenadas("Indefinido aun");
        usuario.setUbicacion("Popayán");
        usuario.setTipo(getTipo());
        usuario.setUrlFoto("Por definir");
        usuario.setNombre(datos_activity.getNombre().getText().toString());
        usuario.setApellidos(datos_activity.getApellidos().getText().toString());
        usuario.setCorreoElectronico(datos_activity.getCorreoElectronico().getText().toString());
        setUsuario(usuario);
    }

    /**
     * valida campos del formulario no esten vacios
     *
     * @return
     */
    private boolean validacionVacio() {
        boolean c1 = false;
        boolean c2 = false;
        boolean c3 = false;
        final StringBuilder msjVacios = new StringBuilder();
        msjVacios.append(datos_activity.getApplicationContext().getText(R.string.msjCampos));
        if (Utilidad.cadenaVacia(datos_activity.getNombre().getText().toString())) {
            c1 = true;
        } else {
            msjVacios.append(ConfiguracionGlobal.SALTO_DE_LINEA);
            msjVacios.append(datos_activity.getApplicationContext().getText(R.string.nombres));
        }
        if (Utilidad.cadenaVacia(datos_activity.getApellidos().getText().toString())) {
            c2 = true;
        } else {
            msjVacios.append(ConfiguracionGlobal.SALTO_DE_LINEA);
            msjVacios.append(datos_activity.getApplicationContext().getText(R.string.apellidos));
        }
        if (Utilidad.cadenaVacia(datos_activity.getCorreoElectronico().getText().toString())) {
            c3 = true;
        } else {
            msjVacios.append(ConfiguracionGlobal.SALTO_DE_LINEA);
            msjVacios.append(datos_activity.getApplicationContext().getText(R.string.email));
        }

        if (c1 && c2 && c3) {
            return true;
        } else {
            msjVacio = msjVacios.toString();
        }
        return false;
    }
    /**
     * Metodo Guardar
     */
    private void guardar() {
        LOG.info("[whilfer]**********Guardar*************");
        //inicilizo variable
        setUsuario(new Usuario());
        if (!validacionVacio()) {
            lanzaMensaje(getMsjVacio());
        } else if (!Utilidad.verificarCorreo(datos_activity.getCorreoElectronico().getText().toString())) {
            lanzaMensaje(String.valueOf(datos_activity.getApplicationContext().getText(R.string.msjErrorEmail)));
        } else {
            crearUsuario();
            datos_activity.getFirebase().child(ConfiguracionGlobal.USUARIOS).child(getUsuario().getNombre()).setValue(getUsuario());
            lanzaMensaje(String.valueOf(datos_activity.getApplicationContext().getText(R.string.msjResExitoso)));
            LOG.info("[whilfer]********** Usuario guardado*************");
            limpiar();
        }
    }

    /**
     * limpiar
     */
    private void limpiar() {
        setUsuario(new Usuario());
        datos_activity.getNombre().setText("");
        datos_activity.getNombre().invalidate();
        datos_activity.getApellidos().setText("");
        datos_activity.getApellidos().invalidate();
        datos_activity.getCorreoElectronico().setText("");
        datos_activity.getCorreoElectronico().invalidate();
    }

    /**
     * selecciona tipo daño
     */
    private void seleccionarTipo() {

        if(datos_activity.getTiposDanos()!=null) {
            setTipo(datos_activity.getTipos().getSelectedItem().toString());
            LOG.info("[whilfer]**********dato seleccionado*************" + getTipo());
        }else{
            lanzaMensaje(String.valueOf(datos_activity.getApplicationContext().getText(R.string.msjResNoExitoso)));

        }
    }

    /**
     * cargar tipos de daños
     */
    public void cargarTipos() {
        LOG.info("[whilfer]**********Cargando tipos*************");
        final ValueEventListener valueEventListener = datos_activity.getFirebaseTipos().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LOG.info("[whilfer]********* valores dataSnapshot **************" + dataSnapshot);
                final GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {
                };
                final List<String> list = dataSnapshot.getValue(t);
                LOG.info("[whilfer]********* tamaño lista **************" + list.size());
                if (!list.isEmpty()) {
                    for (String items : list) {
                        datos_activity.getTiposDanos().add(items);
                        datos_activity.getTipos().setAdapter(datos_activity.getTiposDanos());
                    }
                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // Toast.makeText(activity,getResources().getText(R.string.Error),Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * Metodo para lanzar mensajes
     *
     * @param mensaje
     */
    private final void lanzaMensaje(String mensaje) {
        final Toast toast =
                Toast.makeText(datos_activity.getApplicationContext(),
                        mensaje, Toast.LENGTH_SHORT);
        toast.show();
    }

    //gettrrs sertters
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMsjVacio() {
        return msjVacio;
    }

    public void setMsjVacio(String msjVacio) {
        this.msjVacio = msjVacio;
    }
}
