package sena.com.co.fallasviales.Entidades;

import com.orm.SugarRecord;

/**
 * Created by Wilmer Fernandez on 15/02/2016.
 */
public class Irregularidad extends SugarRecord {
    private String cordenadas;
    private String ubicacion;
    private String tipo;
    private String urlFoto;
    private String identificador;

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


    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }


}
