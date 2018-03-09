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
import com.example.eurorivero.memoria.Ranking.Ranking;
import com.example.eurorivero.memoria.Ranking.RankingDAO;

import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Fabiana Nazaret on 25/11/2017.
 */

public class PartidaController implements View.OnClickListener, RVOnItemClick, Runnable {

    private static final PartidaController ourInstance = new PartidaController();
    private PartidaModel pm;
    private PartidaView pv;

    private Chronometer cronometro;
    private long startTime;
    private long previousTime, currentTime, elapsedTime;
    private long tsStart,tsStart2,tsTick,etsls, tsPrevSec;

    private long tiempoInicioPartida;
    private FragmentActivity a;
    private SQLiteDatabase db;
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
    private ScheduledFuture f;

    static PartidaController getInstance()
    {
        return ourInstance;
    }

    private PartidaController()
    {
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

    void setSTPE(ScheduledThreadPoolExecutor sch){this.scheduledThreadPoolExecutor = sch;}

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
        pv.updateTarjetaRV();
        pv.setVidas(pm.getVidas());
        pm.setDificultad(Configuraciones.getDificultad());
        pv.setDificultad(pm.getDificultad());
        cronometro = pv.getCronometro();
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

        pm.desordenarTarjetas();
        pm.mostrarTarjetas();
        pv.updateTarjetaRV();

        pv.setDificultad(pm.getDificultad());
        pm.setTimeout(Configuraciones.getDificultad());

        startTime = SystemClock.elapsedRealtime();
        tsStart = SystemClock.elapsedRealtime();
        tsPrevSec = tsStart;
        previousTime = startTime;
        elapsedTime = 0;
        f = scheduledThreadPoolExecutor.scheduleAtFixedRate(this,0,100, TimeUnit.MILLISECONDS);
    }

    private void iniciarPartida()
    {
        pm.estadoPartida = PartidaModel.EstadoPartida.CORRIENDO;

        pm.ocultarTarjetas();

        pv.setIdBlah(1);
        a.runOnUiThread(pv);

        pm.contTjtasMostradas = 0;

        tiempoInicioPartida = startTime;
        startTime = SystemClock.elapsedRealtime();
        tsStart = SystemClock.elapsedRealtime();
        tsPrevSec = tsStart;
        previousTime = startTime;
        elapsedTime = 0;
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

    }

