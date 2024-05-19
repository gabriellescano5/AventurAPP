package com.example.aventurapp.gastos;

import androidx.room.TypeConverter;

import java.util.Date;


// Esta clase se utiliza para convertir entre objetos Date y valores de tiempo
// en formato Long

//Los métodos están anotados con @TypeConverter, lo que sugiere que se utilizan
//con Room ya que admite persistencia de objetos para convertir entre tipos que la bd pueda almacenar
public class ConvertidoresFecha {
    @TypeConverter
    public static Date fromTimestamp(Long value){
        return value == null ? null : new Date(value);
    }
    @TypeConverter
    public static Long dateToTimestamp(Date date){
        if(date == null){
            return null;
        } else {
            return date.getTime();
        }
    }
}
