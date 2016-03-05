package sena.com.co.fallasviales.Entidades;

import android.os.AsyncTask;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.logging.Logger;
import sena.com.co.fallasviales.commons.ConfiguracionGlobal;
import sena.com.co.fallasviales.commons.AuxiliarSugar;
import sena.com.co.fallasviales.formulario.Splass_Activity;

/**
 * Created by Wilmer Fernandez on 28/02/2016.
 */
public class TareaSincronaSplas extends AsyncTask {
    static final Logger LOG = Logger.getLogger(TareaSincronaSplas.class
            .getSimpleName());
    private Splass_Activity splass_activity;

    public TareaSincronaSplas(Splass_Activity splass_activity) {
        this.splass_activity=splass_activity;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        splass_activity.onRetainNonConfigurationInstance ();
        this.cancel(true);
    }

    @Override
    protected Object doInBackground(Object[] params) {
        Firebase firebase = (Firebase) params[ConfiguracionGlobal.CERO];
        final ValueEventListener valueEventListener = firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LOG.info("[whilfer]********** numero usuarios *************" + dataSnapshot.getChildrenCount());
                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Usuario usuario = new Usuario();
                        usuario = snapshot.getValue(Usuario.class);
                       // AuxiliarSugar.guardarSqlLite(usuario);
                        LOG.info("[whilfer]********** usuario *************" + usuario.getNombre() + usuario.getApellidos());
                    }
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return null;
    }


}
