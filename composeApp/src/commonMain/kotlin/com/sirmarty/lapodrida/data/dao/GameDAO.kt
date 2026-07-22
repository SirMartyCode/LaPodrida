package com.sirmarty.lapodrida.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sirmarty.lapodrida.data.database.GameDB

private const val TABLE_NAME = "game"

@Dao
interface GameDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createGame(gameDB: GameDB)

    @Query("SELECT * FROM $TABLE_NAME WHERE isFinished = 0")
    suspend fun getGameInProgress(): GameDB?

    @Query("SELECT EXISTS(SELECT * FROM $TABLE_NAME WHERE isFinished = 0)")
    suspend fun hasGameInProgress(): Boolean

    @Query("DELETE FROM $TABLE_NAME WHERE isFinished = 0")
    suspend fun deleteGameInProgress()

    @Query("SELECT * FROM $TABLE_NAME WHERE isFinished = 1")
    suspend fun getFinishedGames(): List<GameDB>
}