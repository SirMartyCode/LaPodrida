package com.sirmarty.lapodrida.domain.usecase

import com.sirmarty.lapodrida.domain.entities.GameSettings
import com.sirmarty.lapodrida.domain.repository.GameSettingsRepository

class GetCurrentGameSettingsUseCase(private val repository: GameSettingsRepository) {
    suspend operator fun invoke(): GameSettings? {
        return repository.getCurrentGameSettings()
    }
}