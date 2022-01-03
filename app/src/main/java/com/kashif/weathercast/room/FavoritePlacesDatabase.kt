package com.kashif.weathercast.room

import android.content.Context
import androidx.room.*

@Database(entities = [FavoritePlace::class], version = 2, exportSchema = false)
@TypeConverters(LatLngTypeConverter::class)
abstract class FavoritePlacesDatabase : RoomDatabase() {
    abstract val favoritePlaceDao: FavoritePlacesDao

    companion object {
        private var INSTANCE: FavoritePlacesDatabase? = null

        fun getInstance(context: Context): FavoritePlacesDatabase {
            var instance = INSTANCE
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoritePlacesDatabase::class.java,
                    "favoritePlaces_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
            }
            return instance
        }

    }

}