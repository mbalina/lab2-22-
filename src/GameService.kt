package com.lab

import java.util.*
import kotlin.collections.ArrayList

class GameService {
    var game = Game(UUID.randomUUID().toString())
    private val games = ArrayList<Game>().apply {
        this.add(game)
    }

    suspend fun startGame(playerId: String): Game? {
        when {
            game.playerId1 == "" -> {
                game.playerId1 = playerId
                return game
            }
            game.playerId2 == "" -> {
                game.playerId2 = playerId
                game.startNewGame(game.playerId1, game.playerId2)
                return game
            }
            else -> {
                game = Game(UUID.randomUUID().toString())
                games.add(game)
                return startGame(playerId)
            }
        }
        return null
    }

    suspend fun getGame(gameId: String): Game? {
        for (game in games)
            if(game.gameId == gameId)
                return game
        return null
    }

    suspend fun restartGame(gameId: String): Game? {
        getGame(gameId)?.let { game ->
            game.startNewGame(game.playerId1, game.playerId2)
            return game
        }
        return null
    }

    suspend fun setCell(gameId: String, playerId: String, row: Int, col: Int): Game? {
        getGame(gameId)?.let {
            game.setCell(playerId, row, col)
            return game
        }
        return null
    }
}