package com.sirmarty.lapodrida.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sirmarty.lapodrida.data.database.GameSettingsDB

@Dao
interface GameSettingsDAO {

    @Query("SELECT * FROM gameSettings")
    suspend fun getCurrentGameSettings(): GameSettingsDB?

    @Insert
    suspend fun save(gameSettings: GameSettingsDB)
}