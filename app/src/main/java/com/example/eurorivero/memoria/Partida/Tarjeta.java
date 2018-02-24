package com.example.eurorivero.memoria.Partida;

/**
 * Created by euror on 28/01/2018.
 */

public class Tarjeta {
    public static int backImageResource;
    public enum TarjetaEstado
    {
        OCULTA,
        VISIBLE
    }
    private TarjetaEstado estado;
    private int idImagen;
    private int tipo;
    private int frontImageResource;
    private int currentImageResource;


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
        if(estado == TarjetaEstado.OCULTA)
            currentImageResource = backImageResource;
        else if(estado == TarjetaEstado.VISIBLE)
            currentImageResource = frontImageResource;
    }

    public void setTipo(int tipo){this.tipo = tipo;}

    public int getTipo(){return this.tipo;}

    int getFrontImageResource(){return this.frontImageResource;}

    void setFrontImageResource(int frontImageResource){this.frontImageResource = frontImageResource;}

    int getCurrentImageResource(){return this.currentImageResource;}

}