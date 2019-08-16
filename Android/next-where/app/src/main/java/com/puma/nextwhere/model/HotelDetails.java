package com.puma.nextwhere.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rajesh on 10/10/17.
 */

public class HotelDetails {
    @SerializedName("id_contenido_hotel")
    @Expose
    private String idContenidoHotel;
    @SerializedName("id_contenido_articulo")
    @Expose
    private String idContenidoArticulo;
    @SerializedName("id_ciudad")
    @Expose
    private String idCiudad;
    @SerializedName("estrellas")
    @Expose
    private String estrellas;
    @SerializedName("wifi")
    @Expose
    private String wifi;
    @SerializedName("direccion")
    @Expose
    private String direccion;
    @SerializedName("cualidad_1")
    @Expose
    private String cualidad1;
    @SerializedName("cualidad_1EN")
    @Expose
    private String cualidad1EN;
    @SerializedName("cualidad_2")
    @Expose
    private String cualidad2;
    @SerializedName("cualidad_2EN")
    @Expose
    private String cualidad2EN;
    @SerializedName("cualidad_3")
    @Expose
    private String cualidad3;
    @SerializedName("cualidad_3EN")
    @Expose
    private String cualidad3EN;
    @SerializedName("cualidad_4")
    @Expose
    private String cualidad4;
    @SerializedName("cualidad_4EN")
    @Expose
    private String cualidad4EN;
    @SerializedName("cualidad_5")
    @Expose
    private String cualidad5;
    @SerializedName("cualidad_5EN")
    @Expose
    private String cualidad5EN;
    @SerializedName("ciudad")
    @Expose
    private String ciudad;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("nombreEN")
    @Expose
    private String nombreEN;
    @SerializedName("ruta_imagen_ciudad_destino")
    @Expose
    private String rutaImagenCiudadDestino;
    @SerializedName("longitud")
    @Expose
    private String longitud;
    @SerializedName("latitud")
    @Expose
    private String latitud;
    @SerializedName("nombreHotel")
    @Expose
    private String nombreHotel;
    @SerializedName("imagen_hotel")
    @Expose
    private String imagenHotel;

    public String getIdContenidoHotel() {
        return idContenidoHotel;
    }

    public void setIdContenidoHotel(String idContenidoHotel) {
        this.idContenidoHotel = idContenidoHotel;
    }

    public String getIdContenidoArticulo() {
        return idContenidoArticulo;
    }

    public void setIdContenidoArticulo(String idContenidoArticulo) {
        this.idContenidoArticulo = idContenidoArticulo;
    }

    public String getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(String idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(String estrellas) {
        this.estrellas = estrellas;
    }

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCualidad1() {
        return cualidad1;
    }

    public void setCualidad1(String cualidad1) {
        this.cualidad1 = cualidad1;
    }

    public String getCualidad1EN() {
        return cualidad1EN;
    }

    public void setCualidad1EN(String cualidad1EN) {
        this.cualidad1EN = cualidad1EN;
    }

    public String getCualidad2() {
        return cualidad2;
    }

    public void setCualidad2(String cualidad2) {
        this.cualidad2 = cualidad2;
    }

    public String getCualidad2EN() {
        return cualidad2EN;
    }

    public void setCualidad2EN(String cualidad2EN) {
        this.cualidad2EN = cualidad2EN;
    }

    public String getCualidad3() {
        return cualidad3;
    }

    public void setCualidad3(String cualidad3) {
        this.cualidad3 = cualidad3;
    }

    public String getCualidad3EN() {
        return cualidad3EN;
    }

    public void setCualidad3EN(String cualidad3EN) {
        this.cualidad3EN = cualidad3EN;
    }

    public String getCualidad4() {
        return cualidad4;
    }

    public void setCualidad4(String cualidad4) {
        this.cualidad4 = cualidad4;
    }

    public String getCualidad4EN() {
        return cualidad4EN;
    }

    public void setCualidad4EN(String cualidad4EN) {
        this.cualidad4EN = cualidad4EN;
    }

    public String getCualidad5() {
        return cualidad5;
    }

    public void setCualidad5(String cualidad5) {
        this.cualidad5 = cualidad5;
    }

    public String getCualidad5EN() {
        return cualidad5EN;
    }

    public void setCualidad5EN(String cualidad5EN) {
        this.cualidad5EN = cualidad5EN;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreEN() {
        return nombreEN;
    }

    public void setNombreEN(String nombreEN) {
        this.nombreEN = nombreEN;
    }

    public String getRutaImagenCiudadDestino() {
        return rutaImagenCiudadDestino;
    }

    public void setRutaImagenCiudadDestino(String rutaImagenCiudadDestino) {
        this.rutaImagenCiudadDestino = rutaImagenCiudadDestino;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getNombreHotel() {
        return nombreHotel;
    }

    public void setNombreHotel(String nombreHotel) {
        this.nombreHotel = nombreHotel;
    }

    public String getImagenHotel() {
        return imagenHotel;
    }

    public void setImagenHotel(String imagenHotel) {
        this.imagenHotel = imagenHotel;
    }
}
