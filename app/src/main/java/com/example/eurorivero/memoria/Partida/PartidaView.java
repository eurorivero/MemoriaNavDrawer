package com.example.eurorivero.memoria.Partida;

import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eurorivero.memoria.Configuraciones;
import com.example.eurorivero.memoria.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabiana Nazaret on 25/11/2017.
 */

public class PartidaView implements Runnable
{
    private static final PartidaView ourInstance = new PartidaView();
    private TextView tvVidas, tvPartidaDificultad;
    private Chronometer cCronometro;
    private Button bIniciarTerminar;

    private RecyclerView rvTarjetas;
    private TarjetaRVAdapter tarjetaRVAdapter;

    private long tiempo;
    private String tiempoString;
    private int idBlah;

    static PartidaView getInstance()
    {
        return ourInstance;
    }

    private PartidaView()
    {

    }

    void setView(View v, Fragment a, List<Tarjeta> alt, RVOnItemClick listener)
    {
        tvVidas = (TextView)v.findViewById(R.id.tv_partida_vidas);
        cCronometro = (Chronometer)v.findViewById(R.id.cCronometro);
        tvPartidaDificultad = (TextView)v.findViewById(R.id.tv_partida_dificultad);
        bIniciarTerminar = (Button)v.findViewById(R.id.bIniciarTerminar);

        rvTarjetas = (RecyclerView)v.findViewById(R.id.rv_tarjetas);
        LinearLayoutManager layoutManager = new GridLayoutManager(a.getContext(),4);
        rvTarjetas.setLayoutManager(layoutManager);

        tarjetaRVAdapter = new TarjetaRVAdapter(alt, listener);
        rvTarjetas.setAdapter(tarjetaRVAdapter);
    }

    Chronometer getCronometro()
    {
        return this.cCronometro;
    }

    long getBase()
    {
        return(cCronometro.getBase());
    }

    void setBotonIniciarTerminarListener(View.OnClickListener l)
    {
        bIniciarTerminar.setOnClickListener(l);
    }

    void setTextBotonIniciarTerminar(int resId)
    {
        bIniciarTerminar.setText(resId);
    }

    void setVidas(int v)
    {
        tvVidas.setText(""+v);
    }

    void setDificultad(Configuraciones.Dificultad d)
    {
        switch(d)
        {
            case NIVEL1:
                tvPartidaDificultad.setText(R.string.Nivel1);
                break;
            case NIVEL2:
                tvPartidaDificultad.setText(R.string.Nivel2);
                break;
            case NIVEL3:
                tvPartidaDificultad.setText(R.string.Nivel3);
                break;
            default:
                break;
        }
    }

    void updateTarjetaRV(){tarjetaRVAdapter.notifyDataSetChanged();}

    void setTiempo(long tiempo){
        this.tiempo = tiempo;
    }

    void setIdBlah(int idBlah){this.idBlah = idBlah;}

    @Override
    public void run() {
        switch(idBlah)
        {
            case 0:
                long minutes=(tiempo/1000)/60;
                long seconds=((tiempo)/1000)%60;
                tiempoString=minutes+":"+seconds;
                cCronometro.setText(tiempoString);
                break;
            case 1:
                updateTarjetaRV();
                break;
            default:
                break;
        }
        Log.d("runUI","idBlah"+idBlah);
    }
}