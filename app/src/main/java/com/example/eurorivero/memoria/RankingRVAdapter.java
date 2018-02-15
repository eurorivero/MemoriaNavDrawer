package com.example.eurorivero.memoria;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by euror on 14/02/2018.
 */

public class RankingRVAdapter extends RecyclerView.Adapter<RankingRVViewHolder>{

    private List<Ranking> lista;

    public RankingRVAdapter(List<Ranking> lista)
    {
        this.lista = lista;
    }

    @Override
    public RankingRVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ranking, parent, false);
        RankingRVViewHolder rankingRVViewHolder = new RankingRVViewHolder(v);
        return rankingRVViewHolder;
    }

    @Override
    public void onBindViewHolder(RankingRVViewHolder holder, int position) {
        Ranking r = lista.get(position);
        holder.tvPosicion.setText(Integer.valueOf(r.getPosicion()).toString());
        holder.tvDificultad.setText(r.getDificultad().toString());
        holder.tvDuracion.setText(Long.valueOf(r.getDuracion()).toString());
        holder.tvVidas.setText(Integer.valueOf(r.getVidas()).toString());
        holder.tvFechaHora.setText(Long.valueOf(r.getFechaHora()).toString());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
