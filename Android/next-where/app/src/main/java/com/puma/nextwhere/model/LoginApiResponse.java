package com.puma.nextwhere.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rajesh on 31/8/17.
 */

public class LoginApiResponse implements Serializable {
    @SerializedName("id_reserva")
    @Expose
    private String idReserva;
    @SerializedName("mostrar_destino")
    @Expose
    private String mostrarDestino;
    public void setError(String error) {
        this.error = error;
    }
    @SerializedName("error")
    @Expose
    private String error="";
    @SerializedName("id_usuario")
    @Expose
    private String idUsuario;
    @SerializedName("id_desde")
    @Expose
    private String idDesde;
    @SerializedName("hasta")
    @Expose
    private String hasta;
    @SerializedName("pasajeros")
    @Expose
    private List<LoginOtherInfo> pasajeros = null;
    @SerializedName("estado")
    @Expose
    private String estado;
    @SerializedName("id_estado_pago")
    @Expose
    private String idEstadoPago;
    @SerializedName("id_viaje_cupo")
    @Expose
    private String idViajeCupo;
    @SerializedName("id_destino")
    @Expose
    private String idDestino;
    @SerializedName("id_hotel")
    @Expose
    private String idHotel;
    @SerializedName("tipo")
    @Expose
    private String tipo;
    @SerializedName("fecha_desde")
    @Expose
    private String fechaDesde;
    @SerializedName("fecha_hasta")
    @Expose
    private String fechaHasta;
    @SerializedName("rango_horario_desde")
    @Expose
    private String rangoHorarioDesde;
    @SerializedName("rango_horario_hasta")
    @Expose
    private String rangoHorarioHasta;
    @SerializedName("aerolinea_ida")
    @Expose
    private String aerolineaIda;
    @SerializedName("aerolinea_reference_ida")
    @Expose
    private String aerolineaReferenceIda;
    @SerializedName("aeropuerto_ida")
    @Expose
    private String aeropuertoIda;
    @SerializedName("aerolinea_vuelta")
    @Expose
    private String aerolineaVuelta;
    @SerializedName("aerolinea_reference_vuelta")
    @Expose
    private String aerolineaReferenceVuelta;
    @SerializedName("aeropuerto_vuelta")
    @Expose
    private String aeropuertoVuelta;
    @SerializedName("numero_vuelo_ida")
    @Expose
    private String numeroVueloIda;
    @SerializedName("numero_vuelo_vuelta")
    @Expose
    private String numeroVueloVuelta;
    @SerializedName("aeropuerto_llegada_ida")
    @Expose
    private String aeropuertoLlegadaIda;
    @SerializedName("aeropuerto_llegada_vuelta")
    @Expose
    private String aeropuertoLlegadaVuelta;
    @SerializedName("fecha_ida")
    @Expose
    private String fechaIda;
    @SerializedName("fecha_vuelta")
    @Expose
    private String fechaVuelta;
    @SerializedName("hora_ida")
    @Expose
    private String horaIda;
    @SerializedName("hora_vuelta")
    @Expose
    private String horaVuelta;
    @SerializedName("observacion_general")
    @Expose
    private String observacionGeneral;
    @SerializedName("premio")
    @Expose
    private String premio;
    @SerializedName("premio_video")
    @Expose
    private String premioVideo;
    @SerializedName("observaciones")
    @Expose
    private String observaciones;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("subtotal")
    @Expose
    private String subtotal;
    @SerializedName("total_descarte_ciudades")
    @Expose
    private String totalDescarteCiudades;
    @SerializedName("total_preferencia_horaria")
    @Expose
    private String totalPreferenciaHoraria;
    @SerializedName("viaje_secreto")
    @Expose
    private String viajeSecreto;
    @SerializedName("vista_inmediata")
    @Expose
    private String vistaInmediata;
    @SerializedName("solo_usa")
    @Expose
    private String soloUsa;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("apellido")
    @Expose
    private String apellido;
    @SerializedName("email_cliente")
    @Expose
    private String emailCliente;
    @SerializedName("hotel")
    @Expose
    private String hotel;
    @SerializedName("imagen_hotel")
    @Expose
    private String imagenHotel;
    @SerializedName("desde")
    @Expose
    private String desde;
    @SerializedName("hasta_asignado")
    @Expose
    private String hastaAsignado;
    @SerializedName("ruta_imagen_destino")
    @Expose
    private String rutaImagenDestino;
    @SerializedName("codigo")
    @Expose
    private String codigo;
    @SerializedName("sorpresas")
    @Expose
    private List<SupriseInfo> sorpresas = null;

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdDesde() {
        return idDesde;
    }

    public void setIdDesde(String idDesde) {
        this.idDesde = idDesde;
    }

    public String getHasta() {
        return hasta;
    }

    public void setHasta(String hasta) {
        this.hasta = hasta;
    }

