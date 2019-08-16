package com.puma.nextwhere.database.tables;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rajesh on 17/11/17.
 */
@Entity
public class UnlockSurpriseData {

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UnlockSurpriseData))
            return false;

        UnlockSurpriseData unlockSupriseData = (UnlockSurpriseData) obj;
        return unlockSupriseData.nombrePrenda.equalsIgnoreCase(nombrePrenda)
                && unlockSupriseData.categoriaPrenda.equalsIgnoreCase(categoriaPrenda)
                && unlockSupriseData.descripcionPrenda.equalsIgnoreCase(descripcionPrenda);
    }

    @Override
    public int hashCode() {
        return nombrePrenda.hashCode();
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")

    public int uid;

    @ColumnInfo(name = "nombre_prenda")
    @SerializedName("nombre_prenda")
    @Expose
    private String nombrePrenda;

    @ColumnInfo(name = "categoria_prenda")
    @SerializedName("categoria_prenda")
    @Expose
    private String categoriaPrenda;

    @ColumnInfo(name = "categoria_otra_prenda")
    @SerializedName("categoria_otra_prenda")
    @Expose
    private String categoriaOtraPrenda;

    @ColumnInfo(name = "descripcion_prenda")
    @SerializedName("descripcion_prenda")
    @Expose
    private String descripcionPrenda;

    @ColumnInfo(name = "imagen_prenda")
    @SerializedName("imagen_prenda")
    @Expose
    private String imagenPrenda;

    @ColumnInfo(name = "latitud")
    @SerializedName("latitud")
    @Expose
    private String latitud;

    @ColumnInfo(name = "longitud")
    @SerializedName("longitud")
    @Expose
    private String longitud;

    @ColumnInfo(name = "respuesta")
    @SerializedName("respuesta")
    @Expose
    private String respuesta;

    public String getNombrePrenda() {
        return nombrePrenda;
    }

    public void setNombrePrenda(String nombrePrenda) {
        this.nombrePrenda = nombrePrenda;
    }

    public String getCategoriaPrenda() {
        return categoriaPrenda;
    }

    public void setCategoriaPrenda(String categoriaPrenda) {
        this.categoriaPrenda = categoriaPrenda;
    }

    public String getCategoriaOtraPrenda() {
        return categoriaOtraPrenda;
    }

    public void setCategoriaOtraPrenda(String categoriaOtraPrenda) {
        this.categoriaOtraPrenda = categoriaOtraPrenda;
    }

    public String getDescripcionPrenda() {
        return descripcionPrenda;
    }

    public void setDescripcionPrenda(String descripcionPrenda) {
        this.descripcionPrenda = descripcionPrenda;
    }

    public String getImagenPrenda() {
        return imagenPrenda;
    }

    public void setImagenPrenda(String imagenPrenda) {
        this.imagenPrenda = imagenPrenda;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public String toString() {
        return nombrePrenda == null ? "" : nombrePrenda;
    }
}
