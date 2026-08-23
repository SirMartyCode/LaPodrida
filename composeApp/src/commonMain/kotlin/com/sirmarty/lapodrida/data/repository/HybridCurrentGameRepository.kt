package com.sirmarty.lapodrida.data.repository

import com.sirmarty.lapodrida.data.database.GameDB
import com.sirmarty.lapodrida.data.database.LaPodridaDatabase
import com.sirmarty.lapodrida.domain.entities.Game
import com.sirmarty.lapodrida.domain.entities.GameSettings
import com.sirmarty.lapodrida.domain.repository.CurrentGameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HybridCurrentGameRepository(database: LaPodridaDatabase) : CurrentGameRepository {
    private val dao = database.getGameDAO()

    private val _game = MutableStateFlow<Game?>(null)
    override val game: StateFlow<Game?> = _game.asStateFlow()

    override fun startNewGame(settings: GameSettings) {
        val newGame =  Game.create(
            playerNames = settings.playerNames,
            pointsPerWin = settings.pointsPerWin,
            pointsPerHand = settings.pointsPerHand,
            indianRound = settings.indianRound,
        )
        _game.update { newGame }
    }

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
}
