package com.sirmarty.lapodrida.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sirmarty.lapodrida.data.database.GameDB
import kotlinx.coroutines.flow.Flow

private const val TABLE_NAME = "game"

@Dao
interface GameDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGame(gameDB: GameDB)

    @Query("SELECT * FROM $TABLE_NAME WHERE isFinished = 0")
    fun observeGameInProgress(): Flow<GameDB?>

    @Query("SELECT EXISTS(SELECT * FROM $TABLE_NAME WHERE isFinished = 0)")
    suspend fun hasGameInProgress(): Boolean

    @Query("DELETE FROM $TABLE_NAME WHERE isFinished = 0")
    suspend fun deleteGameInProgress()

    @Query("SELECT EXISTS(SELECT 1 FROM $TABLE_NAME WHERE isFinished = 1)")
    suspend fun hasFinishedGames(): Boolean

    @Query("SELECT * FROM $TABLE_NAME WHERE isFinished = 1")
    suspend fun getFinishedGames(): List<GameDB>
}