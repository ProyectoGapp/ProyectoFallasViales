package sena.com.co.fallasviales.formulario;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.client.Firebase;
import com.orm.SugarContext;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import sena.com.co.fallasviales.Entidades.TareAsincrona;
import sena.com.co.fallasviales.Entidades.TareaSincronaSplas;
import sena.com.co.fallasviales.PrincipalActivity;
import sena.com.co.fallasviales.R;
import sena.com.co.fallasviales.commons.AuxiliarSugar;
import sena.com.co.fallasviales.commons.ConfiguracionGlobal;
import sena.com.co.fallasviales.commons.Utilidad;
import sena.com.co.fallasviales.fragments_mapa.Fragment_ubicacion;

public class Splass_Activity extends AppCompatActivity {
    static final Logger LOG = Logger.getLogger(Splass_Activity.class
            .getSimpleName());
    private static TareaSincronaSplas tareAsincrona;
    private static AuxiliarSugar auxiliarSugar;
    private Firebase firebaseTipos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splass_);
        LOG.info("[whilfer]:::::::::::Splasss");
        SugarContext.init(this);
        firebaseTipos.setAndroidContext(this);
        firebaseTipos = new Firebase(ConfiguracionGlobal.URL_BUSCAR_USUARIOS);
        auxiliarSugar = new AuxiliarSugar(Splass_Activity.this);
        auxiliarSugar.ejecutarFirebase();
        //auxiliarSugar.borrar();
        //this.onRetainNonConfigurationInstance ();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

               /* tareAsincrona = new TareaSincronaSplas(Splass_Activity.this);
                tareAsincrona.execute(firebaseTipos);*/
                Intent intent = new Intent(Splass_Activity.this, PrincipalActivity.class);
                startActivity(intent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 5000);

    }

    public static TareaSincronaSplas getTareAsincrona() {
        return tareAsincrona;
    }

    public static AuxiliarSugar getAuxiliarSugar() {
        return auxiliarSugar;
    }

    public static void setAuxiliarSugar(AuxiliarSugar auxiliarSugar) {
        Splass_Activity.auxiliarSugar = auxiliarSugar;
    }

    public Firebase getFirebaseTipos() {
        return firebaseTipos;
    }

    public void setFirebaseTipos(Firebase firebaseTipos) {
        this.firebaseTipos = firebaseTipos;
    }
}