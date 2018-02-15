package com.example.eurorivero.memoria;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by euror on 13/02/2018.
 */

public class RankingDAO implements DAOinterface<Ranking> {

    private SQLiteDatabase db;
    //private SQLiteStatement statementSave;

    public RankingDAO(SQLiteDatabase db){
        this.db = db;
        //statementSave = db.compileStatement("INSERT INTO personas (nombre,edad) VALUES(?,?)");
    }

    @Override
    public long save(Ranking type) {

        ContentValues values = new ContentValues();

        values.put("FechaHora", type.getFechaHora());
        values.put("Dificultad", type.getDificultad().toString());
        values.put("Duracion", type.getDuracion());
        values.put("Vidas", type.getVidas());
        values.put("Posicion", type.getPosicion());

        return(db.insert("Rankings",null,values));
    }

    @Override
    public void update(Ranking type) {
        ContentValues values = new ContentValues();

        values.put("FechaHora", type.getFechaHora());
        values.put("Dificultad", type.getDificultad().toString());
        values.put("Duracion", type.getDuracion());
        values.put("Vidas", type.getVidas());
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
                    new String[]{"_id","FechaHora","Dificultad","Duracion","Vidas","Posicion"},
                    "_id="+id, null, null, null, null, null);
        if(c.moveToFirst())
        {
            r = new Ranking();
            r.setId(c.getLong(0));
            r.setFechaHora(c.getLong(1));
            r.setDificultad(c.getString(2));
            r.setDuracion(c.getLong(3));
            r.setVidas(c.getInt(4));
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
        ArrayList<Ranking> alRankings = new ArrayList<Ranking>();
        long id = 0;
        Cursor c;
        Ranking r=null;
        c = db.query("Rankings",
                new String[]{"_id","FechaHora","Dificultad","Duracion","Vidas","Posicion"},
                null, null, null, null, null, null);
        Log.d("RankingDAO.getAll:","Rows: "+c.getCount());
        if(c.moveToFirst() && c!=null) {
            do {
                r = new Ranking();
                Log.d("RankingDAO.getAll:", "r.id: " + c.getLong(0));
                r.setId(c.getLong(0));
                r.setFechaHora(c.getLong(1));
                r.setDificultad(c.getString(2));
                r.setDuracion(c.getLong(3));
                r.setVidas(c.getInt(4));
                r.setPosicion(c.getInt(5));
                alRankings.add(r);
            } while (c.moveToNext());
        }
        c.close();

        return alRankings;
    }
}