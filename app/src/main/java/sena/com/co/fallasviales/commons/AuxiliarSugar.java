package sena.com.co.fallasviales.commons;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.LoggingMXBean;

import sena.com.co.fallasviales.Entidades.Usuario;
import sena.com.co.fallasviales.formulario.Splass_Activity;

/**
 * Created by Wilmer Fernandez on 29/02/2016.
 */
public class AuxiliarSugar {
    static final Logger LOG = Logger.getLogger(AuxiliarSugar.class
            .getSimpleName());
    private Splass_Activity splass_activity;
    private List<Usuario> usuariosFirebase;
    private List<Usuario> usuariosSqlLite;
    public static List<Usuario> usuariosSqlLite_recycler;
    private static List<Usuario> usuariosNuevos = new ArrayList<>();

    private boolean bandera;

    public AuxiliarSugar(Splass_Activity splass_activity) {
        this.splass_activity = splass_activity;
    }

    /*public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) splass_activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (Utilidad.validaNulos(netInfo) && netInfo.isConnected()) {
            return true;
        }
        return false;
    }*/

    /**
     * Traer datos de la nube
     */
    public void ejecutarFirebase() {
        setUsuariosFirebase(new ArrayList<Usuario>());
        LOG.info("[whilfer]********** gfirebase*************");
        final ValueEventListener valueEventListener = splass_activity.getFirebaseTipos().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LOG.info("[whilfer]********** numero usuarios *************" + dataSnapshot.getChildrenCount());
                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String padre = snapshot.getKey().toString();
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
        usuariosSqlLite_recycler = Usuario.listAll(Usuario.class);
        if (!usuarios.isEmpty()) {
            for (Usuario usuario : usuarios) {
                getUsuariosSqlLite().add(usuario);
            }
            LOG.info("[whilfer]*****SQL LITE***" + getUsuariosSqlLite().size());
        } else {
            LOG.info("[whilfer]***Lista vacia*****");
        }

    }


    public void borrar() {
        Usuario.deleteAll(Usuario.class);
    }

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
            LOG.info("list nuevossss <<<<<<<<<<<<<<<<<< :" + usuariosNuevos.size());

            for (Usuario usuario : usuariosNuevos) {
                Usuario.save(usuario);
                LOG.info("nuevossss <<<<<<<<<<<<<<<<<< :" + usuario.isBandera() + " " + usuario.getNombre() + usuario.getIdentificador());
            }
        }
        LOG.info("[whilfer]********** comparar *************" + compararListas(getUsuariosFirebase(), getUsuariosSqlLite()));
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

    public void compared() {
        LOG.info("COMPARED");
        Collection listFirebase = new ArrayList(getUsuariosFirebase());
        LOG.info(" list uno " + listFirebase.size());
        Collection listSql = new ArrayList(getUsuariosSqlLite());
        LOG.info(" list dos " + listSql.size());
        convertirListASet(getUsuariosFirebase());

        Collection<Usuario> similar = new HashSet<Usuario>();
        similar.addAll(listFirebase);
        Collection<Usuario> different = new HashSet<Usuario>();
        different.addAll(listFirebase);
        different.addAll(listSql);

        similar.retainAll(listSql);

        different.removeAll(similar);
        System.out.printf("One:%s%nTwo:%s%nSimilar:%s%nDifferent:%s%n", listFirebase.size(), listSql.size(), similar.size(), different.size());
        for (Usuario collection : different) {
            LOG.info(" DIFERETE " + collection.getNombre());

        }


        for (Usuario collection : similar) {
            LOG.info("SIMILAR" + collection.getNombre());

        }


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
