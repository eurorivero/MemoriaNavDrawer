package com.example.eurorivero.memoria.Partida;

import android.os.SystemClock;
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
    private long previousStartTime;
    private long startTime;
    private long currentTime;

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
        previousStartTime = 0;
        startTime = 0;
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
                    //Aquí se inicia el otro fragment con el resumen.
                }
                break;
            default:
                f=0;
                c=0;
                clickSobreTarjeta = false;
                break;
        }

        t = pm.getTarjeta(f,c);
        if(clickSobreTarjeta && pm.estadoPartida == PartidaModel.EstadoPartida.CORRIENDO)
        {
            if(t.getEstado()== Tarjeta.TarjetaEstado.OCULTA)
            {
                pm.mostrarTarjeta(f,c);
                pv.mostrarTarjeta(t.getIdImagen(),v);
                pm.contTjtasMostradas++;
                switch(pm.contTjtasMostradas)
                {
                    case 1:
                        pm.setTarjetaSeleccionada(0,f,c,v);
                        break;
                    case 2:
                        if(!pm.verificarCoincidencia(f,c))
                        {
                            pm.setTarjetaSeleccionada(1,f,c,v);
                            pm.quitarVida();
                            pv.setVidas(pm.getVidas());
                            if(pm.getVidas()==0)
                            {
                                pm.estadoPartida = PartidaModel.EstadoPartida.TERMINADA_FRACASO;
                                pv.stopChronometer();
                                pv.setTextBotonIniciarTerminar("Reiniciar");
                            }
                            else
                            {
                                pm.estadoPartida = PartidaModel.EstadoPartida.MOSTRANDO_TARJETAS_DESIGUALES;
                            }
                        }
                        else
                        {
                            if(pm.todasLasTarjetasVisibles())
                            {
                                pm.estadoPartida = PartidaModel.EstadoPartida.TERMINADA_EXITO;
                                pv.stopChronometer();
                                pv.setTextBotonIniciarTerminar("Iniciar");
                            }
                        }
                        pm.contTjtasMostradas=0;
                        break;
                    default:

                }
            }
        }
        else if(clickSobreTarjeta && pm.estadoPartida == PartidaModel.EstadoPartida.MOSTRANDO_TARJETAS_DESIGUALES)
        {
            //t = pm.getTarjeta(f,c);
            if(t.getEstado()== Tarjeta.TarjetaEstado.OCULTA)
            {
                pm.ocultarTarjeta(pm.getFilaTarjetaSeleccionada(0), pm.getColTarjetaSeleccionadas(0));
                pv.ocultarTarjeta(pm.getViewTarjetaSeleccionada(0));
                pm.ocultarTarjeta(pm.getFilaTarjetaSeleccionada(1), pm.getColTarjetaSeleccionadas(1));
                pv.ocultarTarjeta(pm.getViewTarjetaSeleccionada(1));
                pm.mostrarTarjeta(f,c);
                pv.mostrarTarjeta(t.getIdImagen(),v);
                pm.setTarjetaSeleccionada(0,f,c,v);
                pm.contTjtasMostradas=1;
                pm.estadoPartida = PartidaModel.EstadoPartida.CORRIENDO;
            }
        }
        Tarjeta t1 = pm.getTarjeta(pm.getFilaTarjetaSeleccionada(0),pm.getColTarjetaSeleccionadas(0));
        Tarjeta t2 = pm.getTarjeta(pm.getFilaTarjetaSeleccionada(1),pm.getColTarjetaSeleccionadas(1));
        int f1 = pm.getFilaTarjetaSeleccionada(0);
        int c1 = pm.getColTarjetaSeleccionadas(0);
        int f2 = pm.getFilaTarjetaSeleccionada(1);
        int c2 = pm.getColTarjetaSeleccionadas(1);

        Log.d("PC_OnClick","EDO: "+pm.estadoPartida.toString()+" t1: "+t1.getEstado().toString()+" f1: "+f1+" c1: "+c1+" t2: "+t2.getEstado().toString()+" f2:"+f2+" c2: "+c2+" cont: "+pm.contTjtasMostradas);
    }

    private void iniciarPartida()
    {
        pm.estadoPartida = PartidaModel.EstadoPartida.CORRIENDO;
        pv.stopChronometer();
        pm.ocultarTarjetas();
        pv.ocultarTarjetas();
        pm.contTjtasMostradas = 0;
        previousStartTime = startTime;
        startTime = pv.startChronometer();
    }

    private void reiniciarPartida()
    {
        pm.estadoPartida = PartidaModel.EstadoPartida.INICIAL;
        pv.setTextBotonIniciarTerminar("Iniciar");
        pm.ocultarTarjetas();
        pv.ocultarTarjetas();
        pm.resetVidas();
        pv.setVidas(pm.getVidas());
        pv.setDificultad(Configuraciones.getDificultad());
        pv.stopChronometer();
    }

    @Override
    public void onChronometerTick(Chronometer chronometer)
    {
        int segs;

        if(startTime>previousStartTime)
        {
            currentTime = SystemClock.elapsedRealtime();
            segs =(int) ((currentTime - startTime)/1000);

            //Log.d("PartidaControllerOnChronometerTick","startTime: "+startTime+" currentTime: "+currentTime+" segs: "+segs+" estado: "+pm.estadoPartida.toString());

            if(pm.estadoPartida == PartidaModel.EstadoPartida.INSPECCION && segs>=pm.getTimeout())
            {
                iniciarPartida();
            }
        }
    }

    private void iniciarInspeccion()
    {
        pm.estadoPartida = PartidaModel.EstadoPartida.INSPECCION;
        pv.setTextBotonIniciarTerminar("Terminar");
        pm.resetVidas();
        pv.setVidas(pm.getVidas());
        pv.setDificultad(Configuraciones.getDificultad());
        pm.setTimeout(Configuraciones.getDificultad());
        pm.desordenarTarjetas();
        pm.mostrarTarjetas();
        pv.mostrarTarjetas(pm.getTarjetas());
        previousStartTime = startTime;
        startTime = pv.startChronometerAsTimer(pm.getTimeout());
    }
}