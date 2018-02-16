package com.example.eurorivero.memoria.Partida;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eurorivero.memoria.R;
import com.example.eurorivero.memoria.Ranking;

/**
 * Created by euror on 15/02/2018.
 */

public class ResumenFragment extends Fragment{

    public ResumenFragment(){}
    private static Ranking rp;

    public static ResumenFragment newInstance(Ranking rankingPartida)
    {
        ResumenFragment rf = new ResumenFragment();
        rp = rankingPartida;
        Bundle args = new Bundle();
        args.putInt("Posicion",rankingPartida.getPosicion());
        args.putString("Dificultad", rankingPartida.getDificultad().toString());
        args.putLong("Duracion",rankingPartida.getDuracion());
        args.putInt("Vidas",rankingPartida.getVidas());
        args.putString("FechaHora",rankingPartida.getFechaHora());
        rf.setArguments(args);
        return rf;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstateState) {
        View v = inflater.inflate(R.layout.layout_resumen, container, false);

        TextView tvRankingPosicion = (TextView)v.findViewById(R.id.tv_ranking_posicion);
        TextView tvRankingDificultad = (TextView)v.findViewById(R.id.tv_ranking_dificultad);
        TextView tvRankingDuracion = (TextView)v.findViewById(R.id.tv_ranking_duracion);
        TextView tvRankingVidas = (TextView)v.findViewById(R.id.tv_ranking_vidas);
        TextView tvRankingFechaHora = (TextView)v.findViewById(R.id.tv_ranking_fechahora);

        Bundle args = getArguments();
/*
        tvRankingPosicion.setText(String.valueOf(args.getInt("Posicion")));
        tvRankingDificultad.setText(args.getString("Dificultad"));
        tvRankingDuracion.setText(Long.toString(args.getLong("Duracion")));
        tvRankingVidas.setText(String.valueOf(args.getInt("Vidas)")));
        tvRankingFechaHora.setText(args.getString("FechaHora"));

*/

//        tvRankingPosicion.setText(String.valueOf(rp.getPosicion()));
        tvRankingDificultad.setText(rp.getDificultad().toString());
        tvRankingDuracion.setText(Long.toString(rp.getDuracion()));
//        tvRankingVidas.setText(String.valueOf(rp.getVidas()));
        tvRankingFechaHora.setText(rp.getFechaHora());

    return v;
    }
}
