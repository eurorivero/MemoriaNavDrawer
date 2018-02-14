package com.example.eurorivero.memoria;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

/**
 * Created by Fabiana Nazaret on 19/11/2017.
 */

public class DificultadFragment extends Fragment implements View.OnClickListener
{
    Configuraciones conf;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstateState)
    {
        View v = inflater.inflate(R.layout.layout_dificultad, container, false);

        RadioButton rbNivel1 = (RadioButton)v.findViewById(R.id.rbNivel1);
        RadioButton rbNivel2 = (RadioButton)v.findViewById(R.id.rbNivel2);
        RadioButton rbNivel3 = (RadioButton)v.findViewById(R.id.rbNivel3);

        rbNivel1.setOnClickListener(this);
        rbNivel2.setOnClickListener(this);
        rbNivel3.setOnClickListener(this);

        conf = Configuraciones.getInstance();
        switch(Configuraciones.getDificultad())
        {
            case NIVEL1:
                rbNivel1.setChecked(true);
                break;
            case NIVEL2:
                rbNivel2.setChecked(true);
                break;
            case NIVEL3:
                rbNivel3.setChecked(true);
                break;
        }

        return v;
    }

    @Override
    public void onClick(View v)
    {

        switch(v.getId())
        {
            case R.id.rbNivel1:
                conf = Configuraciones.getInstance();
                conf.setDificultad(Configuraciones.Dificultad.NIVEL1);
                break;
            case R.id.rbNivel2:
                conf = Configuraciones.getInstance();
                conf.setDificultad(Configuraciones.Dificultad.NIVEL2);
                break;
            case R.id.rbNivel3:
                conf = Configuraciones.getInstance();
                conf.setDificultad(Configuraciones.Dificultad.NIVEL3);
                break;
            default:
                break;
        }
        Log.d("rb","Nivel: "+Configuraciones.getDificultad().toString());

    }
}
