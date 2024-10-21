package com.sirmarty.lapodrida.domain.repository

import com.sirmarty.lapodrida.domain.entities.GameSettings

interface GameSettingsRepository {
    suspend fun getCurrentGameSettings(): GameSettings?
    suspend fun saveGameSettings(gameSettings: GameSettings)
}