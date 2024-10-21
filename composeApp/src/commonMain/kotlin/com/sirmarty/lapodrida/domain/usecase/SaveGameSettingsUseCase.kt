package com.sirmarty.lapodrida.domain.usecase

import com.sirmarty.lapodrida.domain.entities.GameSettings
import com.sirmarty.lapodrida.domain.repository.GameSettingsRepository

class SaveGameSettingsUseCase(private val repository: GameSettingsRepository) {
    suspend operator fun invoke(gameSettings: GameSettings) {
        repository.saveGameSettings(gameSettings)
    }
}