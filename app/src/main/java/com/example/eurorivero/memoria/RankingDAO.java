package com.example.eurorivero.memoria;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by euror on 13/02/2018.
 */

public class RankingDAO implements DAOinterface<Ranking> {

    private SQLiteDatabase db;

    public RankingDAO(SQLiteDatabase db){
        this.db = db;
    }

    @Override
    public long save(Ranking type) {
        db.execSQL("INSERT INTO Rankings "+"(FechaHora,Dificultad,Vidas,Duracion,Posicion) "+
                    "VALUES("+type.getFechaHora()+",'"+type.getDificultad().toString()+"',"+type.getVidas()+
                    ","+type.getDuracion()+","+type.getPosicion()+")");
        return 0;
    }

    @Override
    public void update(Ranking type) {
        ContentValues values = new ContentValues();

        values.put("FechaHora", type.getFechaHora());
        values.put("Dificultad", type.getDificultad().toString());
        values.put("Vidas", type.getVidas());
        values.put("Duracion", type.getDuracion());
        values.put("Posicion", type.getPosicion());

        db.update("Rankings", values, "_id="+type.getId(), null);
    }

    @Override
    public void delete(Ranking type) {
        db.delete("Rankings", "_id="+type.getId(), null);
    }

    @Override
    public Ranking get(long id) {
        Ranking r = null;
        Cursor c;

        c = db.query("Rankings",
                    new String[]{"_id","FechaHora","Dificultad","Vidas","Duracion","Posicion"},
                    "_id="+id, null, null, null, null, null);
        if(c.moveToFirst())
        {
            r = new Ranking();
            r.setId(c.getLong(0));
            r.setFechaHora(c.getLong(1));
            r.setDificultad(c.getString(2));
            r.setVidas(c.getInt(3));
            r.setDuracion(c.getLong(4));
            r.setPosicion(c.getInt(5));
        }
        if(!c.isClosed())
        {
            c.close();
        }

        return r;
    }

    @Override
    public List<Ranking> getAll() {
        ArrayList<Ranking> alRankings = null;
        long id = 0;
        Cursor c;
        Ranking r=null;
        c = db.query("Rankings",
                new String[]{"_id","FechaHora","Dificultad","Vidas","Duracion","Posicion"},
                "_id="+id, null, null, null, null, null);

        do {
            r = new Ranking();
            r.setId(c.getLong(0));
            r.setFechaHora(c.getLong(1));
            r.setDificultad(c.getString(2));
            r.setVidas(c.getInt(3));
            r.setDuracion(c.getLong(4));
            r.setPosicion(c.getInt(5));
            alRankings.add(r);
        }while(c.moveToNext());

        c.close();

        return alRankings;
    }
}