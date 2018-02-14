package com.example.eurorivero.memoria;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by euror on 13/02/2018.
 */

public class RankingsFragment extends Fragment implements View.OnClickListener{

    private static final RankingsFragment ourInstance = new RankingsFragment();

    TextView tvPosicion;
    TextView tvDificultad;
    TextView tvDuracion;
    TextView tvVidas;
    TextView tvFechaHora;
    static SQLiteDatabase db;

    public static RankingsFragment getInstance(SQLiteDatabase database) {
        db = database;
        return ourInstance;
    }

    private RankingsFragment() { }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstateState) {
        View v = inflater.inflate(R.layout.layout_ranking, container, false);

        tvPosicion = (TextView)v.findViewById(R.id.tv_ranking_posicion);
        tvDificultad = (TextView)v.findViewById(R.id.tv_ranking_dificultad);
        tvDuracion = (TextView)v.findViewById(R.id.tv_ranking_duracion);
        tvVidas = (TextView)v.findViewById(R.id.tv_ranking_vidas);
        tvFechaHora = (TextView)v.findViewById(R.id.tv_ranking_fechahora);

        Ranking r = new Ranking();
        r.setDificultad(Configuraciones.Dificultad.NIVEL3.toString());
        Log.d("RankingsFragment","Dificultad: "+r.getDificultad());

        RankingDAO rankingDAO = new RankingDAO(db);

        Ranking ranking = new Ranking();
        ranking.setFechaHora(SystemClock.elapsedRealtime());
        ranking.setDificultad(Configuraciones.Dificultad.NIVEL1);
        ranking.setDuracion(30);
        ranking.setVidas(2);
        ranking.setPosicion(1);

        ranking.setId(rankingDAO.save(ranking));

        Log.d("RF","ID: "+ranking.getId());

        Ranking ranking2 = rankingDAO.get(ranking.getId());

        tvPosicion.setText(Integer.valueOf(ranking2.getPosicion()).toString());
        tvDificultad.setText(ranking2.getDificultad().toString());
        tvDuracion.setText(Long.valueOf(ranking2.getDuracion()).toString());
        tvVidas.setText(Integer.valueOf(ranking2.getVidas()).toString());
        tvFechaHora.setText(Long.valueOf(ranking2.getFechaHora()).toString());

/*
        tvPosicion.setText(Integer.valueOf(ranking.getPosicion()).toString());
        tvDificultad.setText(ranking.getDificultad().toString());
        tvDuracion.setText(Long.valueOf(ranking.getDuracion()).toString());
        tvVidas.setText(Integer.valueOf(ranking.getVidas()).toString());
        tvFechaHora.setText(Long.valueOf(ranking.getFechaHora()).toString());
*/
        return v;
    }

    @Override
    public void onClick(View view) {

    }
}
