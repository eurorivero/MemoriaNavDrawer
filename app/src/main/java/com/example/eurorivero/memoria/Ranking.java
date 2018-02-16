package com.example.eurorivero.memoria;

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

    public long getId(){return id;}
    public void setId(long id){this.id = id;}
    public String getFechaHora(){return fechaHora;}
    public void setFechaHora(String fechaHora){this.fechaHora = fechaHora;}
    public Configuraciones.Dificultad getDificultad(){return dificultad;}
    public void setDificultad(Configuraciones.Dificultad dificultad){this.dificultad = dificultad;}
    public void setDificultad(String dificultad){
        if(dificultad.equals(Configuraciones.Dificultad.NIVEL1.toString()))
            this.dificultad = Configuraciones.Dificultad.NIVEL1;
        else if(dificultad.equals(Configuraciones.Dificultad.NIVEL2.toString()))
            this.dificultad = Configuraciones.Dificultad.NIVEL2;
        else if(dificultad.equals(Configuraciones.Dificultad.NIVEL3.toString()))
            this.dificultad = Configuraciones.Dificultad.NIVEL3;
    }
    public long getDuracion(){return duracion;}
    public void setDuracion(long duracion){this.duracion = duracion;}
    public int getVidas(){return vidas;}
    public void setVidas(int vidas){this.vidas = vidas;}
    public int getPosicion(){return posicion;}
    public void setPosicion(int posicion){this.posicion = posicion;}

}
