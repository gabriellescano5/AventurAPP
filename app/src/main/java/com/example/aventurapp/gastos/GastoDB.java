package com.example.aventurapp.gastos;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {GastoTabla.class}, version = 1)
public abstract class GastoDB extends RoomDatabase {

    public abstract GastoDAO getDao();

    public static volatile GastoDB INSTANCE;


//    Fragmento utilizado para crear una instancia de una base de datos utilizando la biblioteca Room
    public static GastoDB getInstance(Context context){
//        Esta línea del if verifica si la instancia actual de la base de datos GastoDB es nula,
//        lo que significa que aún no se ha creado.
        if(INSTANCE==null){
//   Si la instancia es nula, se crea una nueva instancia utilizando el constructor de la base de datos de Room.
//   context es el contexto de la aplicación, GastoDB.class es la clase de la base de datos que extiende RoomDatabase,
//   y "gasto" es el nombre del archivo de la base de datos.
            INSTANCE= Room.databaseBuilder(context, GastoDB.class,"gasto")
//   Permite que las consultas se ejecuten en el hilo principal, lo cual no es recomendable para operaciones de larga duración ya que puede
//   congelar la interfaz de usuario.
                    .allowMainThreadQueries()
//   En caso de que se haya incrementado la versión de la base de datos y no se haya proporcionado una migración,
//   esta opción destruirá y recreará la base de datos en lugar de bloquear la ejecución.
                    .fallbackToDestructiveMigration()
// Finaliza la construcción de la instancia de la base de datos y la devuelve para su uso.
                    .build();

        }
        return INSTANCE;
    }
}
