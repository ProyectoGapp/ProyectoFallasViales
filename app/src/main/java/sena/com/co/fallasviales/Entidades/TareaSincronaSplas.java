package sena.com.co.fallasviales.Entidades;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.view.View;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import sena.com.co.fallasviales.commons.ConfiguracionGlobal;
import sena.com.co.fallasviales.commons.AuxiliarSugar;
import sena.com.co.fallasviales.commons.Utilidad;
import sena.com.co.fallasviales.formulario.Splass_Activity;

/**
 * Created by Wilmer Fernandez on 28/02/2016.
 */
public class TareaSincronaSplas extends AsyncTask {
    static final Logger LOG = Logger.getLogger(TareaSincronaSplas.class
            .getSimpleName());
    private Splass_Activity splass_activity;
    private List<Usuario> usuariosFirebase;
    private List<Usuario> usuariosSqlLite;
    private static List<Usuario> usuariosNuevos = new ArrayList<>();
    private static List<Usuario> usuariosEliminar = new ArrayList<>();
    private boolean bandera;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        splass_activity.getProgressBar().setVisibility(View.VISIBLE);
    }

    public TareaSincronaSplas(Splass_Activity splass_activity) {
        this.splass_activity=splass_activity;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        splass_activity.getProgressBar().setVisibility(View.INVISIBLE);

    }

    @Override
    protected Object doInBackground(Object[] params) {
        setUsuariosFirebase(new ArrayList<Usuario>());
        Firebase firebase = (Firebase) params[ConfiguracionGlobal.CERO];
        final ValueEventListener valueEventListener = firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LOG.info("[whilfer]********** numero usuarios *************" + dataSnapshot.getChildrenCount());
                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Usuario usuario = snapshot.getValue(Usuario.class);
                        getUsuariosFirebase().add(usuario);
                        LOG.info("[whilfer]********** usuario *************" + usuario.getNombre() + usuario.getApellidos());
                    }
                }
                //comparar listas
                compararListas();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return null;
    }


    /**
     * Guardar en base local
     *
     * @param usuario1
     */
    public void guardarSqlLite(Usuario usuario1) {
        LOG.info("[whilfer]********** guardando en sql lite *************");
        Usuario.save(usuario1);
    }

    /**
     * Obtener datos de la base local
     */
    public void ObtenerUsuariosLocales() {
        LOG.info("[whilfer]********** ObtenerUsuariosLocales *************");
        setUsuariosSqlLite(new ArrayList<Usuario>());
        List<Usuario> usuarios = Usuario.listAll(Usuario.class);
        if (!usuarios.isEmpty()) {
            for (Usuario usuario : usuarios) {
                getUsuariosSqlLite().add(usuario);
            }
            LOG.info("[whilfer]*****SQL LITE***" + getUsuariosSqlLite().size());
        } else {
            LOG.info("[whilfer]***Lista vacia*****");
        }

    }
    /**
     * compararListas
     */
    public void compararListas() {
        //obtener datos de base local
        ObtenerUsuariosLocales();
        //usuario temporal
        Usuario usuarioTem;
        LOG.info(">>>>>Comparando listas>>>>>>> ");
        LOG.info(" lista firebase " + getUsuariosFirebase().size());
        LOG.info(" lista sqlLite " + getUsuariosSqlLite().size());
        if (getUsuariosSqlLite().isEmpty()) {
            LOG.info(" <<<<<<<<<<<<<<< Comparando listas viejos ");
            for (Usuario usuario : getUsuariosFirebase()) {
                guardarSqlLite(usuario);
            }
        } else if (getUsuariosSqlLite().size() > getUsuariosFirebase().size()) {
            LOG.info(" Entro a actualizar base local con respecto a firebase");
            //eleiminar datos que no estan en firebase
            verificarListas(getUsuariosSqlLite(), getUsuariosFirebase());
            //Inicializa lista firebase
            setUsuariosFirebase(new ArrayList<Usuario>());
        } else if (!compararListas(getUsuariosFirebase(), getUsuariosSqlLite())) {
            LOG.info(" Comparando listas nuevos ");
            for (Usuario usuarioUno : getUsuariosFirebase()) {
                LOG.info("lista uno <<<<<<<<<<<<<<<<<<< :" + usuarioUno.getIdentificador());
                for (Usuario usuarioDos : getUsuariosSqlLite()) {
                    LOG.info("lista Dos <<<<<<<<<<<<<<<<<<< :" + usuarioDos.getIdentificador());
                    if (usuarioUno.getIdentificador().equals(usuarioDos.getIdentificador())) {
                        usuarioUno.setBandera(true);
                    }
                }
            }
            for (Usuario usuario : getUsuariosFirebase()) {
                if (!usuario.isBandera()) {
                    usuariosNuevos.add(usuario);
                }
            }
            setUsuariosFirebase(new ArrayList<Usuario>());
            LOG.info("list nuevossss <<<<<<<<<<<<<<<<<< :" + usuariosNuevos.size()+"UsuariosFirebase"+getUsuariosFirebase().size());
            for (Usuario usuario : usuariosNuevos) {
                LOG.info("Usuario nuevo <<<<<<<<<<<<<<<<<< :" + usuario.isBandera() + " " + usuario.getNombre() + usuario.getIdentificador());
                //se manda a persisttir
                Usuario.save(usuario);
            }

        }else{
            setUsuariosFirebase(new ArrayList<Usuario>());
        }
    }

    /**
     * verifica listas en firrebase
     *
     * @param usuariosSqlLite
     * @param usuariosFirebase
     */
    private void verificarListas(List<Usuario> usuariosSqlLite, List<Usuario> usuariosFirebase) {
        for (Usuario usuarioUno : usuariosSqlLite) {
            LOG.info("lista sqlLite <<<<<<<<<<<<<<<<<<< :" + usuarioUno.getIdentificador());
            for (Usuario usuarioDos : usuariosFirebase) {
                LOG.info("lista firebase <<<<<<<<<<<<<<<<<<< :" + usuarioDos.getIdentificador());
                if (usuarioUno.getIdentificador().equals(usuarioDos.getIdentificador())) {
                    usuarioUno.setBandera(true);
                }
            }
        }
        for (Usuario usuario : usuariosSqlLite) {
            if (!usuario.isBandera()) {
                //se elimina objeto
                usuariosEliminar.add(usuario);
            }
        }
        LOG.info("list a eliminar  <<<<<<<<<<<<<<<<<< :" + usuariosEliminar.size());

        for (Usuario usuario : usuariosEliminar) {
            LOG.info("Usuario a eliminar <<<<<<<<<<<<<<<<<< :" + usuario.isBandera() + " " + usuario.getNombre() + usuario.getIdentificador());
            Usuario.delete(usuario);
        }
        setUsuariosFirebase(new ArrayList<Usuario>());
    }

    /**
     * convertir lista a set
     *
     * @param lista
     * @param <E>
     * @return
     */
    public static <E> Set<E> convertirListASet(List<E> lista) {
        Set<E> listaSet = null;
        try {
            listaSet = new HashSet<E>(lista);
        } catch (Exception e) {
            listaSet = null;
        }
        return listaSet;
    }


    /**
     * comparar listas
     *
     * @param list1
     * @param list2
     * @return
     */
    public static boolean compararListas(List<Usuario> list1, List<Usuario> list2) {
        if (list1 == null && list2 == null) return true;
        if (list1 != null && list2 != null) {
            if (list1.size() == list2.size()) {
                for (Usuario usuarios : list1) {
                    boolean isEqual = false;
                    for (Usuario usuarios2 : list2) {
                        if (usuarios.equals(usuarios2)) {
                            isEqual = true;
                            break;
                        }
                    }
                    if (!isEqual) return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    public Splass_Activity getSplass_activity() {
        return splass_activity;
    }

    public void setSplass_activity(Splass_Activity splass_activity) {
        this.splass_activity = splass_activity;
    }

    public List<Usuario> getUsuariosFirebase() {
        return usuariosFirebase;
    }

    public void setUsuariosFirebase(List<Usuario> usuariosFirebase) {
        this.usuariosFirebase = usuariosFirebase;
    }

    public List<Usuario> getUsuariosSqlLite() {
        return usuariosSqlLite;
    }

    public void setUsuariosSqlLite(List<Usuario> usuariosSqlLite) {
        this.usuariosSqlLite = usuariosSqlLite;
    }



}
