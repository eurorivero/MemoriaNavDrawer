package com.example.eurorivero.memoria.Ranking;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eurorivero.memoria.R;

import java.util.List;

/**
 * Created by euror on 13/02/2018.
 */

public class RankingsFragment extends Fragment implements View.OnClickListener{

    private static final RankingsFragment ourInstance = new RankingsFragment();

    static SQLiteDatabase db;

    public static RankingsFragment getInstance(SQLiteDatabase database) {
        db = database;
        return ourInstance;
    }

    private RankingsFragment() { }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstateState) {
        View v = inflater.inflate(R.layout.layout_rankings, container, false);

        RankingDAO rankingDAO = new RankingDAO(db);

        List<Ranking> rankings;
/*
        Ranking r1 = new Ranking();
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        Log.d("RF","cdts: "+currentDateTimeString);
        r1.setFechaHora(currentDateTimeString);
        r1.setDificultad(Configuraciones.Dificultad.NIVEL1);
        r1.setDuracion(30);
        r1.setVidas(2);
        r1.setPosicion(1);
        r1.setId(rankingDAO.save(r1));

/*        rankings.add(r1);

        Ranking r2 = new Ranking();
        r2.setFechaHora(SystemClock.elapsedRealtime());
        r2.setDificultad(Configuraciones.Dificultad.NIVEL2);
        r2.setDuracion(45);
        r2.setVidas(1);
        r2.setPosicion(2);
        rankings.add(r2);

        Ranking r3 = new Ranking();
        r3.setFechaHora(SystemClock.elapsedRealtime());
        r3.setDificultad(Configuraciones.Dificultad.NIVEL3);
        r3.setDuracion(60);
        r3.setVidas(0);
        r3.setPosicion(3);
        rankings.add(r3);
*/
        rankings=rankingDAO.getAll();

        RecyclerView rvRankings = (RecyclerView)v.findViewById(R.id.rvRankings);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        rvRankings.setLayoutManager(layoutManager);

        RankingRVAdapter adapter = new RankingRVAdapter(rankings);
        rvRankings.setAdapter(adapter);

        /*
        Ranking r = new Ranking();
        r.setDificultad(Configuraciones.Dificultad.NIVEL3.toString());
        Log.d("RankingsFragment","Dificultad: "+r.getDificultad());



        Ranking ranking = new Ranking();
        ranking.setFechaHora(SystemClock.elapsedRealtime());
        ranking.setDificultad(Configuraciones.Dificultad.NIVEL1);
        ranking.setDuracion(30);
        ranking.setVidas(2);
        ranking.setPosicion(1);

        ranking.setId(rankingDAO.save(ranking));

        Log.d("RF","ID: "+ranking.getId());

        Ranking ranking2 = rankingDAO.get(ranking.getId());
        */

        return v;
    }

    @Override
    public void onClick(View view) {

    }
}
