package com.example.eurorivero.memoria;

/**
 * Created by euror on 13/02/2018.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyOpenHelper extends SQLiteOpenHelper
{
    private String  dbFilePath;
    private String  dbFileName;
    private Context context;

    private static final int DB_VERSION = 1;

    public MyOpenHelper(Context context, String name)
    {
        super(context, name, null, DB_VERSION);

        // Obtengo path a la db para esta aplicacion
        dbFilePath = context.getDatabasePath(name).getAbsolutePath();
        dbFilePath = dbFilePath.substring(0, dbFilePath.lastIndexOf("/")+1);

        this.context=context;
        this.dbFileName=name;

        checkDataBase(); // si no esta creada la db, la creo
    }


    private void checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = dbFilePath + dbFileName;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        }catch(SQLiteException e){
            // Error al abrir la db, no existe, entonces la creamos
            try {
                Log.d("helper","Copiando archivo desde assets");
                copyDataBase();
            } catch (IOException e2) {
                Log.d("helper","Error copiando archivo desde assets");
            }
        }

        if(checkDB != null){
            checkDB.close();
        }
    }

    private void copyDataBase() throws IOException
    {
        File f = new File(dbFilePath);
        f.mkdirs(); // crea los directorios donde se guardara la db

        //Obtenemos un InputStream del archivo que esta en assets
        InputStream myInput = context.getAssets().open(dbFileName);

        // creamos el path del archivo de destino
        String outFileName = dbFilePath + dbFileName;


        //Obtenemos un output stream para escribir en el path de destino
        OutputStream myOutput = new FileOutputStream(outFileName);

        //copiamos los bytes del inputstream al outputstream

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0)
        {
            Log.d("helper","Copying "+length+" bytes");
            myOutput.write(buffer, 0, length);
        }
        //Cerramos los streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Se ejecuta cuando hay un cambio de version
    }
}
