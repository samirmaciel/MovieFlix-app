package com.samirmaciel.movieflix.shared.localdata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.samirmaciel.movieflix.shared.dao.MovieDao
import com.samirmaciel.movieflix.shared.model.local.MovieEntityLocal


@Database (entities = [MovieEntityLocal::class], version = 5)
abstract class AppDatabase : RoomDatabase() {

    abstract fun MovieDao() : MovieDao

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