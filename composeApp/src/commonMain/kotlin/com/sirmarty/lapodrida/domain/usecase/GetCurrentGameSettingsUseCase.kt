package com.sirmarty.lapodrida.domain.usecase

import com.sirmarty.lapodrida.domain.models.GameSettingsModel
import com.sirmarty.lapodrida.domain.repository.GameSettingsRepository

class GetCurrentGameSettingsUseCase(private val repository: GameSettingsRepository) {
    suspend operator fun invoke(): GameSettingsModel? {
        return repository.getCurrentGameSettings()
    }
}