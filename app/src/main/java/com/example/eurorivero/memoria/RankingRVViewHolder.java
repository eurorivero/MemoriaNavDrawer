package com.example.eurorivero.memoria;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by euror on 14/02/2018.
 */

public class RankingRVViewHolder extends RecyclerView.ViewHolder {

    TextView tvFechaHora;
    TextView tvDificultad;
    TextView tvDuracion;
    TextView tvVidas;
    TextView tvPosicion;

    public RankingRVViewHolder(View itemView) {
        super(itemView);
        tvPosicion = (TextView)itemView.findViewById(R.id.tv_ranking_posicion);
        tvDificultad = (TextView)itemView.findViewById(R.id.tv_ranking_dificultad);
        tvDuracion = (TextView)itemView.findViewById(R.id.tv_ranking_duracion);
        tvVidas = (TextView)itemView.findViewById(R.id.tv_ranking_vidas);
        tvFechaHora = (TextView)itemView.findViewById(R.id.tv_ranking_fechahora);
    }
}
