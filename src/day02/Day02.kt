package day02

import println
import readInput

fun main() {

    fun parseGameSets(game: String) =
        game.split(";").map {
            it.trim().split(",").map {
                val (cubesStr, color) = it.trim().split(" ")
                val cubes = cubesStr.toInt()
                color to cubes
            }
        }

    fun evaluateGameValidity(game: String): Int {
        val (gameId, gameInfo) = game.split(":")
        val (_, id) = gameId.split(" ")

        val sets = parseGameSets(gameInfo)
        val isValid = sets.all { set ->
            set.all { (color, cubes) ->
                when (color) {
                    "red" -> cubes <= 12
                    "green" -> cubes <= 13
                    "blue" -> cubes <= 14
                    else -> false
                }
            }
        }

        return if (isValid) id.toInt() else 0
    }

    fun calculateGameStrength(game: String): Int {
        val (_, gameInfo) = game.split(":")

        val sets = parseGameSets(gameInfo)

        val maxCubes = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
        sets.forEach { set ->
            set.forEach { (color, cubes) ->
                maxCubes[color] = maxOf(maxCubes[color] ?: 0, cubes)
            }
        }

        return maxCubes.values.reduce { acc, i -> acc * i }
    }

    fun part1(input: List<String>) = input.sumOf { evaluateGameValidity(it) }

    fun part2(input: List<String>) = input.sumOf { calculateGameStrength(it) }


    val testInput = readInput("Day02_test", "day02")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)



    val input = readInput("Day02", "day02")
    part1(input).println()

    println("_______________________________________ PART 2")
    part2(input).println()
}
