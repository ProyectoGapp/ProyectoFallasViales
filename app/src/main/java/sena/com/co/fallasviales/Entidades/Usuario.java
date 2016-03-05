package sena.com.co.fallasviales.Entidades;

import com.orm.SugarRecord;

/**
 * Created by Wilmer Fernandez on 15/02/2016.
 */
public class Usuario extends Irregularidad {
    private String nombre;
    private String apellidos;
    private String correoElectronico;

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

    /*@Override
    public boolean equals(Object o) {
        if (o instanceof Usuario) {
            Usuario usuario = (Usuario) o;
            if (super.equals(o) && this.getIdentificador().equals(usuario.getIdentificador()))
                return true;
            else {
                return false;
            }
        }else {
            return false;
        }
    }*/
}
