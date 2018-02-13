package com.example.eurorivero.memoria.Partida;

import android.os.Build;
import android.os.SystemClock;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eurorivero.memoria.Configuraciones;
import com.example.eurorivero.memoria.R;

/**
 * Created by Fabiana Nazaret on 25/11/2017.
 */

public class PartidaView
{
    private static final PartidaView ourInstance = new PartidaView();
    private View v;
    private static final int TIPOS_DE_TARJETA = 6;
    private TextView tvVidas, tvPartidaTiempo, tvPartidaDificultad;
    private Chronometer cCronometro;
    private boolean timerOrChronometer;
    private ImageView iv_0_0, iv_0_1, iv_0_2;
    private ImageView iv_1_0, iv_1_1, iv_1_2;
    private ImageView iv_2_0, iv_2_1, iv_2_2;
    private ImageView iv_3_0, iv_3_1, iv_3_2;
    private Button bIniciarTerminar;

    private int[] Res = new int[TIPOS_DE_TARJETA];
    private int ResDorso;

    static PartidaView getInstance()
    {
        return ourInstance;
    }

    private PartidaView()
    {

    }

    void setView(View v)
    {
        tvVidas = (TextView)v.findViewById(R.id.tv_partida_vidas);
        cCronometro = (Chronometer)v.findViewById(R.id.cCronometro);
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
        //Log.d("PartidaView","PartidaView builder EXECUTED.");

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

    void setChronometerListener(Chronometer.OnChronometerTickListener l)
    {
        cCronometro.setOnChronometerTickListener(l);
    }

    long startChronometer(int offset)
    {
        timerOrChronometer = false;
        cCronometro.setBase(SystemClock.elapsedRealtime()-(offset*1000));
        cCronometro.start();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            cCronometro.setCountDown(false);
        }
        return(cCronometro.getBase());
    }

    void stopChronometer()
    {
        cCronometro.stop();
    }

    long startChronometerAsTimer(int timeout)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            cCronometro.setBase(SystemClock.elapsedRealtime()+timeout*1000);
            cCronometro.setCountDown(true);
            timerOrChronometer = true;
            cCronometro.start();
            return(cCronometro.getBase()-timeout*1000);
        }
        else
        {
            return(startChronometer(0));
        }
    }

    boolean isTimer()
    {
        //true = timer
        //false = chronometer
        return(timerOrChronometer);
    }

    long getBase()
    {
        return(cCronometro.getBase());
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

    void mostrarTarjeta(int r, int f, int c)
    {
        switch(f)
        {
            case 0:
                switch(c)
                {
                    case 0:
                        mostrarTarjeta(r,iv_0_0);
                        break;
                    case 1:
                        mostrarTarjeta(r,iv_0_1);
                        break;
                    case 2:
                        mostrarTarjeta(r,iv_0_2);
                        break;
                    default:
                        break;
                }
                break;
            case 1:
                switch(c)
                {
                    case 0:
                        mostrarTarjeta(r,iv_1_0);
                        break;
                    case 1:
                        mostrarTarjeta(r,iv_1_1);
                        break;
                    case 2:
                        mostrarTarjeta(r,iv_1_2);
                        break;
                    default:
                        break;
                }
                break;
            case 2:
                switch(c)
                {
                    case 0:
                        mostrarTarjeta(r,iv_2_0);
                        break;
                    case 1:
                        mostrarTarjeta(r,iv_2_1);
                        break;
                    case 2:
                        mostrarTarjeta(r,iv_2_2);
                        break;
                    default:
                        break;
                }
                break;
            case 3:
                switch(c)
                {
                    case 0:
                        mostrarTarjeta(r,iv_3_0);
                        break;
                    case 1:
                        mostrarTarjeta(r,iv_3_1);
                        break;
                    case 2:
                        mostrarTarjeta(r,iv_3_2);
                        break;
                    default:
                        break;
                }
                break;
            default:
        }
    }

    void ocultarTarjeta(View v)
    {
        ImageView iv = (ImageView) v;
        iv.setImageResource(ResDorso);
    }

    void ocultarTarjeta(int f, int c)
    {
        switch(f)
        {
            case 0:
                switch(c)
                {
                    case 0:
                        ocultarTarjeta(iv_0_0);
                        break;
                    case 1:
                        ocultarTarjeta(iv_0_1);
                        break;
                    case 2:
                        ocultarTarjeta(iv_0_2);
                        break;
                    default:
                        break;
                }
                break;
            case 1:
                switch(c)
                {
                    case 0:
                        ocultarTarjeta(iv_1_0);
                        break;
                    case 1:
                        ocultarTarjeta(iv_1_1);
                        break;
                    case 2:
                        ocultarTarjeta(iv_1_2);
                        break;
                    default:
                        break;
                }
                break;
            case 2:
                switch(c)
                {
                    case 0:
                        ocultarTarjeta(iv_2_0);
                        break;
                    case 1:
                        ocultarTarjeta(iv_2_1);
                        break;
                    case 2:
                        ocultarTarjeta(iv_2_2);
                        break;
                    default:
                        break;
                }
                break;
            case 3:
                switch(c)
                {
                    case 0:
                        ocultarTarjeta(iv_3_0);
                        break;
                    case 1:
                        ocultarTarjeta(iv_3_1);
                        break;
                    case 2:
                        ocultarTarjeta(iv_3_2);
                        break;
                    default:
                        break;
                }
                break;
            default:
        }
    }

    void setTextBotonIniciarTerminar(int resId)
    {
        bIniciarTerminar.setText(resId);
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

    void mostrarTarjetas(Tarjeta tarjetas[][])
    {
        iv_0_0.setImageResource(Res[tarjetas[0][0].getIdImagen()]);
        iv_0_1.setImageResource(Res[tarjetas[0][1].getIdImagen()]);
        iv_0_2.setImageResource(Res[tarjetas[0][2].getIdImagen()]);
        iv_1_0.setImageResource(Res[tarjetas[1][0].getIdImagen()]);
        iv_1_1.setImageResource(Res[tarjetas[1][1].getIdImagen()]);
        iv_1_2.setImageResource(Res[tarjetas[1][2].getIdImagen()]);
        iv_2_0.setImageResource(Res[tarjetas[2][0].getIdImagen()]);
        iv_2_1.setImageResource(Res[tarjetas[2][1].getIdImagen()]);
        iv_2_2.setImageResource(Res[tarjetas[2][2].getIdImagen()]);
        iv_3_0.setImageResource(Res[tarjetas[3][0].getIdImagen()]);
        iv_3_1.setImageResource(Res[tarjetas[3][1].getIdImagen()]);
        iv_3_2.setImageResource(Res[tarjetas[3][2].getIdImagen()]);
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
}
