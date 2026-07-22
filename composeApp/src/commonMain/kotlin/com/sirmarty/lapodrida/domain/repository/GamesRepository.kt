package com.sirmarty.lapodrida.domain.repository

import com.sirmarty.lapodrida.domain.entities.Game

interface GamesRepository {
    suspend fun save(game: Game)
    suspend fun getGameInProgress(): Game?
    suspend fun hasGameInProgress(): Boolean
    suspend fun deleteGameInProgress()
    suspend fun getGamesHistory(): List<Game>
}
