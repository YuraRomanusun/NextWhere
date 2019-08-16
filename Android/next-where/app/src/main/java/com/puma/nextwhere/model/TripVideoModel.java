package com.puma.nextwhere.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rajesh on 8/10/17.
 */

public class TripVideoModel {
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("ruta_imagen")
    @Expose
    private String rutaImagen;
    @SerializedName("video")
    @Expose
    private String video;
    @SerializedName("codigo_origen")
    @Expose
    private String codigoOrigen;
    @SerializedName("id_contenido_articulo")
    @Expose
    private String idContenidoArticulo;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getCodigoOrigen() {
        return codigoOrigen;
    }

    public void setCodigoOrigen(String codigoOrigen) {
        this.codigoOrigen = codigoOrigen;
    }

    public String getIdContenidoArticulo() {
        return idContenidoArticulo;
    }

    public void setIdContenidoArticulo(String idContenidoArticulo) {
        this.idContenidoArticulo = idContenidoArticulo;
    }
}
