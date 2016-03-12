package sena.com.co.fallasviales.commons;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sena.com.co.fallasviales.Entidades.TareaSincronaSplas;
import sena.com.co.fallasviales.Entidades.Usuario;

/**
 * Created by Wilmer Fernandez on 15/02/2016.
 */
public class Utilidad {
    static final Logger LOG = Logger.getLogger(Utilidad.class
            .getSimpleName());
    public static final List<Usuario> usuarios = new ArrayList<>();

    /**
     * Metodo generic para validar objetos nulos     *
     *
     * @param entidad
     * @param <T>
     * @return false en caso verdadero
     */
    public static <T> boolean validaNulos(T entidad) {
        if (null == entidad) {
            return false;
        }
        return true;
    }


    public static boolean cadenaVacia(String cadena) {
        boolean cadenaVacia = true;
        if (cadena == null || cadena.trim().isEmpty()) {
            cadenaVacia = false;
        }
        return cadenaVacia;
    }

    /**
     * verifica si hay conexion
     *
     * @param context
     * @return
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (Utilidad.validaNulos(netInfo) && netInfo.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param context
     * @return
     */
    public static boolean hasActiveInternetConnection(Context context) {
        if (isOnline(context)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(3000);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
                LOG.info("Error checking internet connection" + e);
            }
        } else {
            LOG.info("No network available!");
        }
        return false;
    }
    /**
     * @param context
     * @return
     */
    /*public static boolean hasActiveInternetConnection(Context context) {
        if (isOnline(context)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection)
                        (new URL("http://clients3.google.com/generate_204")
                                .openConnection());
                urlc.setRequestProperty("User-Agent", "Android");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(3000);
                urlc.connect();
                return (urlc.getResponseCode() == 204 &&
                        urlc.getContentLength() == 0);
            } catch (IOException e) {
                LOG.info("Error checking internet connection" + e);
            }
        } else {
            LOG.info("No network available!");
        }
        return false;
    }*/

    /**
     * genera id
     *
     * @param id
     * @return
     */
    public static StringBuilder url(String id) {
        StringBuilder url = new StringBuilder();
        url.append(ConfiguracionGlobal.URL_BUSCAR_USUARIOS);
        url.append(ConfiguracionGlobal.SALTO_DE_LINEA);
        url.append(id);
        url.append(ConfiguracionGlobal.SALTO_DE_LINEA);
        return url;
    }

    /**
     * Metodo para validar el campo correo
     *
     * @param correo
     * @return
     */
    public static boolean verificarCorreo(String correo) {
        LOG.info("[whilfer]***********Correo **********" + correo);
        final Pattern pat = Pattern
                .compile(ConfiguracionGlobal.PATTERN);
        final Matcher mat = pat.matcher(correo);
        if (mat.find()) {
            return true;
        }
        return false;
    }

    /**
     * id usuario
     *
     * @return
     */
    public static StringBuilder idUsuario(String tipo) {
        StringBuilder fechaActual = new StringBuilder();
        int año, mes, dia, hora, minuto, segundo;
        Calendar fecha = new GregorianCalendar();
        fechaActual.append(tipo);
        fechaActual.append(año = fecha.get(Calendar.YEAR));
        fechaActual.append(mes = fecha.get(Calendar.MONTH));
        fechaActual.append(dia = fecha.get(Calendar.DAY_OF_MONTH));
        fechaActual.append(hora = fecha.get(Calendar.HOUR_OF_DAY));
        fechaActual.append(minuto = fecha.get(Calendar.MINUTE));
        fechaActual.append(segundo = fecha.get(Calendar.SECOND));
        return fechaActual;

    }

    /**
     * valida si es un numero
     *
     * @param cadena
     * @return
     */
    public static boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }


    public static Logger getLOG() {
        return LOG;
    }

    public static List<Usuario> getUsuarios() {
        return usuarios;
    }
}
