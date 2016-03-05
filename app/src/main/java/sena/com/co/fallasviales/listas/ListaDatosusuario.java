package sena.com.co.fallasviales.listas;

/**
 * Created by Mobilelab18 on 5/03/2016.
 */
public class ListaDatosusuario {
    String padre;
    String apellidos;
    String cordenadas;
    String correoElectronico;
    String nombre;
    String tipo;
    String ubicacion;
    String urlFoto;

    public ListaDatosusuario(String padre,String apellidos, String cordenadas, String correoElectronico, String nombre, String tipo, String ubicacion, String urlFoto) {
        this.padre = padre;
        this.apellidos = apellidos;
        this.cordenadas = cordenadas;
        this.correoElectronico = correoElectronico;
        this.nombre = nombre;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.urlFoto = urlFoto;
    }

    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCordenadas() {
        return cordenadas;
    }

    public void setCordenadas(String cordenadas) {
        this.cordenadas = cordenadas;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}
