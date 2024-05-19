package com.example.aventurapp.gastos;

import androidx.room.TypeConverter;

import java.util.Date;

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