    void reanudarPartida()
    {
        pv.updateTarjetaRV();
        cronometro = pv.getCronometro();
        previousTime = SystemClock.elapsedRealtime();
        f = scheduledThreadPoolExecutor.scheduleAtFixedRate(this,0,100, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onClick(View v)
    {
        Log.d("onClick","EstadoPartida = "+pm.estadoPartida);
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
                        pm.estadoPartida == PartidaModel.EstadoPartida.TERMINADA_FRACASO ||
                        pm.estadoPartida == PartidaModel.EstadoPartida.TERMINADA_EXITO) {
                    reiniciarPartida();
                }
                break;
            default:
                break;
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
                                f.cancel(false);
                                pv.setTextBotonIniciarTerminar(R.string.Reiniciar);
                            }
                            else
                            {
                                tsStart2 = SystemClock.elapsedRealtime();
                                pm.estadoPartida = PartidaModel.EstadoPartida.MOSTRANDO_TARJETAS_DESIGUALES;
                            }
                        }
                        else
                        {
                            if(pm.todasTarjetasVisibles())
                            {
                                pm.estadoPartida = PartidaModel.EstadoPartida.TERMINADA_EXITO;
                                f.cancel(false);
                                pv.setTextBotonIniciarTerminar(R.string.Reiniciar);

                                Ranking rankingPartida = new Ranking();
                                rankingPartida.setDificultad(pm.getDificultad());
                                rankingPartida.setDuracion((SystemClock.elapsedRealtime()-tiempoInicioPartida)/1000);
                                rankingPartida.setFechaHora(DateFormat.getDateTimeInstance().format(new Date()));
                                rankingPartida.setVidas(pm.getVidas());
                                rankingPartida.setPosicion(2);

                                RankingDAO rankingDAO = new RankingDAO(db);

                                /* Prueba de update y delete de RankingDAO */
                                /*
                                List<Ranking> rankings = rankingDAO.getAll();
                                Ranking rtbd = rankings.get(0);
                                rankingDAO.delete(rtbd);
                                Ranking rtbu = rankings.get(1);
                                rtbu.setDuracion(99);
                                rtbu.setDificultad(Configuraciones.Dificultad.NIVEL3);
                                rtbu.setPosicion(9);
                                rankingDAO.update(rtbu);
                                /* Fin de prueba*/

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

    @Override
    public void run() {
/*
        currentTime = SystemClock.elapsedRealtime();

        if((currentTime-previousTime)>=1000)
        {
            elapsedTime = elapsedTime + currentTime-previousTime;
            if(pm.estadoPartida == PartidaModel.EstadoPartida.INSPECCION && elapsedTime > (pm.getTimeout()*1000))
            {
                iniciarPartida();
            }
            previousTime = currentTime;
        }
/*
        if(pm.estadoPartida== PartidaModel.EstadoPartida.MOSTRANDO_TARJETAS_DESIGUALES)
        {
            elapsedTimeDesiguales = elapsedTimeDesiguales + currentTime - previousTimeDesiguales;
            if(elapsedTimeDesiguales > 2000)
            {
                pm.estadoPartida = PartidaModel.EstadoPartida.CORRIENDO;
            }
        }
*/
/*
        Log.d("run","Estado: "+pm.estadoPartida.toString()+"\nst = "+startTime+"\nct = "+currentTime+"\npt = "+previousTime+"\net = "+ elapsedTime);

        if(pm.estadoPartida == PartidaModel.EstadoPartida.INSPECCION) {
            pv.setTiempo(((long)pm.getTimeout()*1000)-elapsedTime);
        }
        else if(pm.estadoPartida == PartidaModel.EstadoPartida.CORRIENDO || pm.estadoPartida == PartidaModel.EstadoPartida.MOSTRANDO_TARJETAS_DESIGUALES)
        {
            pv.setTiempo(elapsedTime);
        }
        pv.setIdBlah(0);
        a.runOnUiThread(pv);
*/
        tsTick = SystemClock.elapsedRealtime();
        Log.d("run","Estado: "+pm.estadoPartida.toString()+"\ntsStart  = "+tsStart+"\ntsTick   = "+tsTick+"\ntsStart2 = "+tsStart2);
        if(pm.estadoPartida == PartidaModel.EstadoPartida.INSPECCION){
            if( (tsTick-tsStart) > (pm.getTimeout()*1000) )
            {
                iniciarPartida();
            }
            pv.setTiempo(((long)pm.getTimeout()*1000)-(tsTick-tsStart));
        }
        else if(pm.estadoPartida == PartidaModel.EstadoPartida.CORRIENDO){
            pv.setTiempo(tsTick-tsStart);
        }
        else if(pm.estadoPartida == PartidaModel.EstadoPartida.MOSTRANDO_TARJETAS_DESIGUALES){
            pv.setTiempo(tsTick-tsStart);
            if((tsTick-tsStart2)>2000){
                pm.getAlTarjetas().get(pm.getPosTarjetaSeleccionada(0)).setEstado(Tarjeta.TarjetaEstado.OCULTA);
                pm.getAlTarjetas().get(pm.getPosTarjetaSeleccionada(1)).setEstado(Tarjeta.TarjetaEstado.OCULTA);
                pm.setPosTarjetasSeleccionadas(0,0);
                pm.setPosTarjetasSeleccionadas(1,0);
                pm.contTjtasMostradas=0;
                pv.setIdBlah(1);
                a.runOnUiThread(pv);
                pm.estadoPartida = PartidaModel.EstadoPartida.CORRIENDO;
            }
        }
        etsls = tsTick - tsPrevSec;
        if(etsls>1000){
            pv.setIdBlah(0);
            a.runOnUiThread(pv);
            tsPrevSec = tsTick;
        }
    }
}