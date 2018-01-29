package com.example.eurorivero.memoria.Partida;

/**
 * Created by euror on 28/01/2018.
 */

public class Tarjeta {

    public enum TarjetaEstado
    {
        OCULTA,
        VISIBLE
    }
    private TarjetaEstado estado;
    private int idImagen;

    public Tarjeta()
    {
        this.idImagen = 0;
        this.estado = TarjetaEstado.OCULTA;
    }

    public int getIdImagen()
    {
        return this.idImagen;
    }

    public void setIdImagen(int idImagen)
    {
        this.idImagen = idImagen;
    }

    public TarjetaEstado getEstado()
    {
        return this.estado;
    }

    public void setEstado(TarjetaEstado estado)
    {
        this.estado = estado;
    }
}
