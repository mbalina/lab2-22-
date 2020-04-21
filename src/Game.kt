package com.lab

import kotlin.random.Random

class Game(val gameId: String) {
    // field 8x8
    var field = Array<Array<Int>>(8) {
        Array<Int>(8) {
            0
        }
    }
    var playerId1: String = ""
    var playerId2: String = ""
    var winner: String? = null
    var turn: String = "null"

    fun startNewGame(playerId1: String, playerId2: String) {
        field = Array<Array<Int>>(8) {
            Array<Int>(8) {
                0
            }
        }
        this.playerId1 = playerId1
        this.playerId2 = playerId2
        turn = if(Random.nextInt(2) == 0) playerId1
        else playerId2
        winner = null
    }

    fun setCell(playerId: String, row: Int, column: Int): Boolean {
        if (turn != playerId) return false
        when (playerId) {
            playerId1 -> {
                if (field[row][column] == 0) {
                    field[row][column] = 1
                    winner = checkWinner()
                    turn = playerId2
                    return true
                }
            }
            playerId2 -> {
                if (field[row][column] == 0) {
                    field[row][column] = 2
                    winner = checkWinner()
                    turn = playerId1
                    return true
                }
            }
        }
        return false
    }

    private fun checkWinner(): String? {
        var winner = checkWinnerHorizontal()
        if (winner != null) return winner
        winner = checkWinnerVertical()
        if (winner != null) return winner
        winner = checkWinnerDiagonals()
        if (winner != null) return winner
        return null
    }

    private fun checkWinnerHorizontal(): String? {
        var winner1: Int = 0
        var winner2: Int = 0
        for (row in 0 until field.size) {
            winner1 = 0
            winner2 = 0
            for (col in 0 until field.size - 1) {
                if (field[row][col] == 1 && field[row][col + 1] == 1) {
                    winner2 = 0
                    winner1++
                }
                else if (field[row][col] == 2 && field[row][col + 1] == 2) {
                    winner1 = 0
                    winner2++
                } else if(winner1 != 4 && winner2 != 4) {
                    winner1 = 0
                    winner2 = 0
                }
            }
            // 4 одинаковых символ + символ (col + 1) = 5 символов по факту
            if (winner1 == 4) return playerId1
            if (winner2 == 4) return playerId2
        }
        return null
    }

    private fun checkWinnerVertical(): String? {
        var winner1: Int = 0
        var winner2: Int = 0
        for (col in 0 until field.size) {
            winner1 = 0
            winner2 = 0
            for (row in 0 until field.size - 1) {
                if (field[row][col] == 1 && field[row + 1][col] == 1) {
                    winner2 = 0
                    winner1++
                }
                else if (field[row][col] == 2 && field[row + 1][col] == 2) {
                    winner1 = 0
                    winner2++
                } else if(winner1 != 4 && winner2 != 4) {
                    winner1 = 0
                    winner2 = 0
                }
            }
            // 4 одинаковых символ + символ (col + 1) = 5 символов по факту
            if (winner1 == 4) return playerId1
            if (winner2 == 4) return playerId2
        }
        return null
    }

    private fun checkWinnerDiagonals(): String? {
        var winner1: Int = 0
        var winner2: Int = 0
        for (row in 0 until 4) {
            for (col in 0 until 4) {
                winner1 = 0
                winner2 = 0
                // c левого верхнего угла
                for (i in 0 until 4) {
                    if (field[row + i][col + i] == 1 && field[row + i + 1][col + i + 1] == 1) {
                        winner1++
                        winner2 = 0
                    }
                    else if (field[row + i][col + i] == 2 && field[row + i + 1][col + i + 1] == 2) {
                        winner2++
                        winner1 = 0
                    } else if(winner1 != 4 && winner2 != 4) {
                        winner1 = 0
                        winner2 = 0
                    }
                }
                // 4 одинаковых символ + символ (col + 1) = 5 символов по факту
                if (winner1 == 4) return playerId1
                if (winner2 == 4) return playerId2
                winner1 = 0
                winner2 = 0
                // с правого верхнего угла
                for (i in 0 until 4) {
                    if (field[row + i][col + 4 - i] == 1 && field[row + i + 1][col + 4 - i - 1] == 1) {
                        winner1++
                        winner2 = 0
                    }
                    else if (field[row + i][col + 4 - i] == 2 && field[row + i + 1][col + 4 - i - 1] == 2) {
                        winner2++
                        winner1 = 0
                    } else if(winner1 != 4 && winner2 != 4) {
                        winner1 = 0
                        winner2 = 0
                    }
                }
                // 4 одинаковых символ + символ (col + 1) = 5 символов по факту
                if (winner1 == 4) return playerId1
                if (winner2 == 4) return playerId2
                winner1 = 0
                winner2 = 0
                // с правого нижнего угла
                for (i in 0 until 4) {
                    if (field[row + 4 - i][col + 4 - i] == 1 && field[row + 4 - i - 1][col + 4 - i - 1] == 1) {
                        winner1++
                        winner2 = 0
                    }
                    else if (field[row + 4 - i][col + 4 - i] == 2 && field[row + 4 - i - 1][col + 4 - i - 1] == 2) {
                        winner2++
                        winner1 = 0
                    } else if(winner1 != 4 && winner2 != 4) {
                        winner1 = 0
                        winner2 = 0
                    }
                }
                // 4 одинаковых символ + символ (col + 1) = 5 символов по факту
                if (winner1 == 4) return playerId1
                if (winner2 == 4) return playerId2
                winner1 = 0
                winner2 = 0
                // с левого нижнего угла
                for (i in 0 until 4) {
                    if (field[row + 4 - i][col + i] == 1 && field[row + 4 - i - 1][col + i + 1] == 1) {
                        winner1++
                        winner2 = 0
                    }
                    else if (field[row + 4 - i][col + i] == 2 && field[row + 4 - i - 1][col + i + 1] == 2) {
                        winner2++
                        winner1 = 0
                    } else if(winner1 != 4 && winner2 != 4) {
                        winner1 = 0
                        winner2 = 0
                    }
                }
                // 4 одинаковых символ + символ (col + 1) = 5 символов по факту
                if (winner1 == 4) return playerId1
                if (winner2 == 4) return playerId2
            }
        }
        return null
    }

    override fun equals(other: Any?): Boolean {
        return this.gameId == (other as Game).gameId
    }
}