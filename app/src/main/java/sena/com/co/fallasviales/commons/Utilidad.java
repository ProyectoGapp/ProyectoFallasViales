package sena.com.co.fallasviales.commons;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Wilmer Fernandez on 15/02/2016.
 */
public class Utilidad {
    static final Logger LOG = Logger.getLogger(Utilidad.class
            .getSimpleName());

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
     * Metodo para validar el campo correo
     *
     * @param correo
     * @return
     */
    public static boolean verificarCorreo(String correo) {
        LOG.info("[whilfer]***********Correo **********"+correo);
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
}
