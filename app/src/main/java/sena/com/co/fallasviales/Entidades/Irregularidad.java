package sena.com.co.fallasviales.Entidades;

/**
 * Created by Wilmer Fernandez on 15/02/2016.
 */
public class Irregularidad {
    private String cordenadas;
    private String ubicacion;
    private String tipo;
    private String urlFoto;

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getCordenadas() {
        return cordenadas;
    }

    public void setCordenadas(String cordenadas) {
        this.cordenadas = cordenadas;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
