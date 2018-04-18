package com.example.alexandrareinhart.videogamelibrary;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateConverter {

    @TypeConverter
    public static Date fromTimestamp (Long value){
        return value == null ? null : new Date(value);
        //does value = null? if it is, leave it null. if not, convert to new date (value).
        //TODO look up ternary
    }

    @TypeConverter
    public static Long dateToTimestamp (Date date){
        return date == null ? null : date.getTime();
        //room was created by google to create local persistence. look up/study.
    }
}
