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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstateState) {
        View v = inflater.inflate(R.layout.layout_resumen, container, false);

        TextView tvResumenPosicion = (TextView)v.findViewById(R.id.tv_resumen_posicion);
        TextView tvResumenDificultad = (TextView)v.findViewById(R.id.tv_resumen_dificultad);
        TextView tvResumenDuracion = (TextView)v.findViewById(R.id.tv_resumen_duracion);
        TextView tvResumenVidas = (TextView)v.findViewById(R.id.tv_resumen_vidas);
        TextView tvResumenFechaHora = (TextView)v.findViewById(R.id.tv_resumen_fechahora);

        Bundle args = getArguments();

        tvResumenPosicion.setText(String.valueOf(args.getInt("Posicion")));
        tvResumenDificultad.setText(args.getString("Dificultad"));
        tvResumenDuracion.setText(Long.toString(args.getLong("Duracion")));
        tvResumenVidas.setText(String.valueOf(args.getInt("Vidas")));
        tvResumenFechaHora.setText(args.getString("FechaHora"));

        return v;
    }
}
