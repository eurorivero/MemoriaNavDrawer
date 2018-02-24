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
        pv.setTarjetasListeners(this);
        pv.setBotonIniciarTerminarListener(this);
        pv.setChronometerListener(this);
        pv.ocultarTarjetas();
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
        pv.mostrarTarjetas(pm.getTarjetas());
        segundos = 0;
        previousStartTime = startTime;
        startTime = pv.startChronometerAsTimer(pm.getTimeout());


    }

    private void iniciarPartida()
    {
        pm.estadoPartida = PartidaModel.EstadoPartida.CORRIENDO;
        pv.stopChronometer();
        pm.ocultarTarjetas();
        pv.ocultarTarjetas();
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
        pv.ocultarTarjetas();
        pm.resetVidas();
        pv.setVidas(pm.getVidas());
        pm.setDificultad(Configuraciones.getDificultad());
        pv.setDificultad(pm.getDificultad());
        pv.stopChronometer();
    }

    void reanudarPartida()
    {
        Tarjeta t;
        for (int i = 0; i < PartidaModel.FILAS; i++)
        {
            for (int j = 0; j < PartidaModel.COLUMNAS; j++)
            {
                t=pm.getTarjeta(i,j);
                if(t.getEstado()== Tarjeta.TarjetaEstado.VISIBLE)
                    pv.mostrarTarjeta(t.getIdImagen(),i,j);
                else if(t.getEstado()== Tarjeta.TarjetaEstado.OCULTA)
                    pv.ocultarTarjeta(i,j);
            }
        }

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
                                pv.setTextBotonIniciarTerminar(R.string.Reiniciar);
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

                                /*
                                Fragment newFragment = new ExampleFragment();
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack
                                transaction.replace(R.id.fragment_container, newFragment);
                                transaction.addToBackStack(null);
// Commit the transaction
                                transaction.commit();
                                */
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

    @Override
    public void onChronometerTick(Chronometer chronometer)
    {
        //int segs;

        if(startTime>previousStartTime)
        {
            segundos++;
            Log.d("OnChronometerTick","segundos: "+segundos+" estado: "+pm.estadoPartida.toString()+" Ch: "+chronometer.hashCode());
            currentTime = SystemClock.elapsedRealtime();
            //segs =(int) ((currentTime - startTime)/1000);

            //Log.d("OnChronometerTick","startTime: "+startTime+" currentTime: "+currentTime+" segs: "+segs+" estado: "+pm.estadoPartida.toString());

            if(pm.estadoPartida == PartidaModel.EstadoPartida.INSPECCION && segundos>=pm.getTimeout())
            {
                iniciarPartida();
                Tarjeta t1 = pm.getTarjeta(pm.getFilaTarjetaSeleccionada(0),pm.getColTarjetaSeleccionadas(0));
                Tarjeta t2 = pm.getTarjeta(pm.getFilaTarjetaSeleccionada(1),pm.getColTarjetaSeleccionadas(1));
                int f1 = pm.getFilaTarjetaSeleccionada(0);
                int c1 = pm.getColTarjetaSeleccionadas(0);
                int f2 = pm.getFilaTarjetaSeleccionada(1);
                int c2 = pm.getColTarjetaSeleccionadas(1);
                //Log.d("PC_OnClick","EDO: "+pm.estadoPartida.toString()+" t1: "+t1.getEstado().toString()+" f1: "+f1+" c1: "+c1+" t2: "+t2.getEstado().toString()+" f2:"+f2+" c2: "+c2+" cont: "+pm.contTjtasMostradas);
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
                        if(!pm.verificarCoincidencia())
                        {
                            pm.setPosTarjetasSeleccionadas(1,position);
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
                pm.ocultarTarjetas();
                t.setEstado(Tarjeta.TarjetaEstado.VISIBLE);
                pm.setPosTarjetasSeleccionadas(0, position);
                pm.contTjtasMostradas=1;
                pm.estadoPartida = PartidaModel.EstadoPartida.CORRIENDO;
                pv.updateTarjetaRV();
            }
        }
    }
}