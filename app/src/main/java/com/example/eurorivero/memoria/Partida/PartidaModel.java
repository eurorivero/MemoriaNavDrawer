package com.example.eurorivero.memoria.Partida;

import android.util.Log;
import android.view.View;

import com.example.eurorivero.memoria.Configuraciones;

import java.util.ArrayList;
import java.util.Collections;
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
        MOSTRANDO_TARJETAS_DESIGUALES,
        TERMINADA_EXITO,
        TERMINADA_FRACASO,
    }
    EstadoPartida estadoPartida;

    private static final PartidaModel ourInstance = new PartidaModel();
    private static int vidas;
    private static final int MAX_VIDAS = 3;
    private static int timeout;
    static final int FILAS = 4;
    static final int COLUMNAS = 3;
    static final int TIPOS = (FILAS * COLUMNAS) / 2;
    static final int TARJETAS = FILAS * COLUMNAS;

    int contTjtasMostradas;
    private static Configuraciones.Dificultad dificultad;

    private ArrayList<Tarjeta> alTarjetas = new ArrayList<>();
    private View pfView;
    private int[] posTarjetasSeleccionadas = new int[2];

    static PartidaModel getInstance() {
        return ourInstance;
    }

    private PartidaModel()
    {
    }

    void inicializarTarjetas()
    {
        Tarjeta.backImageResource = pfView.getResources().getIdentifier("question_icon", "drawable","com.example.eurorivero.memoria");
        for (int k = 0; k<TARJETAS; k++)
        {
            Tarjeta t = new Tarjeta();
            t.setEstado(Tarjeta.TarjetaEstado.OCULTA);
            switch(k/2)
            {
                case 0:
                    t.setFrontImageResource(pfView.getResources().getIdentifier("img_1", "drawable","com.example.eurorivero.memoria"));
                    break;
                case 1:
                    t.setFrontImageResource(pfView.getResources().getIdentifier("img_2", "drawable","com.example.eurorivero.memoria"));
                    break;
                case 2:
                    t.setFrontImageResource(pfView.getResources().getIdentifier("img_3", "drawable","com.example.eurorivero.memoria"));
                    break;
                case 3:
                    t.setFrontImageResource(pfView.getResources().getIdentifier("img_4", "drawable","com.example.eurorivero.memoria"));
                    break;
                case 4:
                    t.setFrontImageResource(pfView.getResources().getIdentifier("img_5", "drawable","com.example.eurorivero.memoria"));
                    break;
                case 5:
                    t.setFrontImageResource(pfView.getResources().getIdentifier("img_6", "drawable","com.example.eurorivero.memoria"));
                    break;
                default:
                    t.setFrontImageResource(pfView.getResources().getIdentifier("question_icon", "drawable","com.example.eurorivero.memoria"));
                    break;
            }
            alTarjetas.add(t);
        }
    }

    void desordenarTarjetas()
    {
        Collections.shuffle(alTarjetas);
    }

    void quitarVida()
    {
        vidas--;
    }

    int getVidas()
    {
        return vidas;
    }

    void resetVidas()
    {
        vidas=MAX_VIDAS;
    }

    void ocultarTarjetas()
    {
        for(Tarjeta t: alTarjetas)
        {
            t.setEstado(Tarjeta.TarjetaEstado.OCULTA);
        }
    }

    void mostrarTarjetas()
    {
        for(Tarjeta t: alTarjetas)
        {
            t.setEstado(Tarjeta.TarjetaEstado.VISIBLE);
        }
    }

    void setTimeout(Configuraciones.Dificultad d)
    {
        switch(d)
        {
            case NIVEL1:
                timeout = 10;
                break;
            case NIVEL2:
                timeout = 5;
                break;
            case NIVEL3:
                timeout = 3;
                break;
            default:
                break;
        }
    }

    int getTimeout()
    {
        return(timeout);
    }

    boolean verificarCoincidencia()
    {
        return(alTarjetas.get(posTarjetasSeleccionadas[0]).getCurrentImageResource()==alTarjetas.get(posTarjetasSeleccionadas[1]).getCurrentImageResource());
    }

    boolean todasTarjetasVisibles()
    {
        for(Tarjeta t: alTarjetas)
        {
            if(t.getEstado()== Tarjeta.TarjetaEstado.OCULTA)
                return(false);
        }
        return(true);
    }

    Configuraciones.Dificultad getDificultad()
    {
        return dificultad;
    }

    void setDificultad(Configuraciones.Dificultad dificultad)
    {
        this.dificultad = dificultad;
    }

    ArrayList<Tarjeta> getAlTarjetas(){return this.alTarjetas;}

    void setPfView(View pfView){this.pfView = pfView;}

    public void setPosTarjetasSeleccionadas(int index, int position) {
        this.posTarjetasSeleccionadas[index] = position;
    }

    public int getPosTarjetaSeleccionada(int index){return this.posTarjetasSeleccionadas[index];}
}