    public List<LoginOtherInfo> getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(List<LoginOtherInfo> pasajeros) {
        this.pasajeros = pasajeros;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdEstadoPago() {
        return idEstadoPago;
    }

    public void setIdEstadoPago(String idEstadoPago) {
        this.idEstadoPago = idEstadoPago;
    }

    public String getIdViajeCupo() {
        return idViajeCupo;
    }

    public void setIdViajeCupo(String idViajeCupo) {
        this.idViajeCupo = idViajeCupo;
    }

    public String getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(String idDestino) {
        this.idDestino = idDestino;
    }

    public String getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(String idHotel) {
        this.idHotel = idHotel;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getRangoHorarioDesde() {
        return rangoHorarioDesde;
    }

    public void setRangoHorarioDesde(String rangoHorarioDesde) {
        this.rangoHorarioDesde = rangoHorarioDesde;
    }

    public String getRangoHorarioHasta() {
        return rangoHorarioHasta;
    }

    public void setRangoHorarioHasta(String rangoHorarioHasta) {
        this.rangoHorarioHasta = rangoHorarioHasta;
    }

    public String getAerolineaIda() {
        return aerolineaIda;
    }

    public void setAerolineaIda(String aerolineaIda) {
        this.aerolineaIda = aerolineaIda;
    }

    public String getAerolineaReferenceIda() {
        return aerolineaReferenceIda;
    }

    public void setAerolineaReferenceIda(String aerolineaReferenceIda) {
        this.aerolineaReferenceIda = aerolineaReferenceIda;
    }

    public String getAeropuertoIda() {
        return aeropuertoIda;
    }

    public void setAeropuertoIda(String aeropuertoIda) {
        this.aeropuertoIda = aeropuertoIda;
    }

    public String getAerolineaVuelta() {
        return aerolineaVuelta;
    }

    public void setAerolineaVuelta(String aerolineaVuelta) {
        this.aerolineaVuelta = aerolineaVuelta;
    }

    public String getAerolineaReferenceVuelta() {
        return aerolineaReferenceVuelta;
    }

    public void setAerolineaReferenceVuelta(String aerolineaReferenceVuelta) {
        this.aerolineaReferenceVuelta = aerolineaReferenceVuelta;
    }

    public String getAeropuertoVuelta() {
        return aeropuertoVuelta;
    }

    public void setAeropuertoVuelta(String aeropuertoVuelta) {
        this.aeropuertoVuelta = aeropuertoVuelta;
    }

    public String getNumeroVueloIda() {
        return numeroVueloIda;
    }

    public void setNumeroVueloIda(String numeroVueloIda) {
        this.numeroVueloIda = numeroVueloIda;
    }

    public String getNumeroVueloVuelta() {
        return numeroVueloVuelta;
    }

    public void setNumeroVueloVuelta(String numeroVueloVuelta) {
        this.numeroVueloVuelta = numeroVueloVuelta;
    }

    public String getAeropuertoLlegadaIda() {
        return aeropuertoLlegadaIda;
    }

    public void setAeropuertoLlegadaIda(String aeropuertoLlegadaIda) {
        this.aeropuertoLlegadaIda = aeropuertoLlegadaIda;
    }

    public String getAeropuertoLlegadaVuelta() {
        return aeropuertoLlegadaVuelta;
    }

    public void setAeropuertoLlegadaVuelta(String aeropuertoLlegadaVuelta) {
        this.aeropuertoLlegadaVuelta = aeropuertoLlegadaVuelta;
    }

    public String getFechaIda() {
        return fechaIda;
    }

    public void setFechaIda(String fechaIda) {
        this.fechaIda = fechaIda;
    }

    public String getFechaVuelta() {
        return fechaVuelta;
    }

    public void setFechaVuelta(String fechaVuelta) {
        this.fechaVuelta = fechaVuelta;
    }

    public String getHoraIda() {
        return horaIda;
    }

    public void setHoraIda(String horaIda) {
        this.horaIda = horaIda;
    }

    public String getHoraVuelta() {
        return horaVuelta;
    }

    public void setHoraVuelta(String horaVuelta) {
        this.horaVuelta = horaVuelta;
    }

    public String getObservacionGeneral() {
        return observacionGeneral;
    }

    public void setObservacionGeneral(String observacionGeneral) {
        this.observacionGeneral = observacionGeneral;
    }

    public String getPremio() {
        return premio;
    }

    public void setPremio(String premio) {
        this.premio = premio;
    }

    public String getPremioVideo() {
        return premioVideo;
    }

    public void setPremioVideo(String premioVideo) {
        this.premioVideo = premioVideo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getTotalDescarteCiudades() {
        return totalDescarteCiudades;
    }

    public void setTotalDescarteCiudades(String totalDescarteCiudades) {
        this.totalDescarteCiudades = totalDescarteCiudades;
    }

    public String getTotalPreferenciaHoraria() {
        return totalPreferenciaHoraria;
    }

    public void setTotalPreferenciaHoraria(String totalPreferenciaHoraria) {
        this.totalPreferenciaHoraria = totalPreferenciaHoraria;
    }

    public String getViajeSecreto() {
        return viajeSecreto;
    }

    public void setViajeSecreto(String viajeSecreto) {
        this.viajeSecreto = viajeSecreto;
    }

    public String getVistaInmediata() {
        return vistaInmediata;
    }

    public void setVistaInmediata(String vistaInmediata) {
        this.vistaInmediata = vistaInmediata;
    }

    public String getSoloUsa() {
        return soloUsa;
    }

    public void setSoloUsa(String soloUsa) {
        this.soloUsa = soloUsa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getImagenHotel() {
        return imagenHotel;
    }

    public void setImagenHotel(String imagenHotel) {
        this.imagenHotel = imagenHotel;
    }

    public String getDesde() {
        return desde;
    }

    public void setDesde(String desde) {
        this.desde = desde;
    }

    public String getHastaAsignado() {
        return hastaAsignado;
    }

    public void setHastaAsignado(String hastaAsignado) {
        this.hastaAsignado = hastaAsignado;
    }

    public String getRutaImagenDestino() {
        return rutaImagenDestino;
    }

    public void setRutaImagenDestino(String rutaImagenDestino) {
        this.rutaImagenDestino = rutaImagenDestino;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<SupriseInfo> getSorpresas() {
        return sorpresas;
    }

    public void setSorpresas(List<SupriseInfo> sorpresas) {
        this.sorpresas = sorpresas;
    }

    public String getError() {
        return error;
    }

    public String getMostrarDestino() {
        return mostrarDestino;
    }

    public void setMostrarDestino(String mostrarDestino) {
        this.mostrarDestino = mostrarDestino;
    }
}
