package day08

import readInput

fun parseInput(input: List<String>): Pair<String, Map<String, Pair<String, String>>> {

    val directions = input.first()
    val positions = input.drop(2).associate { line ->
        val (path, nextDirections) = line.split(" = ")
        val (positionLeft, positionRight) = nextDirections.drop(1).dropLast(1).split(",")
        path to (positionLeft to positionRight)
    }
    return directions to positions
}


fun part1(input: List<String>): Int {

    val (directions, path) = parseInput(input)
    var currentPosition = "AAA"
    var count = 0
    while (currentPosition.trim() != "ZZZ") {
        val nextDirection = directions[count % directions.length]
        currentPosition = when (nextDirection) {
            'L' -> path[currentPosition.trim()]!!.first
            else -> path[currentPosition.trim()]!!.second
        }
        count++
    }


    return count
}

fun part2(input: List<String>): Long {
    return 0L
}

fun main() {

    val testInput = readInput("day08_test", "day08")
    val input = readInput("day08", "day08")

    println(part1(testInput))
    check(part1(testInput) == 6)
    check(part1(input) == 17621)
    println(part1(input))

//    check(part2(input) == 20685524831999L)
//    println(part2(input))
}