package com.sirmarty.lapodrida.domain.repository

import com.sirmarty.lapodrida.domain.entities.Game
import com.sirmarty.lapodrida.domain.entities.GameSettings
import kotlinx.coroutines.flow.StateFlow

interface CurrentGameRepository {
    val game: StateFlow<Game?>

    suspend fun startNewGame(settings: GameSettings)
    suspend fun submitPredictions(predictions: Map<Int, Int>)
    suspend fun hasGameInProgress(): Boolean
    suspend fun deleteGameInProgress()
}
