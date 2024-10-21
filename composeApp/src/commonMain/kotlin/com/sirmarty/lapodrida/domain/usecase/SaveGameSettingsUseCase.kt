package com.sirmarty.lapodrida.domain.usecase

import com.sirmarty.lapodrida.domain.models.GameSettingsModel
import com.sirmarty.lapodrida.domain.repository.GameSettingsRepository

class SaveGameSettingsUseCase(private val repository: GameSettingsRepository) {
    suspend operator fun invoke(gameSettings: GameSettingsModel) {
        repository.saveGameSettings(gameSettings)
    }
}