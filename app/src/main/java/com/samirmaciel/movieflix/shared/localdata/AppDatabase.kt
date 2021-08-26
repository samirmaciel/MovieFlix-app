package com.samirmaciel.movieflix.shared.localdata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.samirmaciel.movieflix.shared.dao.MovieWatchLaterDao
import com.samirmaciel.movieflix.shared.dao.MovieWatchedDao
import com.samirmaciel.movieflix.shared.model.local.MovieWatchLaterEntityLocal
import com.samirmaciel.movieflix.shared.model.local.MovieWatchedEntityLocal


@Database (entities = [MovieWatchedEntityLocal::class, MovieWatchLaterEntityLocal::class], version = 7)
abstract class AppDatabase : RoomDatabase() {

    abstract fun MovieWatchedDao() : MovieWatchedDao
    abstract fun MovieWatchLaterDao() : MovieWatchLaterDao

    companion object{
        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getDatabase(context : Context) : AppDatabase{
            val tempInstace = INSTANCE
            if(tempInstace != null){
                return tempInstace
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance
                return instance
            }
        }
    }

}