package com.example.eurorivero.memoria.Ranking;

import com.example.eurorivero.memoria.Configuraciones;

import java.util.Comparator;

/**
 * Created by euror on 13/02/2018.
 */

public class Ranking {

    private long id;
    private String fechaHora;
    private Configuraciones.Dificultad dificultad;
    private long duracion;
    private int vidas;
    private int posicion;
    private double indicadorPosicion;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Configuraciones.Dificultad getDificultad() {
        return dificultad;
    }

    public void setDificultad(Configuraciones.Dificultad dificultad) {
        this.dificultad = dificultad;
    }

    public void setDificultad(String dificultad) {
        if (dificultad.equals(Configuraciones.Dificultad.NIVEL1.toString()))
            this.dificultad = Configuraciones.Dificultad.NIVEL1;
        else if (dificultad.equals(Configuraciones.Dificultad.NIVEL2.toString()))
            this.dificultad = Configuraciones.Dificultad.NIVEL2;
        else if (dificultad.equals(Configuraciones.Dificultad.NIVEL3.toString()))
            this.dificultad = Configuraciones.Dificultad.NIVEL3;
    }

    public long getDuracion() {
        return duracion;
    }

    public void setDuracion(long duracion) {
        this.duracion = duracion;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public double getIndicadorPosicion() {
        return indicadorPosicion;
    }

    public void setIndicadorPosicion(double indicadorPosicion) {
        this.indicadorPosicion = indicadorPosicion;
    }

    public static double calcularIndicadorPosicion(Ranking r) {
        double dif, v, t, i;

        switch (r.getDificultad()) {
            case NIVEL1:
                dif = 1;
                break;
            case NIVEL2:
                dif = 2;
                break;
            case NIVEL3:
                dif = 3;
                break;
            default:
                dif = 1;
        }

        v = (double) r.getVidas();
        t = (double) r.getDuracion();

        i  = Math.pow(dif, 4);
        i += Math.pow(v, 2);
        i += t;

        return i;
    }

    public static Comparator<Ranking> RankingIndPosComparator = new Comparator<Ranking>() {
        @Override
        public int compare(Ranking o1, Ranking o2) {
            double indPos1 = o1.getIndicadorPosicion();
            double indPos2 = o2.getIndicadorPosicion();
            int r = 0;

            if(indPos1==indPos2)
                r = 0;
            else if(indPos1<indPos2)
                r = +1;
            else if(indPos1>indPos2)
                r = -1;

            return r;
        }
    };

    public String toString(){
        return "[ ID = "+this.id+"; Fecha/Hora = "+fechaHora+"; Posicion = "+this.posicion+"; IndPos = "+this.indicadorPosicion+"; Dificultad = "+this.dificultad+"; Vidas = "+this.vidas+"; Tiempo = "+this.duracion;
    }
}