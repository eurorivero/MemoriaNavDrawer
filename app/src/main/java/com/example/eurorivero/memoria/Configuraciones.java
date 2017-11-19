package com.example.eurorivero.memoria;

/**
 * Created by Fabiana Nazaret on 19/11/2017.
 */

class Configuraciones {
    private static final Configuraciones ourInstance = new Configuraciones();

    public enum Dificultad
    {
        NIVEL1,
        NIVEL2,
        NIVEL3,
    }
    private static Dificultad dificultad = Dificultad.NIVEL1;

    static Configuraciones getInstance() {
        return ourInstance;
    }

    private Configuraciones() {
    }

    public void setDificultad(Dificultad dificultad)
    {
        this.dificultad = dificultad;
    }

    public static Dificultad getDificultad()
    {
        return(dificultad);
    }
}
