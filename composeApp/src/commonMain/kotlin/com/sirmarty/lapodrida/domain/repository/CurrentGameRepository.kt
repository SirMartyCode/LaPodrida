package com.sirmarty.lapodrida.domain.repository

import com.sirmarty.lapodrida.domain.entities.Game
import com.sirmarty.lapodrida.domain.entities.GameSettings
import kotlinx.coroutines.flow.StateFlow

interface CurrentGameRepository {
    val game: StateFlow<Game?>

    fun startNewGame(settings: GameSettings)
    suspend fun save(game: Game)
    suspend fun getGameInProgress(): Game?
    suspend fun hasGameInProgress(): Boolean
    suspend fun deleteGameInProgress()
}
