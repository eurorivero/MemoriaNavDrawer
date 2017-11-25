package com.example.eurorivero.memoria;

import android.view.View;
import android.widget.ImageView;

/**
 * Created by Fabiana Nazaret on 25/11/2017.
 */

public class PartidaController implements View.OnClickListener{

    PartidaModel pm;
    PartidaView pv;

    public PartidaController(PartidaModel pm, PartidaView pv)
    {
        this.pm = pm;
        this.pv = pv;

        switch(Configuraciones.getDificultad())
        {
            case NIVEL1:
                pm.configurarPartida(3,9,120);
                break;
            case NIVEL2:
                pm.configurarPartida(2,6,120);
                break;
            case NIVEL3:
                pm.configurarPartida(1,3,120);
                break;
        }
        pv.setTarjetasListeners(this);
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.iv0_0:
            case R.id.iv0_1:
            case R.id.iv0_2:
            case R.id.iv1_0:
            case R.id.iv1_1:
            case R.id.iv1_2:
            case R.id.iv2_0:
            case R.id.iv2_1:
            case R.id.iv2_2:
            case R.id.iv3_0:
            case R.id.iv3_1:
            case R.id.iv3_2:
                pv.voltearTarjeta(v);
                break;
            default:
                return;
        }
    }
}
