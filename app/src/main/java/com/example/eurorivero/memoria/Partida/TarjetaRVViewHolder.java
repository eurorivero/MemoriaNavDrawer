
package com.example.eurorivero.memoria.Partida;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.eurorivero.memoria.R;

/**
 * Created by euror on 19/02/2018.
 */

public class TarjetaRVViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    View view;
    ImageView ivTarjeta;
    RVOnItemClick listener;
    int position;

    public TarjetaRVViewHolder(View itemView, RVOnItemClick listener)
    {
        super(itemView);
        this.view = itemView;
        ivTarjeta = (ImageView)itemView.findViewById(R.id.iv_tarjeta);
        ivTarjeta.setOnClickListener(this);
        this.listener = listener;
    }

    void setPosition(int position){this.position = position;}

    @Override
    public void onClick(View v) {
        Log.d("TarjetaRVViewHolde", "onClick: "+position);
        listener.onItemClick(position);
    }
}
