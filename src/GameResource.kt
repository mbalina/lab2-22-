package com.lab

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.*
import kotlin.text.get

val mapper = jacksonObjectMapper().apply { setSerializationInclusion(JsonInclude.Include.NON_NULL) }

fun Route.widget(gameService: GameService){

    route("/game") {

        get("/{gameId}") {
            val gameId: String = call.parameters["gameId"].toString()
            val game = gameService.getGame(gameId)
            if (game != null)
                call.respond(HttpStatusCode.OK, game)
            else
                call.respond("Some error")
        }

        post("/restart/{gameId}") {
            val gameId = call.parameters["gameId"].toString()
            val game = gameService.restartGame(gameId)
            if (game != null)
                call.respond(HttpStatusCode.OK, game)
            else
                call.respond("Some error")

        }

        post("/{playerId}") {
            val playerId = call.parameters["playerId"].toString()
            val game = gameService.startGame(playerId)
            if (game != null)
                call.respond(HttpStatusCode.OK, game)
            else
                call.respond("Some error")
        }

        post("/cell/{gameId}&{playerId}&{row}&{col}") {
            val gameId = call.parameters["gameId"].toString()
            val playerId = call.parameters["playerId"].toString()
            val row: Int = call.parameters["row"]?.toInt()!!
            val col: Int = call.parameters["col"]?.toInt()!!
            val game = gameService.setCell(gameId, playerId, row, col)
            if (game != null)
                call.respond(HttpStatusCode.OK, game)
            else
                call.respond("Some error")
        }
    }
}