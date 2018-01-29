package com.example.eurorivero.memoria.Partida;

import java.util.Random;

public class PartidaModel {

    /**
     * Created by Fabiana Nazaret on 24/11/2017.
     */

    public enum EstadoPartida
    {
        INICIAL,
        INSPECCION,
        CORRIENDO,
        TERMINADA
    }
    EstadoPartida estadoPartida;

    private static final PartidaModel ourInstance = new PartidaModel();
    private static int vidas;
    private static int timeoutPrevio;
    private static int timeout;
    private static final int FILAS = 4;
    private static final int COLUMNAS = 3;

    private static Tarjeta[][] tarjetas2 = new Tarjeta[FILAS][COLUMNAS];

    static PartidaModel getInstance() {
        return ourInstance;
    }

    private PartidaModel()
    {

    }

    void configurarPartida(int vidas, int timeoutPrevio)
    {
        this.vidas = vidas;
        this.timeoutPrevio = timeoutPrevio;

        inicializarTarjetas();
    }

    private void inicializarTarjetas()
    {
        int[] contadores = new int[6];
        Random rn = new Random();

        for (int i = 0; i < 6; i++)
        {
            contadores[i] = 2;
        }

        for (int i = 0; i < FILAS; i++)
        {
            for (int j = 0; j < COLUMNAS; j++)
            {
                tarjetas2[i][j] = new Tarjeta();

                do
                {
                    tarjetas2[i][j].setIdImagen(rn.nextInt(6));
                }
                while (contadores[tarjetas2[i][j].getIdImagen()]==0); // Se acabaron los tokens disponibles para ese tipo de tarjeta?
                //Log.d("PartidaModel","PartidaModel " + "; i = " + i + "; j = " + j + "; Tipo Tarjeta =" + tarjetas2[i][j]);

                contadores[tarjetas2[i][j].getIdImagen()]--;
                tarjetas2[i][j].setEstado(Tarjeta.TarjetaEstado.OCULTA);

            }
        }
    }

    public void quitarVida()
    {
        this.vidas--;
    }

    public int getVidas()
    {
        return vidas;
    }

    public Tarjeta getTarjeta(int fila, int columna)
    {
        return(tarjetas2[fila][columna]);
    }

}
