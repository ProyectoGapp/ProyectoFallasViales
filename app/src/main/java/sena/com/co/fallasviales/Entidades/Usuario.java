package sena.com.co.fallasviales.Entidades;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

/**
 * Created by Wilmer Fernandez on 15/02/2016.
 */
public class Usuario extends Irregularidad {
    private String nombre;
    private String apellidos;
    private String correoElectronico;
    @Ignore
    private boolean bandera;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }
}
