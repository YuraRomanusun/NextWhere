package com.puma.nextwhere.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rajesh on 28/10/17.
 */

public class SupriseInfo {
    @SerializedName("id_nextwhere_viajes_sorpresas")
    @Expose
    private String idNextwhereViajesSorpresas;
    @SerializedName("id_viaje")
    @Expose
    private String idViaje;
    @SerializedName("imagen")
    @Expose
    private String imagen;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("imagen_barcode")
    @Expose
    private String imagenBarcode;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("terms")
    @Expose
    private String terms;

    public String getIdNextwhereViajesSorpresas() {
        return idNextwhereViajesSorpresas;
    }

    public void setIdNextwhereViajesSorpresas(String idNextwhereViajesSorpresas) {
        this.idNextwhereViajesSorpresas = idNextwhereViajesSorpresas;
    }

    public String getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(String idViaje) {
        this.idViaje = idViaje;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getImagenBarcode() {
        return imagenBarcode;
    }

    public void setImagenBarcode(String imagenBarcode) {
        this.imagenBarcode = imagenBarcode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }
}
