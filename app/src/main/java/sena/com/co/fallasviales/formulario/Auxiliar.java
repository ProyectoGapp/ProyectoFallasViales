package sena.com.co.fallasviales.formulario;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.xml.validation.Validator;

import sena.com.co.fallasviales.Entidades.TareAsincrona;
import sena.com.co.fallasviales.Entidades.TareaSincronaSplas;
import sena.com.co.fallasviales.Entidades.Usuario;
import sena.com.co.fallasviales.R;
import sena.com.co.fallasviales.commons.AuxiliarSugar;
import sena.com.co.fallasviales.commons.ConfiguracionGlobal;
import sena.com.co.fallasviales.commons.DialogAlertaFoto;
import sena.com.co.fallasviales.commons.DialogoAlerta;
import sena.com.co.fallasviales.commons.Utilidad;

/**
 * Created by Wilmer Fernandez on 15/02/2016.
 */
public class Auxiliar implements View.OnClickListener {
    static final Logger LOG = Logger.getLogger(Auxiliar.class
            .getSimpleName());
    private final FormularioActivity datos_activity;
    private Usuario usuario;
    private String tipo;
    private String msjVacio;
    private boolean hayTipos;
    private boolean subirFoto;
    private Cloudinary cloudinary;
    private TareAsincrona tareAsincrona;
    private List<Usuario> usuarios;
    private String corrdenadas;


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
        int seleccion = view.getId();
        try {
            switch (seleccion) {
                case (R.id.btnEnviar):
                    datos_activity.setValidar(false);
                    datos_activity.getValidator().validate();
                    if (!datos_activity.isValidar()) {
                        if (esNombreValido(datos_activity.getNombre().getText().toString(), datos_activity.getUsername()) &&
                                esNombreValido(datos_activity.getApellidos().getText().toString(), datos_activity.getTxtInapellidos())) {
                            guardar();
                        }


                    }
                    break;
                case (R.id.btnTomarFoto):
                    LOG.info("[whilfer]**********Tomar foto*************");
                    tomarFoto();
                    break;
            }
        } catch (Exception e) {
            LOG.info("[whilfer]>>>>>>>>>>>>Error al seleccionar " + e.getLocalizedMessage());
        }

    }

    /**
     * nombre invalido
     *
     * @param nombre
     * @return
     */
    private boolean esNombreValido(String nombre, TextInputLayout inputLayout) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron.matcher(nombre).matches() || nombre.length() > 30) {
            inputLayout.setError("Nombre inválido");
            return false;
        } else {
            datos_activity.getUsername().setError(null);
        }

        return true;
    }

    /**
     * funcion crear usuario
     *
     * @return
     */
    private void crearUsuario(String id) {
        LOG.info("[whilfer]**********Crear Usuario*************");
        Usuario usuario = new Usuario();
        usuario.setCordenadas(getCorrdenadas());
        usuario.setUbicacion("Popayán");
        usuario.setTipo(getTipo());
        usuario.setIdentificador(id);
        usuario.setUrlFoto(tareAsincrona.getUrl());
        usuario.setNombre(datos_activity.getNombre().getText().toString());
        usuario.setApellidos(datos_activity.getApellidos().getText().toString());
        usuario.setCorreoElectronico(datos_activity.getCorreoElectronico().getText().toString());
        setUsuario(usuario);
    }

    /**
     * metodo tomar foto
     */
    private void tomarFoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        datos_activity.startActivityForResult(intent, 1, null);
    }


    /**
     * subirFoto
     */
    public void subirFoto() {
        tareAsincrona = new TareAsincrona(datos_activity);
        tareAsincrona.execute(datos_activity.getBitmap(), getCloudinary());
        setSubirFoto(true);
    }


    /**
     * Metodo Guardar
     */
    private void guardar() {
        //inicilizo variable
        setUsuario(new Usuario());
        //tipo de daño
        seleccionarTipo();
        if (!datos_activity.coordenadas().toString().isEmpty()) {
            setCorrdenadas(datos_activity.coordenadas().toString());
        }
        if (isSubirFoto()) {
            if (isHayTipos()) {
                String id = Utilidad.idUsuario(ConfiguracionGlobal.USER).toString();
                crearUsuario(id);
                datos_activity.getFirebase().child(ConfiguracionGlobal.USUARIOS).child(id).setValue(getUsuario());
                lanzaMensaje(String.valueOf(datos_activity.getApplicationContext().getText(R.string.msjResExitoso)));
                limpiar();
            }

        } else {
            FragmentManager fragmentManager = datos_activity.getSupportFragmentManager();
            DialogAlertaFoto dialogAlertaFoto = new DialogAlertaFoto();
            dialogAlertaFoto.show(fragmentManager, "tagAlerta");
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
        //valor por defecto sppiner
        datos_activity.getTipos().setAdapter(datos_activity.getTiposDanos());
        setSubirFoto(false);
    }

    /**
     * selecciona tipo daño
     */
    private void seleccionarTipo() {
        if (isHayTipos()) {
            setTipo(datos_activity.getTipos().getSelectedItem().toString());
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
                LOG.info("[whilfer]**********dataSnapshot*************" + dataSnapshot);
                final GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {
                };
                final List<String> list = dataSnapshot.getValue(t);
                if (!list.isEmpty()) {
                    //verifica si se carga el sppiner con los tipos de la base de datos
                    setHayTipos(true);

                    //se recorre lista de items
                    for (String items : list) {
                        LOG.info("[whilfer]**********c::::*************" + items);
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
        //valor por defecto sppiner
        datos_activity.getTipos().setAdapter(datos_activity.getTiposDanos());

    }

    /**
     * Configuracion
     * Coneccion con cloudinary
     */
    public void configuracionClaudinary() {
        Map config = new HashMap();
        config.put(ConfiguracionGlobal.CLOUDINARY_CONNECTION_NAME, ConfiguracionGlobal.CLOUDINARY_NOMBRE);
        config.put(ConfiguracionGlobal.CLOUDINARY_API_KEY, ConfiguracionGlobal.CLOUDINARY_API_KEY_SECRET);
        config.put(ConfiguracionGlobal.CLOUDINARY_API_SECRET_N, ConfiguracionGlobal.CLOUDINARY_API_SECRET);
        cloudinary = new Cloudinary(config);
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

    public boolean isHayTipos() {
        return hayTipos;
    }

    public void setHayTipos(boolean hayTipos) {
        this.hayTipos = hayTipos;
    }

    public Cloudinary getCloudinary() {
        return cloudinary;
    }

    public void setCloudinary(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public boolean isSubirFoto() {
        return subirFoto;
    }

    public String getCorrdenadas() {
        return corrdenadas;
    }

    public void setCorrdenadas(String corrdenadas) {
        this.corrdenadas = corrdenadas;
    }

    public void setSubirFoto(boolean subirFoto) {
        this.subirFoto = subirFoto;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
