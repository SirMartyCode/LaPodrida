package com.sirmarty.lapodrida.domain.repository

import com.sirmarty.lapodrida.domain.entities.Game

interface GamesRepository {
    suspend fun save(game: Game)
    suspend fun getUnfinishedGame(): Game?
    suspend fun getGamesHistory(): List<Game>
    suspend fun isThereUnfinishedGame(): Boolean
    suspend fun deleteUnfinished()
}
