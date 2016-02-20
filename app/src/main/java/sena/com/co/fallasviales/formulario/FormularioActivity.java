package sena.com.co.fallasviales.formulario;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.client.Firebase;

import sena.com.co.fallasviales.R;
import sena.com.co.fallasviales.commons.ConfiguracionGlobal;

public class FormularioActivity extends AppCompatActivity {
    private EditText nombre, apellidos, correoElectronico;
    private TextView ubicacion;
    private Button btnEnviar;
    private Auxiliar auxiliar;
    Firebase firebase;
    Firebase firebaseTipos;
    private Spinner tipos;
    private ArrayAdapter<CharSequence> tiposDanos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        //inicializar con el contexto
        auxiliar = new Auxiliar(this);
        Firebase.setAndroidContext(this);
        firebaseTipos.setAndroidContext(this);
        //url guardar
        firebase = new Firebase(ConfiguracionGlobal.URL);
        //url buscar tipos
        firebaseTipos=new Firebase(ConfiguracionGlobal.URL_BUSCAR);
        //inicilizar controles
        nombre = (EditText) findViewById(R.id.editNombre);
        apellidos = (EditText) findViewById(R.id.editApellido);
        correoElectronico = (EditText) findViewById(R.id.editEmail);
        ubicacion = (TextView) findViewById(R.id.txtUbicacion);
        tipos = (Spinner) findViewById(R.id.spnTipo);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        //cargar tipos en sppiner
        tiposDanos = new ArrayAdapter<CharSequence>(this, R.layout.support_simple_spinner_dropdown_item);
        tiposDanos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        auxiliar.cargarTipos();
        //enviar datos
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        getBtnEnviar().setOnClickListener(auxiliar);
    }

    public EditText getNombre() {
        return nombre;
    }

    public void setNombre(EditText nombre) {
        this.nombre = nombre;
    }

    public EditText getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(EditText correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public EditText getApellidos() {
        return apellidos;
    }

    public void setApellidos(EditText apellidos) {
        this.apellidos = apellidos;
    }

    public TextView getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(TextView ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Button getBtnEnviar() {
        return btnEnviar;
    }

    public void setBtnEnviar(Button btnEnviar) {
        this.btnEnviar = btnEnviar;
    }

    public Auxiliar getAuxiliar() {
        return auxiliar;
    }

    public void setAuxiliar(Auxiliar auxiliar) {
        this.auxiliar = auxiliar;
    }

    public Firebase getFirebase() {
        return firebase;
    }

    public void setFirebase(Firebase firebase) {
        this.firebase = firebase;
    }

    public Firebase getFirebaseTipos() {
        return firebaseTipos;
    }

    public void setFirebaseTipos(Firebase firebaseTipos) {
        this.firebaseTipos = firebaseTipos;
    }

    public Spinner getTipos() {
        return tipos;
    }

    public void setTipos(Spinner tipos) {
        this.tipos = tipos;
    }

    public ArrayAdapter<CharSequence> getTiposDanos() {
        return tiposDanos;
    }

    public void setTiposDanos(ArrayAdapter<CharSequence> tiposDanos) {
        this.tiposDanos = tiposDanos;
    }
}
