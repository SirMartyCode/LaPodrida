package com.sirmarty.lapodrida.data.repository

import com.sirmarty.lapodrida.data.database.GameDB
import com.sirmarty.lapodrida.data.database.LaPodridaDatabase
import com.sirmarty.lapodrida.domain.entities.Game
import com.sirmarty.lapodrida.domain.repository.GamesRepository

class RoomGamesRepository(database: LaPodridaDatabase) : GamesRepository {
    private val dao = database.getGameDAO()

    override suspend fun save(game: Game) {
        dao.createGame(GameDB.fromDomain(game))
    }

    override suspend fun getGameInProgress(): Game? {
        return dao.getGameInProgress()?.toDomain()
    }

    override suspend fun hasGameInProgress(): Boolean {
        return dao.hasGameInProgress()
    }

    override suspend fun deleteGameInProgress() {
        dao.deleteGameInProgress()
    }

    override suspend fun getGamesHistory(): List<Game> {
        return dao.getFinishedGames().map { it.toDomain() }
    }
}
