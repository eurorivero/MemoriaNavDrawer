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
    private int frontImageResource;
    private int currentImageResource;


    public Tarjeta()
    {
        this.estado = TarjetaEstado.OCULTA;
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

    int getFrontImageResource(){return this.frontImageResource;}

    void setFrontImageResource(int frontImageResource){this.frontImageResource = frontImageResource;}

    int getCurrentImageResource(){return this.currentImageResource;}

}