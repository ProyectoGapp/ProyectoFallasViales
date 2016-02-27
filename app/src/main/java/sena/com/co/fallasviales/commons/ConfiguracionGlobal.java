package sena.com.co.fallasviales.commons;

import android.content.Context;

import sena.com.co.fallasviales.R;

/**
 * Created by Wilmer Fernandez on 15/02/2016.
 */
public class ConfiguracionGlobal {
    private ConfiguracionGlobal() {
        super();
    }
    private static Context context;
    public static final String PATTERN = "^[a-zA-Z0-9_-]{2,}(\\.[a-zA-Z0-9_-]{2,})?@[a-zA-Z0-9_-]{2,}\\.[a-zA-Z]{2,4}(\\.[a-zA-Z]{2,4})?$";
    public static final String URL = "https://intense-inferno-2724.firebaseio.com/";
    public static final String URL_BUSCAR = "https://intense-inferno-2724.firebaseio.com/Irregularidad/tipos";
    public static final String USUARIOS = "Usuarios";
    public static final String SALTO_DE_LINEA = "\n";
    public static final String SELECCION = "Seleccione";
    //cLOUDINARY
    public static final String CLOUDINARY_NOMBRE = "senawhilfer";
    public static final String CLOUDINARY_CONNECTION_NAME = "cloud_name";
    public static final String CLOUDINARY_API_KEY = "api_key";
    public static final String CLOUDINARY_API_KEY_SECRET= "629271186361592";
    public static final String CLOUDINARY_API_SECRET_N= "api_secret";
    public static final String CLOUDINARY_API_SECRET = "ps2zdBHMwxK7NtiaZNSrtIBX0A8";
    public static final String DATA = "data";
    public static final String URL_FOTO = "url";
    public static final String MESAJE_ERRO_FOTO = "Error al cargar Imagen";
    public static final String IMAGEN = "IMG";
    public static final String USER = "USER";
    public static final String LLAVE = "public_id";
    public static final int CALIDAD_IMAGEN = 100;
    public static final int CERO = 0;
    public static final int UNO = 1;
            //String.valueOf(context.getResources().getText(R.string.msjSeleccionarTexto)) ;

}
