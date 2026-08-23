package com.sirmarty.lapodrida.data.repository

import com.sirmarty.lapodrida.data.database.GameDB
import com.sirmarty.lapodrida.data.database.LaPodridaDatabase
import com.sirmarty.lapodrida.domain.entities.Game
import com.sirmarty.lapodrida.domain.entities.GameSettings
import com.sirmarty.lapodrida.domain.repository.CurrentGameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HybridCurrentGameRepository(
    database: LaPodridaDatabase
) : CurrentGameRepository {

    private val dao = database.getGameDAO()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override val game: StateFlow<Game?> = dao.observeGameInProgress()
        .map { it?.toDomain() }
        .stateIn(scope, SharingStarted.Eagerly, null)

    override suspend fun startNewGame(settings: GameSettings) {
        val newGame = Game.create(
            playerNames = settings.playerNames,
            pointsPerWin = settings.pointsPerWin,
            pointsPerHand = settings.pointsPerHand,
            indianRound = settings.indianRound,
        )
        save(newGame)
    }

    override suspend fun save(game: Game) {
        dao.saveGame(GameDB.fromDomain(game))
    }

    override suspend fun hasGameInProgress(): Boolean {
        return dao.hasGameInProgress()
    }

    override suspend fun deleteGameInProgress() {
        dao.deleteGameInProgress()
    }
}
