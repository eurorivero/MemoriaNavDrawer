package com.example.eurorivero.memoria.Partida;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;

import com.example.eurorivero.memoria.Configuraciones;
import com.example.eurorivero.memoria.R;
import com.example.eurorivero.memoria.Ranking;
import com.example.eurorivero.memoria.RankingDAO;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Fabiana Nazaret on 25/11/2017.
 */

public class PartidaController implements View.OnClickListener, Chronometer.OnChronometerTickListener, RVOnItemClick {

    private static final PartidaController ourInstance = new PartidaController();
    private PartidaModel pm;
    private PartidaView pv;
    private long previousStartTime;
    private long startTime;
    private long currentTime;
    private long segundos;
    private long tiempoInicioPartida;
    private FragmentActivity a;
    private SQLiteDatabase db;

    static PartidaController getInstance()
    {
        return ourInstance;
    }

    private PartidaController()
    {
        previousStartTime = 0;
        startTime = 0;
        Log.d("PartidaController","PartidaController builder executed.");
    }

    void setFA(FragmentActivity a)
    {
        this.a = a;
    }

    void setDB(SQLiteDatabase db)
    {
        this.db = db;
    }

    void setPm(PartidaModel pm)
    {
        this.pm = pm;
        pm.estadoPartida = PartidaModel.EstadoPartida.INICIAL;
        pm.inicializarTarjetas();
        pm.ocultarTarjetas();
        pm.resetVidas();
    }

    void setPv(PartidaView pv)
    {
        this.pv = pv;
        pv.setBotonIniciarTerminarListener(this);
        pv.setChronometerListener(this);
        pv.updateTarjetaRV();
        pv.setVidas(pm.getVidas());
        pm.setDificultad(Configuraciones.getDificultad());
        pv.setDificultad(pm.getDificultad());
    }

    PartidaModel getPm()
    {
        return(this.pm);
    }

    PartidaView getPv()
    {
        return(this.pv);
    }

    private void iniciarInspeccion()
    {
        pm.estadoPartida = PartidaModel.EstadoPartida.INSPECCION;
        pv.setTextBotonIniciarTerminar(R.string.Terminar);

        pm.resetVidas();
        pv.setVidas(pm.getVidas());
        pv.setDificultad(pm.getDificultad());
        pm.setTimeout(Configuraciones.getDificultad());
        pm.desordenarTarjetas();
        pm.mostrarTarjetas();
        pv.updateTarjetaRV();
        segundos = 0;
        previousStartTime = startTime;
        startTime = pv.startChronometerAsTimer(pm.getTimeout());
    }

    private void iniciarPartida()
    {
        pm.estadoPartida = PartidaModel.EstadoPartida.CORRIENDO;
        pv.stopChronometer();
        pm.ocultarTarjetas();
        pv.updateTarjetaRV();
        pm.contTjtasMostradas = 0;
        segundos=0;
        previousStartTime = startTime;
        startTime = pv.startChronometer(0);
        tiempoInicioPartida = startTime;


    }

    private void reiniciarPartida()
    {
        pm.estadoPartida = PartidaModel.EstadoPartida.INICIAL;
        pv.setTextBotonIniciarTerminar(R.string.Iniciar);
        pm.ocultarTarjetas();
        pv.updateTarjetaRV();
        pm.resetVidas();
        pv.setVidas(pm.getVidas());
        pm.setDificultad(Configuraciones.getDificultad());
        pv.setDificultad(pm.getDificultad());
        pv.stopChronometer();
    }

