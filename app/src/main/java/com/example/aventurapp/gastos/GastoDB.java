package com.example.aventurapp.gastos;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {GastoTabla.class}, version = 1)
public abstract class GastoDB extends RoomDatabase {

    public abstract GastoDAO getDao();

    public static volatile GastoDB INSTANCE;

    public static GastoDB getInstance(Context context){
        if(INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context, GastoDB.class,"gasto")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return INSTANCE;
    }
}
