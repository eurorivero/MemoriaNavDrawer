package com.example.eurorivero.memoria;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Fabiana Nazaret on 20/11/2017.
 */

public class PartidaFragment extends Fragment
{
    Configuraciones conf;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstateState)
    {
        View v = inflater.inflate(R.layout.layout_partida, container, false);

        conf = Configuraciones.getInstance();
        switch(Configuraciones.getDificultad())
        {
            case NIVEL1:

                break;
            case NIVEL2:

                break;
            case NIVEL3:

                break;
        }

        return v;
    }
}
