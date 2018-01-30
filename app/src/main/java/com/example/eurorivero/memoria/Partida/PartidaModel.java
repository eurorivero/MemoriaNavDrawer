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
    private static final int MAX_VIDAS = 3;
    private static int timeoutPrevio;
    private static int timeout;
    private static final int FILAS = 4;
    private static final int COLUMNAS = 3;

    private static Tarjeta[][] tarjetas = new Tarjeta[FILAS][COLUMNAS];

    static PartidaModel getInstance() {
        return ourInstance;
    }

    private PartidaModel()
    {

    }

    void inicializarTarjetas()
    {
        for (int i = 0; i < FILAS; i++)
        {
            for (int j = 0; j < COLUMNAS; j++)
            {
                tarjetas[i][j] = new Tarjeta();
                tarjetas[i][j].setEstado(Tarjeta.TarjetaEstado.OCULTA);
                tarjetas[i][j].setIdImagen(1);
            }
        }
    }

    void desordenarTarjetas()
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
                do
                {
                    tarjetas[i][j].setIdImagen(rn.nextInt(6));
                }
                while (contadores[tarjetas[i][j].getIdImagen()]==0); // Se acabaron los tokens disponibles para ese tipo de tarjeta?
                //Log.d("PartidaModel","PartidaModel " + "; i = " + i + "; j = " + j + "; Tipo Tarjeta =" + tarjetas[i][j]);

                contadores[tarjetas[i][j].getIdImagen()]--;
                tarjetas[i][j].setEstado(Tarjeta.TarjetaEstado.OCULTA);

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

    void resetVidas()
    {
        this.vidas=MAX_VIDAS;
    }

    public Tarjeta getTarjeta(int fila, int columna)
    {
        return(tarjetas[fila][columna]);
    }

    void ocultarTarjetas()
    {
        for (int i = 0; i < FILAS; i++)
        {
            for (int j = 0; j < COLUMNAS; j++)
            {
                tarjetas[i][j].setEstado(Tarjeta.TarjetaEstado.OCULTA);
            }
        }
    }
}
