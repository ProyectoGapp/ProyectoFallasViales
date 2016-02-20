package sena.com.co.fallasviales.commons;

/**
 * Created by Wilmer Fernandez on 15/02/2016.
 */
public class ConfiguracionGlobal {
    private ConfiguracionGlobal() {
        super();
    }
    public static final String PATTERN = "^[a-zA-Z0-9_-]{2,}(\\.[a-zA-Z0-9_-]{2,})?@[a-zA-Z0-9_-]{2,}\\.[a-zA-Z]{2,4}(\\.[a-zA-Z]{2,4})?$";
    public static final String URL = "https://luminous-fire-2293.firebaseio.com/";
    public static final String URL_BUSCAR = "https://luminous-fire-2293.firebaseio.com/Tipos";
    public static final String USUARIOS = "Usuarios";
    public static final String SALTO_DE_LINEA = "\n";
    public static final String SELECCION = "Seleccione";
    //cLOUDINARY
    public static final String CLOUDINARY_NOMBRE = "senawhilfer";
    public static final String CLOUDINARY_CONNECTION_NAME = "clous_name";
    public static final String CLOUDINARY_API_KEY = "api_key";
    public static final String CLOUDINARY_API_KEY_SECRET= "629271186361592";
    public static final String CLOUDINARY_API_SECRET_N= "api_secret";
    public static final String CLOUDINARY_API_SECRET = "ps2zdBHMwxK7NtiaZNSrtIBX0A8";

}
