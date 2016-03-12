package sena.com.co.fallasviales.formulario;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcelable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


import sena.com.co.fallasviales.Entidades.Usuario;
import sena.com.co.fallasviales.R;
import sena.com.co.fallasviales.commons.ConfiguracionGlobal;
import sena.com.co.fallasviales.commons.DialogoAlerta;
import sena.com.co.fallasviales.commons.Utilidad;

public class FormularioActivity extends AppCompatActivity implements Validator.ValidationListener {
    static final Logger LOG = Logger.getLogger(FormularioActivity.class
            .getSimpleName());

    private Auxiliar auxiliar;
    @NotEmpty(messageResId = R.string.msjSeleccionarTexto)
    private EditText nombre, apellidos;
    @Email(messageResId = R.string.msjErrorEmail)
    private EditText correoElectronico;
    private EditText ubicacion;
    private Button btnEnviar;
    private ImageButton btnTomarFoto;
    Firebase firebase;
    Firebase firebaseTipos;
    @Select(messageResId = R.string.msjTipo)
    private Spinner tipos;
    private ArrayAdapter<CharSequence> tiposDanos;
    private Bitmap bitmap;
    private Validator validator;
    private boolean validar;
    Map<View, TextView> spinnerSelections;
    String longitud;
    String latitud;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        final TextInputLayout txtubicacion = (TextInputLayout) findViewById(R.id.txtInUbicacion);
        txtubicacion.setHint(getResources().getString(R.string.ubicacion));
        final TextInputLayout username = (TextInputLayout) findViewById(R.id.txtName);
        username.setHint(getResources().getString(R.string.nombres));
        final TextInputLayout txtInapellidos = (TextInputLayout) findViewById(R.id.txtApellidos);
        txtInapellidos.setHint(getResources().getString(R.string.apellidos));
        final TextInputLayout txtImEmail = (TextInputLayout) findViewById(R.id.txtImEmail);
        txtImEmail.setHint(getResources().getString(R.string.email));
        //inicializar con el contexto
        auxiliar = new Auxiliar(this);
        Firebase.setAndroidContext(this);
        firebaseTipos.setAndroidContext(this);
        //url guardar
        firebase = new Firebase(ConfiguracionGlobal.URL);
        //url buscar tipos
        firebaseTipos = new Firebase(ConfiguracionGlobal.URL_BUSCAR);
        //cargando la configuración cloudinary
        auxiliar.configuracionClaudinary();
        //inicilizar controles
        nombre = (EditText) findViewById(R.id.editNombre);
        apellidos = (EditText) findViewById(R.id.editApellido);
        correoElectronico = (EditText) findViewById(R.id.editEmail);
        ubicacion = (EditText) findViewById(R.id.txtUbicacion);
        ubicacion.setEnabled(false);
        ubicacion.setFocusable(false);
        tipos = (Spinner) findViewById(R.id.spnTipo);
        //cargar tipos en sppiner
        tiposDanos = new ArrayAdapter<CharSequence>(this, R.layout.support_simple_spinner_dropdown_item);
        tiposDanos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tiposDanos.add(getApplicationContext().getText(R.string.seleccion));
        LOG.info("[whilfer]**********formulario activity*************");
        auxiliar.cargarTipos();
        //enviar datos
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        btnTomarFoto = (ImageButton) findViewById(R.id.btnTomarFoto);
        //listener botones
        getBtnEnviar().setOnClickListener(auxiliar);
        getBtnTomarFoto().setOnClickListener(auxiliar);
        validator = new Validator(this);
        validator.setValidationListener(this);
        spinnerSelections = new HashMap<View, TextView>();
        Bundle bundle = getIntent().getExtras();
        //Progress Bar
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setProgress(100);
        //Coordenadas
        longitud = bundle.getString(ConfiguracionGlobal.LONGITUD);
        latitud = bundle.getString(ConfiguracionGlobal.LATITUD);
        //concatenar coordenadas
        coordenadas();
        //añaden coordenas al text view
        if (!coordenadas().toString().isEmpty()){
            ubicacion.setText(coordenadas().toString());
        }else {
            ubicacion.setText( "No se obtuvieron coordenadas  ");
        }

        tipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerSelections.put(parent, (TextView) view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * cooredenas de los mapas
     *
     * @return
     */
    public StringBuilder coordenadas() {
        LOG.info(" *****Cordenadas :longitud" + longitud + ",latitud " + latitud);
        StringBuilder coordenadas = new StringBuilder();
        if (Utilidad.validaNulos(longitud) && Utilidad.validaNulos(latitud)) {
            coordenadas.append(longitud);
            coordenadas.append(",");
            coordenadas.append(latitud);
        }
        return coordenadas;
    }

    /**
     * resultado de la foto capturada
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Utilidad.validaNulos(data.getExtras())) {
            bitmap = (Bitmap) data.getExtras().get(ConfiguracionGlobal.DATA);
            if (Utilidad.validaNulos(bitmap)) {
                //subir foto al cloudinary
                getAuxiliar().subirFoto();
            }
        }
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        boolean bandera = false;
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
                bandera = true;
                setValidar(true);
            } else if (view instanceof Spinner && !bandera) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogoAlerta dialogo = new DialogoAlerta();
                spinnerSelections.get(view).setError(message);
                dialogo.show(fragmentManager, "tagAlerta");
                setValidar(true);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    //gettrrs sertters

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public Spinner getTipos() {
        return tipos;
    }

    public void setTipos(Spinner tipos) {
        this.tipos = tipos;
    }

    public EditText getNombre() {
        return nombre;
    }

    public void setNombre(EditText nombre) {
        this.nombre = nombre;
    }

    public EditText getApellidos() {
        return apellidos;
    }

    public void setApellidos(EditText apellidos) {
        this.apellidos = apellidos;
    }

    public EditText getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(EditText correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public TextView getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(EditText ubicacion) {
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

    public ArrayAdapter<CharSequence> getTiposDanos() {
        return tiposDanos;
    }

    public void setTiposDanos(ArrayAdapter<CharSequence> tiposDanos) {
        this.tiposDanos = tiposDanos;
    }

    public ImageButton getBtnTomarFoto() {
        return btnTomarFoto;
    }

    public void setBtnTomarFoto(ImageButton btnTomarFoto) {
        this.btnTomarFoto = btnTomarFoto;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public boolean isValidar() {
        return validar;
    }

    public void setValidar(boolean validar) {
        this.validar = validar;
    }
}
