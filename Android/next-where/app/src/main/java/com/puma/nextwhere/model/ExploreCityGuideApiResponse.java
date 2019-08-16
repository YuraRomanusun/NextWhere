package com.puma.nextwhere.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rajesh on 8/9/17.
 */

public class ExploreCityGuideApiResponse {

    @SerializedName("id_nextwhere_destinos_puntos_interes")
    @Expose
    private String idNextwhereDestinosPuntosInteres;
    @SerializedName("id_destino")
    @Expose
    private String idDestino;
    @SerializedName("tipo_atraccion")
    @Expose
    private String tipoAtraccion;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("categoria")
    @Expose
    private String categoria;
    @SerializedName("raking")
    @Expose
    private String raking;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("imagen")
    @Expose
    private String imagen;
    @SerializedName("direccion")
    @Expose
    private String direccion;
    @SerializedName("codigopostal")
    @Expose
    private String codigopostal;
    @SerializedName("estado")
    @Expose
    private String estado;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("hora")
    @Expose
    private String hora;
    @SerializedName("minutos")
    @Expose
    private String minutos;
    @SerializedName("nombre_destino")
    @Expose
    private String nombreDestino;
    @SerializedName("latitud")
    @Expose
    private String latitud;
    @SerializedName("longitud")
    @Expose
    private String longitud;

    public String getNombreLink() {
        return nombreLink;
    }

    public void setNombreLink(String nombreLink) {
        this.nombreLink = nombreLink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @SerializedName("rango_precio")
    @Expose
    private String rangoPrecio;

    @SerializedName("nombre_link")
    @Expose
    private String nombreLink;

    @SerializedName("link")
    @Expose
    private String link;



    public String getRangoPrecio() {
        return rangoPrecio;
    }

    public void setRangoPrecio(String rangoPrecio) {
        this.rangoPrecio = rangoPrecio;
    }

    private String distance = "";

    private double distanceDouble;

    public double getDistanceDouble() {
        return distanceDouble;
    }

    public void setDistanceDouble(double distanceDouble) {
        this.distanceDouble = distanceDouble;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getLatitud() {
        return latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getIdNextwhereDestinosPuntosInteres() {
        return idNextwhereDestinosPuntosInteres;
    }

    public void setIdNextwhereDestinosPuntosInteres(String idNextwhereDestinosPuntosInteres) {
        this.idNextwhereDestinosPuntosInteres = idNextwhereDestinosPuntosInteres;
    }

    public String getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(String idDestino) {
        this.idDestino = idDestino;
    }

    public String getTipoAtraccion() {
        return tipoAtraccion;
    }

    public void setTipoAtraccion(String tipoAtraccion) {
        this.tipoAtraccion = tipoAtraccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getRaking() {
        return raking;
    }

    public void setRaking(String raking) {
        this.raking = raking;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigopostal() {
        return codigopostal;
    }

    public void setCodigopostal(String codigopostal) {
        this.codigopostal = codigopostal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMinutos() {
        return minutos;
    }

    public void setMinutos(String minutos) {
        this.minutos = minutos;
    }

    public String getNombreDestino() {
        return nombreDestino;
    }

    public void setNombreDestino(String nombreDestino) {
        this.nombreDestino = nombreDestino;
    }

}

