package com.puma.nextwhere.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by rajesh on 31/8/17.
 */

public class LoginOtherInfo implements Serializable {
    @SerializedName("id_usuario_detalle")
    @Expose
    private String idUsuarioDetalle;
    @SerializedName("id_usuario")
    @Expose
    private String idUsuario;
    @SerializedName("id_reserva")
    @Expose
    private String idReserva;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("apellido")
    @Expose
    private String apellido;
    @SerializedName("apellido2")
    @Expose
    private Object apellido2;
    @SerializedName("movil")
    @Expose
    private String movil;
    @SerializedName("genero")
    @Expose
    private String genero;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("fecha_nacimiento")
    @Expose
    private String fechaNacimiento;
    @SerializedName("tipo_documento")
    @Expose
    private Object tipoDocumento;
    @SerializedName("documento")
    @Expose
    private Object documento;
    @SerializedName("fecha_expiracion_documento")
    @Expose
    private String fechaExpiracionDocumento;
    @SerializedName("pasaporte")
    @Expose
    private String pasaporte;
    @SerializedName("nacionalidad")
    @Expose
    private String nacionalidad;
    @SerializedName("recidencia")
    @Expose
    private String recidencia;
    @SerializedName("direccion")
    @Expose
    private String direccion;
    @SerializedName("municipio")
    @Expose
    private String municipio;
    @SerializedName("estado")
    @Expose
    private String estado;
    @SerializedName("codigo_postal")
    @Expose
    private String codigoPostal;
    @SerializedName("facebook")
    @Expose
    private String facebook;
    @SerializedName("instagram")
    @Expose
    private String instagram;
    @SerializedName("principal")
    @Expose
    private String principal;
    @SerializedName("borrado")
    @Expose
    private String borrado;

    public String getIdUsuarioDetalle() {
        return idUsuarioDetalle;
    }

    public void setIdUsuarioDetalle(String idUsuarioDetalle) {
        this.idUsuarioDetalle = idUsuarioDetalle;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Object getApellido2() {
        return apellido2;
    }

    public void setApellido2(Object apellido2) {
        this.apellido2 = apellido2;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Object getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(Object tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Object getDocumento() {
        return documento;
    }

    public void setDocumento(Object documento) {
        this.documento = documento;
    }

    public String getFechaExpiracionDocumento() {
        return fechaExpiracionDocumento;
    }

    public void setFechaExpiracionDocumento(String fechaExpiracionDocumento) {
        this.fechaExpiracionDocumento = fechaExpiracionDocumento;
    }

    public String getPasaporte() {
        return pasaporte;
    }

    public void setPasaporte(String pasaporte) {
        this.pasaporte = pasaporte;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getRecidencia() {
        return recidencia;
    }

    public void setRecidencia(String recidencia) {
        this.recidencia = recidencia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getBorrado() {
        return borrado;
    }

    public void setBorrado(String borrado) {
        this.borrado = borrado;
    }



}
