package com.chinmay.horobook.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SongDao {

    @Insert
    suspend fun insertAll(vararg songs: SongData): List<Long>

    @Query("SELECT * FROM songdata")
    suspend fun getAllSongs(): List<SongData>

    @Query("SELECT * FROM songdata WHERE uuid = :dogId")
    suspend fun getSong(dogId: Int): SongData

    @Query("DELETE FROM songdata")
    suspend fun deleteAllSongs()
}