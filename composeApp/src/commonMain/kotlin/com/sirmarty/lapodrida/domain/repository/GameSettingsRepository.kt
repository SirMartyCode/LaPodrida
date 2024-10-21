package com.sirmarty.lapodrida.domain.repository

import com.sirmarty.lapodrida.domain.models.GameSettingsModel

interface GameSettingsRepository {
    suspend fun getCurrentGameSettings(): GameSettingsModel?
    suspend fun saveGameSettings(gameSettings: GameSettingsModel)
}