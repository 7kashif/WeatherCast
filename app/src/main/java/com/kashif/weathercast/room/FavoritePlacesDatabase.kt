package com.kashif.weathercast.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FavoritePlace::class], version = 3, exportSchema = false)
@TypeConverters(LatLngTypeConverter::class)
abstract class FavoritePlacesDatabase : RoomDatabase() {
    abstract val favoritePlaceDao: FavoritePlacesDao

    companion object {
        @Volatile
        private var INSTANCE: FavoritePlacesDatabase? = null

        fun getInstance(context: Context): FavoritePlacesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoritePlacesDatabase::class.java,
                    "favoritePlaces_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }

    }

}