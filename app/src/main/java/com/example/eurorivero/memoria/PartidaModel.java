package com.example.eurorivero.memoria;

/**
 * Created by Fabiana Nazaret on 24/11/2017.
 */

public class PartidaModel {

    private static final PartidaModel ourInstance = new PartidaModel();
    private static int vidas;
    private static int timeout;
    private static int timeoutPrevio;
    private static int segundosTiempoJuego;

    static PartidaModel getInstance() {
        return ourInstance;
    }

    public PartidaModel()
    {

    }

    public void configurarPartida(int vidas, int timeoutPrevio, int timeout)
    {
        this.vidas = vidas;
        this.timeoutPrevio = timeoutPrevio;
        this.timeout = timeout;
    }

    public void comenzarPartida()
    {

    }

    public void terminarPartida()
    {

    }

    public void quitarVida()
    {
        this.vidas--;
    }

    public boolean QuedanVidas()
    {
        if(vidas>0)
            return true;
        else
            return false;
    }

}
