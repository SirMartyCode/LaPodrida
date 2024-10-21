package com.sirmarty.lapodrida.data.repository

import com.sirmarty.lapodrida.data.database.GameSettingsDB
import com.sirmarty.lapodrida.data.database.LaPodridaDatabase
import com.sirmarty.lapodrida.domain.models.GameSettingsModel
import com.sirmarty.lapodrida.domain.repository.GameSettingsRepository

class GameSettingsDataRepository(val database: LaPodridaDatabase) : GameSettingsRepository {

    override suspend fun getCurrentGameSettings(): GameSettingsModel? {
        return database.getGameSettingsDAO().getCurrentGameSettings()?.toDomain()
    }

    override suspend fun saveGameSettings(gameSettings: GameSettingsModel) {
        return database.getGameSettingsDAO().save(GameSettingsDB.fromDomain(gameSettings))
    }
}