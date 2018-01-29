package com.example.eurorivero.memoria.Partida;

import android.view.View;

import com.example.eurorivero.memoria.Configuraciones;
import com.example.eurorivero.memoria.R;

/**
 * Created by Fabiana Nazaret on 25/11/2017.
 */

public class PartidaController implements View.OnClickListener{

    private PartidaModel pm;
    private PartidaView pv;

    PartidaController(PartidaModel pm, PartidaView pv)
    {
        this.pm = pm;
        this.pv = pv;
        pm.estadoPartida = PartidaModel.EstadoPartida.INICIAL;
        switch(Configuraciones.getDificultad())
        {
            case NIVEL1:
                pm.configurarPartida(3,9);
                break;
            case NIVEL2:
                pm.configurarPartida(2,6);
                break;
            case NIVEL3:
                pm.configurarPartida(1,3);
                break;
            default:
                pm.configurarPartida(3,9);
        }
        pv.setTarjetasListeners(this);
        //Log.d("PartidaController","PartidaController builder executed.");

    }

    @Override
    public void onClick(View v)
    {
        int f,c;
        Tarjeta t;

        switch(v.getId())
        {
            case R.id.iv0_0:
                f = 0;
                c = 0;
                break;
            case R.id.iv0_1:
                f = 0;
                c = 1;
                break;
            case R.id.iv0_2:
                f = 0;
                c = 2;
                break;
            case R.id.iv1_0:
                f = 1;
                c = 0;
                break;
            case R.id.iv1_1:
                f = 1;
                c = 1;
                break;
            case R.id.iv1_2:
                f = 1;
                c = 2;
                break;
            case R.id.iv2_0:
                f = 2;
                c = 0;
                break;
            case R.id.iv2_1:
                f = 2;
                c = 1;
                break;
            case R.id.iv2_2:
                f = 2;
                c = 2;
                break;
            case R.id.iv3_0:
                f = 3;
                c = 0;
                break;
            case R.id.iv3_1:
                f = 3;
                c = 1;
                break;
            case R.id.iv3_2:
                f = 3;
                c = 2;
                break;
            default:
                f=0;
                c=0;
                break;
        }
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
