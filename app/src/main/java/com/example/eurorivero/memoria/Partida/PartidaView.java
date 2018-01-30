package com.example.eurorivero.memoria.Partida;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eurorivero.memoria.Configuraciones;
import com.example.eurorivero.memoria.R;

/**
 * Created by Fabiana Nazaret on 25/11/2017.
 */

public class PartidaView
{
    private static final int TIPOS_DE_TARJETA = 6;
    private TextView tvVidas, tvPartidaTiempo, tvPartidaDificultad;
    private ImageView iv_0_0, iv_0_1, iv_0_2;
    private ImageView iv_1_0, iv_1_1, iv_1_2;
    private ImageView iv_2_0, iv_2_1, iv_2_2;
    private ImageView iv_3_0, iv_3_1, iv_3_2;
    private Button bIniciarTerminar;

    private int[] Res = new int[TIPOS_DE_TARJETA];
    private int ResDorso;

    PartidaView(View v)
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
        bIniciarTerminar = (Button)v.findViewById(R.id.bIniciarTerminar);

        ResDorso = v.getResources().getIdentifier("question_icon", "drawable","com.example.eurorivero.memoria");
        Res[0] = v.getResources().getIdentifier("img_1", "drawable","com.example.eurorivero.memoria");
        Res[1] = v.getResources().getIdentifier("img_2", "drawable","com.example.eurorivero.memoria");
        Res[2] = v.getResources().getIdentifier("img_3", "drawable","com.example.eurorivero.memoria");
        Res[3] = v.getResources().getIdentifier("img_4", "drawable","com.example.eurorivero.memoria");
        Res[4] = v.getResources().getIdentifier("img_5", "drawable","com.example.eurorivero.memoria");
        Res[5] = v.getResources().getIdentifier("img_6", "drawable","com.example.eurorivero.memoria");
        //Log.d("PartidaView","PartidaView builder executed.");

    }

    void setTarjetasListeners(View.OnClickListener l)
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

    void setBotonIniciarTerminarListener(View.OnClickListener l)
    {
        bIniciarTerminar.setOnClickListener(l);
    }

    void mostrarTarjeta(int r, View v)
    {
        ImageView iv = (ImageView) v;
        iv.setImageResource(Res[r]);
    }

    void ocultarTarjeta(View v)
    {
        ImageView iv = (ImageView) v;
        iv.setImageResource(ResDorso);
    }

    void setTextBotonIniciarTerminar(CharSequence text)
    {
        bIniciarTerminar.setText(text);
    }

    void ocultarTarjetas()
    {
        iv_0_0.setImageResource(ResDorso);
        iv_0_1.setImageResource(ResDorso);
        iv_0_2.setImageResource(ResDorso);
        iv_1_0.setImageResource(ResDorso);
        iv_1_1.setImageResource(ResDorso);
        iv_1_2.setImageResource(ResDorso);
        iv_2_0.setImageResource(ResDorso);
        iv_2_1.setImageResource(ResDorso);
        iv_2_2.setImageResource(ResDorso);
        iv_3_0.setImageResource(ResDorso);
        iv_3_1.setImageResource(ResDorso);
        iv_3_2.setImageResource(ResDorso);
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
                tvPartidaDificultad.setText("Nivel 1");
                break;
            case NIVEL2:
                tvPartidaDificultad.setText("Nivel 2");
                break;
            case NIVEL3:
                tvPartidaDificultad.setText("Nivel 3");
                break;
            default:
                break;
        }
    }
}
