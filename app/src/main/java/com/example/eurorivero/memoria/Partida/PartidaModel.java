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

    private static Tarjeta[][] tarjetas = new Tarjeta[FILAS][COLUMNAS];
    int contTjtasMostradas;
    private static int filaTarjetaSeleccionada1;
    private static int colTarjetaSeleccionada1;
    private View viewTarjetaSeleccionada1;
    private static int filaTarjetaSeleccionada2;
    private static int colTarjetaSeleccionada2;
    private View viewTarjetaSeleccionada2;
    private static Configuraciones.Dificultad dificultad;

    private ArrayList<Tarjeta> alTarjetas = new ArrayList<>();
    private View pfView;
    private int[] posTarjetasSeleccionadas = new int[2];

    static PartidaModel getInstance() {
        return ourInstance;
    }

    private PartidaModel()
    {
        Log.d("PartidaModel","PartidaModel builder executed");
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

        Tarjeta.backImageResource = pfView.getResources().getIdentifier("question_icon", "drawable","com.example.eurorivero.memoria");
        for (int k = 0; k<TARJETAS; k++)
        {
            Tarjeta t = new Tarjeta();
            t.setEstado(Tarjeta.TarjetaEstado.OCULTA);
            t.setTipo(k/2);
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
            }
        }
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

    Tarjeta getTarjeta(int fila, int columna)
    {
        return(tarjetas[fila][columna]);
    }

    Tarjeta[][] getTarjetas()
    {
        return(tarjetas);
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

        for(Tarjeta t: alTarjetas)
        {
            t.setEstado(Tarjeta.TarjetaEstado.OCULTA);
        }
    }

    void mostrarTarjetas()
    {
        for (int i = 0; i < FILAS; i++)
        {
            for (int j = 0; j < COLUMNAS; j++)
            {
                tarjetas[i][j].setEstado(Tarjeta.TarjetaEstado.VISIBLE);
            }
        }

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
                timeout = 9;
                break;
            case NIVEL2:
                timeout = 6;
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

    void mostrarTarjeta(int f, int c)
    {
        tarjetas[f][c].setEstado(Tarjeta.TarjetaEstado.VISIBLE);
    }

    void ocultarTarjeta(int f, int c)
    {
        tarjetas[f][c].setEstado(Tarjeta.TarjetaEstado.OCULTA);
    }

    void setTarjetaSeleccionada(int index ,int f, int c, View v)
    {
        if(index == 0)
        {
            filaTarjetaSeleccionada1 = f;
            colTarjetaSeleccionada1 = c;
            viewTarjetaSeleccionada1 = v;
        }
        else if(index == 1)
        {
            filaTarjetaSeleccionada2 = f;
            colTarjetaSeleccionada2 = c;
            viewTarjetaSeleccionada2 = v;
        }
    }

    int  getFilaTarjetaSeleccionada(int index)
    {
        if(index==1)
        {
            return(filaTarjetaSeleccionada2);
        }
        else
        {
            return(filaTarjetaSeleccionada1);
        }
    }

    int getColTarjetaSeleccionadas(int index)
    {
        if(index==1)
        {
            return(colTarjetaSeleccionada2);
        }
        else
        {
            return(colTarjetaSeleccionada1);
        }
    }

    View getViewTarjetaSeleccionada(int index)
    {
        if(index==1)
        {
            return(viewTarjetaSeleccionada2);
        }
        else
        {
            return(viewTarjetaSeleccionada1);
        }
    }

    boolean verificarCoincidencia(int f2, int c2)
    {
        Tarjeta t1 = tarjetas[filaTarjetaSeleccionada1][colTarjetaSeleccionada1];
        Tarjeta t2 = tarjetas[f2][c2];
        return(t1.getIdImagen()==t2.getIdImagen());
    }

    boolean verificarCoincidencia()
    {
        return(alTarjetas.get(posTarjetasSeleccionadas[0])==alTarjetas.get(posTarjetasSeleccionadas[1]));
    }

    boolean todasLasTarjetasVisibles()
    {
        for (int i = 0; i < FILAS; i++)
        {
            for (int j = 0; j < COLUMNAS; j++)
            {
                if(tarjetas[i][j].getEstado()== Tarjeta.TarjetaEstado.OCULTA)
                {
                    return(false);
                }
            }
        }
        return(true);
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
}