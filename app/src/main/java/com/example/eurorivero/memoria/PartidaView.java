package com.example.eurorivero.memoria;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Fabiana Nazaret on 25/11/2017.
 */

public class PartidaView
{
    TextView tvVidas, tvPartidaTiempo, tvPartidaDificultad;
    ImageView iv_0_0, iv_0_1, iv_0_2;
    ImageView iv_1_0, iv_1_1, iv_1_2;
    ImageView iv_2_0, iv_2_1, iv_2_2;
    ImageView iv_3_0, iv_3_1, iv_3_2;

    int res;

    public PartidaView(View v)
    {
        tvVidas = (TextView)v.findViewById(R.id.tv_partida_vidas);
        tvPartidaTiempo = (TextView)v.findViewById(R.id.tv_partida_tiempo);
        tvPartidaDificultad = (TextView)v.findViewById(R.id.tv_partida_dificultad);
        iv_0_0 = (ImageView)v.findViewById(R.id.iv0_0);
        iv_0_1 = (ImageView)v.findViewById(R.id.iv0_1);
        iv_0_2 = (ImageView)v.findViewById(R.id.iv0_2);
        iv_1_0 = (ImageView)v.findViewById(R.id.iv1_0);
        iv_1_1 = (ImageView)v.findViewById(R.id.iv1_1);
        iv_1_2 = (ImageView)v.findViewById(R.id.iv1_2);
        iv_2_0 = (ImageView)v.findViewById(R.id.iv2_0);
        iv_2_1 = (ImageView)v.findViewById(R.id.iv2_1);
        iv_2_2 = (ImageView)v.findViewById(R.id.iv2_2);
        iv_3_0 = (ImageView)v.findViewById(R.id.iv3_0);
        iv_3_1 = (ImageView)v.findViewById(R.id.iv3_1);
        iv_3_2 = (ImageView)v.findViewById(R.id.iv3_2);

        res = v.getResources().getIdentifier("img_5", "drawable","com.example.eurorivero.memoria");
    }

    public void setTarjetasListeners(View.OnClickListener l)
    {
        iv_0_0.setOnClickListener(l);
        iv_0_1.setOnClickListener(l);
        iv_0_2.setOnClickListener(l);
        iv_1_0.setOnClickListener(l);
        iv_1_1.setOnClickListener(l);
        iv_1_2.setOnClickListener(l);
        iv_2_0.setOnClickListener(l);
        iv_2_1.setOnClickListener(l);
        iv_2_2.setOnClickListener(l);
        iv_3_0.setOnClickListener(l);
        iv_3_1.setOnClickListener(l);
        iv_3_2.setOnClickListener(l);
    }

    public void voltearTarjeta(View v)
    {
        switch(v.getId()) {
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
                ImageView iv = (ImageView) v;
                iv.setImageResource(res);
                break;
            default:
                break;
        }
    }

}