    void reanudarPartida()
    {
        pv.updateTarjetaRV();

        if(pm.estadoPartida == PartidaModel.EstadoPartida.INSPECCION)
        {
            previousStartTime = startTime;
            startTime = pv.startChronometerAsTimer(pm.getTimeout()-(int)segundos);
        }
        else if(pm.estadoPartida == PartidaModel.EstadoPartida.CORRIENDO ||
                pm.estadoPartida == PartidaModel.EstadoPartida.MOSTRANDO_TARJETAS_DESIGUALES)
        {
            previousStartTime = startTime;
            startTime = pv.startChronometer((int)segundos);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.bIniciarTerminar:
                if(pm.estadoPartida == PartidaModel.EstadoPartida.INICIAL)
                {
                    iniciarInspeccion();
                }
                else if(pm.estadoPartida == PartidaModel.EstadoPartida.CORRIENDO ||
                        pm.estadoPartida == PartidaModel.EstadoPartida.INSPECCION ||
                        pm.estadoPartida == PartidaModel.EstadoPartida.MOSTRANDO_TARJETAS_DESIGUALES ||
                        pm.estadoPartida == PartidaModel.EstadoPartida.TERMINADA_FRACASO)
                {
                    reiniciarPartida();
                }
                else if(pm.estadoPartida == PartidaModel.EstadoPartida.TERMINADA_EXITO)
                {
                    reiniciarPartida();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onChronometerTick(Chronometer chronometer)
    {
        if(startTime>previousStartTime)
        {
            segundos++;
            Log.d("OnChronometerTick","segundos: "+segundos+" estado: "+pm.estadoPartida.toString()+" Ch: "+chronometer.hashCode());
            currentTime = SystemClock.elapsedRealtime();
            if(pm.estadoPartida == PartidaModel.EstadoPartida.INSPECCION && segundos>=pm.getTimeout())
            {
                iniciarPartida();
            }
        }
    }

    @Override
    public void onItemClick(int position)
    {
        Tarjeta t = pm.getAlTarjetas().get(position);

        if(pm.estadoPartida == PartidaModel.EstadoPartida.CORRIENDO)
        {
            if(t.getEstado()== Tarjeta.TarjetaEstado.OCULTA)
            {
                t.setEstado(Tarjeta.TarjetaEstado.VISIBLE);
                pv.updateTarjetaRV();
                pm.contTjtasMostradas++;
                switch(pm.contTjtasMostradas)
                {
                    case 1:
                        pm.setPosTarjetasSeleccionadas(0,position);
                        break;
                    case 2:
                        pm.setPosTarjetasSeleccionadas(1,position);
                        if(!pm.verificarCoincidencia())
                        {
                            pm.quitarVida();
                            pv.setVidas(pm.getVidas());
                            if(pm.getVidas()==0)
                            {
                                pm.estadoPartida = PartidaModel.EstadoPartida.TERMINADA_FRACASO;
                                pv.stopChronometer();
                                pv.setTextBotonIniciarTerminar(R.string.Reiniciar);
                            }
                            else
                            {
                                pm.estadoPartida = PartidaModel.EstadoPartida.MOSTRANDO_TARJETAS_DESIGUALES;
                            }
                        }
                        else
                        {
                            if(pm.todasTarjetasVisibles())
                            {
                                pm.estadoPartida = PartidaModel.EstadoPartida.TERMINADA_EXITO;
                                pv.stopChronometer();
                                pv.setTextBotonIniciarTerminar(R.string.Reiniciar);

                                Ranking rankingPartida = new Ranking();
                                rankingPartida.setDificultad(pm.getDificultad());
                                rankingPartida.setDuracion((SystemClock.elapsedRealtime()-tiempoInicioPartida)/1000);
                                rankingPartida.setFechaHora(DateFormat.getDateTimeInstance().format(new Date()));
                                rankingPartida.setVidas(pm.getVidas());
                                rankingPartida.setPosicion(2);

                                RankingDAO rankingDAO = new RankingDAO(db);

                                rankingPartida.setId(rankingDAO.save(rankingPartida));

                                Bundle args = new Bundle();
                                args.putInt("Posicion",rankingPartida.getPosicion());
                                args.putString("Dificultad", rankingPartida.getDificultad().toString());
                                args.putLong("Duracion",rankingPartida.getDuracion());
                                args.putInt("Vidas",rankingPartida.getVidas());
                                args.putString("FechaHora",rankingPartida.getFechaHora());

                                Fragment fragment = new ResumenFragment();
                                fragment.setArguments(args);
                                FragmentManager fragmentManager = a.getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.contenedor, fragment).commit();

                            }
                        }
                        pm.contTjtasMostradas=0;
                        break;
                    default:
                }
            }
        }
        else if(pm.estadoPartida == PartidaModel.EstadoPartida.MOSTRANDO_TARJETAS_DESIGUALES)
        {
            if(t.getEstado()== Tarjeta.TarjetaEstado.OCULTA)
            {
                pm.getAlTarjetas().get(pm.getPosTarjetaSeleccionada(0)).setEstado(Tarjeta.TarjetaEstado.OCULTA);
                pm.getAlTarjetas().get(pm.getPosTarjetaSeleccionada(1)).setEstado(Tarjeta.TarjetaEstado.OCULTA);
                t.setEstado(Tarjeta.TarjetaEstado.VISIBLE);
                pm.setPosTarjetasSeleccionadas(0, position);
                pm.contTjtasMostradas=1;
                pm.estadoPartida = PartidaModel.EstadoPartida.CORRIENDO;
                pv.updateTarjetaRV();
            }
        }
    }
}