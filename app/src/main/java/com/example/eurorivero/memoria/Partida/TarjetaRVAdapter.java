package com.example.eurorivero.memoria.Partida;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eurorivero.memoria.R;

import java.util.List;

/**
 * Created by euror on 19/02/2018.
 */

public class TarjetaRVAdapter extends RecyclerView.Adapter<TarjetaRVViewHolder>
{
    private List<Tarjeta> lista;
    private RVOnItemClick listener;

    public TarjetaRVAdapter(List<Tarjeta> lista, RVOnItemClick listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @Override
    public TarjetaRVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tarjeta,parent,false);
        TarjetaRVViewHolder tarjetaRVViewHolder = new TarjetaRVViewHolder(v, listener);
        return tarjetaRVViewHolder;
    }

    @Override
    public void onBindViewHolder(TarjetaRVViewHolder holder, int position) {
        Tarjeta t = lista.get(position);
        holder.ivTarjeta.setImageResource(t.getCurrentImageResource());
        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
