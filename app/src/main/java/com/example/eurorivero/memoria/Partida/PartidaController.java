package com.example.eurorivero.memoria.Partida;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;

import com.example.eurorivero.memoria.Configuraciones;
import com.example.eurorivero.memoria.R;

/**
 * Created by Fabiana Nazaret on 25/11/2017.
 */

public class PartidaController implements View.OnClickListener, Chronometer.OnChronometerTickListener{

    private PartidaModel pm;
    private PartidaView pv;

    PartidaController(PartidaModel pm, PartidaView pv)
    {
        this.pm = pm;
        this.pv = pv;

        pv.setTarjetasListeners(this);
        pv.setBotonIniciarTerminarListener(this);
        pv.setChronometerListener(this);

        pm.estadoPartida = PartidaModel.EstadoPartida.INICIAL;
        pm.inicializarTarjetas();
        pm.ocultarTarjetas();
        pv.ocultarTarjetas();
        pm.resetVidas();
        pv.setVidas(pm.getVidas());
        pv.setDificultad(Configuraciones.getDificultad());
        Log.d("PartidaController","PartidaController builder executed.");
    }

    @Override
    public void onClick(View v)
    {
        int f,c;
        Tarjeta t;
        boolean clickSobreTarjeta;

        switch(v.getId())
        {
            case R.id.iv0_0:
                f = 0;
                c = 0;
                clickSobreTarjeta = true;
                break;
            case R.id.iv0_1:
                f = 0;
                c = 1;
                clickSobreTarjeta = true;
                break;
            case R.id.iv0_2:
                f = 0;
                c = 2;
                clickSobreTarjeta = true;
                break;
            case R.id.iv1_0:
                f = 1;
                c = 0;
                clickSobreTarjeta = true;
                break;
            case R.id.iv1_1:
                f = 1;
                c = 1;
                clickSobreTarjeta = true;
                break;
            case R.id.iv1_2:
                f = 1;
                c = 2;
                clickSobreTarjeta = true;
                break;
            case R.id.iv2_0:
                f = 2;
                c = 0;
                clickSobreTarjeta = true;
                break;
            case R.id.iv2_1:
                f = 2;
                c = 1;
                clickSobreTarjeta = true;
                break;
            case R.id.iv2_2:
                f = 2;
                c = 2;
                clickSobreTarjeta = true;
                break;
            case R.id.iv3_0:
                f = 3;
                c = 0;
                clickSobreTarjeta = true;
                break;
            case R.id.iv3_1:
                f = 3;
                c = 1;
                clickSobreTarjeta = true;
                break;
            case R.id.iv3_2:
                f = 3;
                c = 2;
                clickSobreTarjeta = true;
                break;
            case R.id.bIniciarTerminar:
                f=0;
                c=0;
                clickSobreTarjeta = false;
                if(pm.estadoPartida== PartidaModel.EstadoPartida.INICIAL)
                {
                    iniciarPartida();
                }
                else if(pm.estadoPartida== PartidaModel.EstadoPartida.CORRIENDO)
                {
                    terminarPartida();
                }
                break;
            default:
                f=0;
                c=0;
                clickSobreTarjeta = false;
                break;
        }

        if(clickSobreTarjeta && pm.estadoPartida == PartidaModel.EstadoPartida.CORRIENDO)
        {
            t = pm.getTarjeta(f,c);
            if(t.getEstado()== Tarjeta.TarjetaEstado.OCULTA)
            {
                t.setEstado(Tarjeta.TarjetaEstado.VISIBLE);
                pv.mostrarTarjeta(t.getIdImagen(),v);
            }
            else if(t.getEstado()== Tarjeta.TarjetaEstado.VISIBLE)
            {
                t.setEstado(Tarjeta.TarjetaEstado.OCULTA);
                pv.ocultarTarjeta(v);
            }
        }
    }

    private void iniciarPartida()
    {
        pm.estadoPartida = PartidaModel.EstadoPartida.CORRIENDO;
        pv.setTextBotonIniciarTerminar("Terminar");
        pm.desordenarTarjetas();
        pm.resetVidas();
        pv.setVidas(pm.getVidas());
        pv.setDificultad(Configuraciones.getDificultad());
        if(!pv.isChronometerBusy())
        {
            //pv.startChronometer();
            pv.startTimer();
        }
    }

    private void terminarPartida()
    {
        pm.estadoPartida = PartidaModel.EstadoPartida.INICIAL;
        pv.setTextBotonIniciarTerminar("Iniciar");
        pm.ocultarTarjetas();
        pv.ocultarTarjetas();
        pm.resetVidas();
        pv.setVidas(pm.getVidas());
        pv.setDificultad(Configuraciones.getDificultad());
        if(pv.isChronometerBusy())
        {
            //pv.stopChronometer();
            pv.stopTimer();
        }
    }

    @Override
    public void onChronometerTick(Chronometer chronometer)
    {
        int segs = pv.getChronometerSeconds();

        Log.d("PartidaController","onChronometerTick: "+segs);
        if (!pv.isTimerOrChronometer() && segs == 9)
        {
            terminarPartida();
        }
        else if (pv.isTimerOrChronometer() && segs == 0)
        {
            terminarPartida();
        }
    }
}