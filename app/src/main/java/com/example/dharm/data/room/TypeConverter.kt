//package com.example.dharm.data.room
//
//import androidx.annotation.StringRes
//import androidx.room.TypeConverter
//import androidx.room.TypeConverters
//import com.example.dharm.models.verse.VerseItem
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//
//class AppTypeConverter {
//
//    @TypeConverter
//    fun fromListToString(list : List<String>): String{
//        return Gson().toJson(list)
//    }
//    @TypeConverter
//    fun fromStringToList(string : String): List<String>{
//        return Gson().fromJson(string,object :  TypeToken<List<String>>(){}.type)
//    }
//}