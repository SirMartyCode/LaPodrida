package com.sirmarty.lapodrida.data.repository

import com.sirmarty.lapodrida.data.database.GameSettingsDB
import com.sirmarty.lapodrida.data.database.LaPodridaDatabase
import com.sirmarty.lapodrida.domain.entities.GameSettings
import com.sirmarty.lapodrida.domain.repository.GameSettingsRepository

class GameSettingsDataRepository(val database: LaPodridaDatabase) : GameSettingsRepository {

    override suspend fun getCurrentGameSettings(): GameSettings? {
        return database.getGameSettingsDAO().getCurrentGameSettings()?.toDomain()
    }

    override suspend fun saveGameSettings(gameSettings: GameSettings) {
        return database.getGameSettingsDAO().save(GameSettingsDB.fromDomain(gameSettings))
    }
}