package sena.com.co.fallasviales.Entidades;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.logging.Logger;

import sena.com.co.fallasviales.commons.ConfiguracionGlobal;
import sena.com.co.fallasviales.commons.Utilidad;
import sena.com.co.fallasviales.formulario.Auxiliar;
import sena.com.co.fallasviales.formulario.FormularioActivity;

/**
 * Created by Wilmer Fernandez on 20/02/2016.
 */
public class TareAsincrona extends AsyncTask {
    static final Logger LOG = Logger.getLogger(TareAsincrona.class
            .getSimpleName());
    private String url;
    private FormularioActivity activity;

    public TareAsincrona(FormularioActivity activity) {
        this.activity = activity;
    }


    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        activity.getProgressBar().setVisibility(View.INVISIBLE);

    }

    /**
     * @param params
     * @return
     */
    @Override
    protected Object doInBackground(Object[] params) {
        Bitmap bitmap = (Bitmap) params[ConfiguracionGlobal.CERO];
        Cloudinary cloudinary = (Cloudinary) params[ConfiguracionGlobal.UNO];
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, ConfiguracionGlobal.CALIDAD_IMAGEN, bytes);
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes.toByteArray());
        try {
            Map resultadoDesacarga = cloudinary.uploader().upload(bais, ObjectUtils.asMap(ConfiguracionGlobal.LLAVE, Utilidad.idUsuario(ConfiguracionGlobal.IMAGEN).toString()));
            setUrl(new String());
            //se asigna la url devuelta
            setUrl((String) resultadoDesacarga.get(ConfiguracionGlobal.URL_FOTO));
            LOG.info("[whilfer]****Url********" + resultadoDesacarga.get(ConfiguracionGlobal.URL_FOTO));
        } catch (IOException e) {
           LOG.info("[Error al cargar foto]"+e.getLocalizedMessage());
        }
        return null;
    }
    //gettrs y settrs
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
