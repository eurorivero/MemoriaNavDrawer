package com.example.eurorivero.memoria.Partida;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eurorivero.memoria.Configuraciones;
import com.example.eurorivero.memoria.R;

/**
 * Created by Fabiana Nazaret on 20/11/2017.
 */

public class PartidaFragment extends Fragment
{
    Configuraciones conf;
    PartidaModel pm;
    PartidaView pv;
    PartidaController pc;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstateState)
    {
        View v = inflater.inflate(R.layout.layout_partida, container, false);

        conf = Configuraciones.getInstance();
        pm = PartidaModel.getInstance();
        pv = new PartidaView(v);
        pc = new PartidaController(pm,pv);

        return v;
    }
}
