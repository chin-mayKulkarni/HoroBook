package com.chinmay.horobook.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(SongData::class), version = 2)
abstract class SongDataBase: RoomDatabase(){
    abstract fun songDao(): SongDao

    companion object{
        @Volatile private var instance: SongDataBase? = null
        private val LOCK = Any()

        operator fun invoke (context: Context) = instance ?: synchronized(LOCK){
            instance?: buildDataBase(context).also {
                instance = it
            }
        }

        private fun buildDataBase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            SongDataBase::class.java,
            "songdatabase"
        ).build()
    }


}