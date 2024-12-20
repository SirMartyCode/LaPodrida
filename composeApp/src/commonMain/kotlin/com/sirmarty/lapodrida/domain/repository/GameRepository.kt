package com.sirmarty.lapodrida.domain.repository

import com.sirmarty.lapodrida.domain.entities.Game

interface GameRepository {
    suspend fun getCurrentGame(): Game?
    suspend fun createGame(game: Game)
    suspend fun isThereUnfinishedGame(): Boolean
    suspend fun deleteCurrentGame()
    suspend fun getFinishedGames(): List<Game>
}