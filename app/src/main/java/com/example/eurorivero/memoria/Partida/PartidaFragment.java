package com.example.eurorivero.memoria.Partida;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
    private static final PartidaFragment ourInstance = new PartidaFragment();
    Configuraciones conf;
    PartidaModel pm;
    PartidaView pv;
    PartidaController pc;
    static SQLiteDatabase db;

    public static PartidaFragment getInstance()
    {
        return ourInstance;
    }

    private PartidaFragment()
    {
        Log.d("PartidaFragment","PartidaFragment builder executed");
    }

    public static void setDB(SQLiteDatabase database){
        db = database;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstateState)
    {
        View v = inflater.inflate(R.layout.layout_partida, container, false);

        conf = Configuraciones.getInstance();
        pc = PartidaController.getInstance();
        pc.setFA(this.getActivity());
        pc.setDB(db);
        pm = PartidaModel.getInstance();
        pv = PartidaView.getInstance();
        Log.d("PartidaFragment","pm.EstadoPartida: "+pm.estadoPartida);

        boolean primeraVez = false;

        if(pc.getPm() == null)
        {
            pc.setPm(pm);
            primeraVez = true;
        }

        pv.setView(v);
        pv.setTarjetasListeners(pc);
        pv.setBotonIniciarTerminarListener(pc);
        pv.setChronometerListener(pc);
        if(pc.getPv() == null)
        {
            pc.setPv(pv);
            primeraVez = true;
        }

        if(!primeraVez)
            pc.reanudarPartida();

        return v;
    }

    public void onPause()
    {
        super.onPause();
        pv.stopChronometer();
        Log.d("PFonPause ","onPause");
    }

}
