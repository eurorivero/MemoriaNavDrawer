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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

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

    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    public static PartidaFragment getInstance()
    {
        return ourInstance;
    }

    private PartidaFragment()
    {

    }

    public static void setDB(SQLiteDatabase database){
        db = database;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstateState)
    {
        View v = inflater.inflate(R.layout.layout_partida, container, false);

        scheduledThreadPoolExecutor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(2);

        conf = Configuraciones.getInstance();
        pc = PartidaController.getInstance();
        pc.setFA(this.getActivity());
        pc.setDB(db);
        pc.setSTPE(scheduledThreadPoolExecutor);
        pm = PartidaModel.getInstance();
        pm.setPfView(v);
        pv = PartidaView.getInstance();
        Log.d("PartidaFragment","pm.EstadoPartida: "+pm.estadoPartida);

        boolean primeraVez = false;

        if(pc.getPm() == null)
        {
            pc.setPm(pm);
            primeraVez = true;
        }

        pv.setView(v, this, pm.getAlTarjetas(), pc);
        pv.setBotonIniciarTerminarListener(pc);

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
        scheduledThreadPoolExecutor.shutdown();
        Log.d("PFonPause ","onPause");
    }

}