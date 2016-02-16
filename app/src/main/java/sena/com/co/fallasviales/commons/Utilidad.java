package sena.com.co.fallasviales.commons;

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
