package com.community.core.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Room type converters for complex data types
 * 
 * These converters allow Room to store complex objects like lists and maps
 * by converting them to JSON strings and back.
 */
class Converters {
    
    private val gson = Gson()
    
    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return gson.toJson(value)
    }
    
    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return if (value == null) {
            null
        } else {
            val listType = object : TypeToken<List<String>>() {}.type
            gson.fromJson(value, listType)
        }
    }
    
    @TypeConverter
    fun fromStringMap(value: Map<String, String>?): String? {
        return gson.toJson(value)
    }
    
    @TypeConverter
    fun toStringMap(value: String?): Map<String, String>? {
        return if (value == null) {
            null
        } else {
            val mapType = object : TypeToken<Map<String, String>>() {}.type
            gson.fromJson(value, mapType)
        }
    }
    
    @TypeConverter
    fun fromStringAnyMap(value: Map<String, Any>?): String? {
        return gson.toJson(value)
    }
    
    @TypeConverter
    fun toStringAnyMap(value: String?): Map<String, Any>? {
        return if (value == null) {
            null
        } else {
            val mapType = object : TypeToken<Map<String, Any>>() {}.type
            gson.fromJson(value, mapType)
        }
    }
    
    @TypeConverter
    fun fromDoubleList(value: List<Double>?): String? {
        return gson.toJson(value)
    }
    
    @TypeConverter
    fun toDoubleList(value: String?): List<Double>? {
        return if (value == null) {
            null
        } else {
            val listType = object : TypeToken<List<Double>>() {}.type
            gson.fromJson(value, listType)
        }
    }
}
