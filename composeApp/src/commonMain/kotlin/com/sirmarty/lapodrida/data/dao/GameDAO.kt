package com.sirmarty.lapodrida.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sirmarty.lapodrida.data.database.GameDB

@Dao
interface GameDAO {

    @Query("SELECT * FROM game")
    suspend fun getCurrentGame(): GameDB?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createGame(gameDB: GameDB)
}