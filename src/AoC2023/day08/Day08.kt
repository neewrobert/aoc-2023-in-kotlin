package AoC2023.day08

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
    var endPosition = "ZZZ"
    var count = countSteps(currentPosition, endPosition, directions, path)


    return count
}

private fun countSteps(
    startPosition: String,
    endPosition: String,
    directions: String,
    path: Map<String, Pair<String, String>>
): Int {
    var currentPosition = startPosition
    var count = 0
    while (currentPosition.trim() != endPosition) {
        val nextDirection = directions[count % directions.length]
        currentPosition = when (nextDirection) {
            'L' -> path[currentPosition.trim()]!!.first
            else -> path[currentPosition.trim()]!!.second
        }
        count++
    }
    return count
}

private fun countStepsPart2(
    startPosition: String,
    directions: String,
    path: Map<String, Pair<String, String>>
): Int {
    var currentPosition = startPosition
    var count = 0
    while (currentPosition.trim().endsWith("Z").not()) {
        val nextDirection = directions[count % directions.length]
        currentPosition = when (nextDirection) {
            'L' -> path[currentPosition.trim()]!!.first
            else -> path[currentPosition.trim()]!!.second
        }
        count++
    }
    return count
}

fun List<Long>.findLeastCommonMultiple(): Long {
    fun greatestCommonDivisor(first: Long, second: Long): Long =
        if (second == 0L) first else greatestCommonDivisor(second, first % second)

    fun leastCommonMultiple(first: Long, second: Long): Long =
        (first * second) / greatestCommonDivisor(first, second)

    return this.reduce { accumulated, number -> leastCommonMultiple(accumulated, number) }
}

fun part2(input: List<String>): Long {

    val (directions, path) = parseInput(input)


    val nodesEndingWithA = path.filter { it.key.trim().endsWith("A") }
    return nodesEndingWithA.map { (startPosition, _) ->
         countStepsPart2(startPosition, directions, path)
    }.map { it.toLong() }.findLeastCommonMultiple()

}

fun main() {

    val testInput = readInput("day08_test", "day08")
    val input = readInput("day08", "day08")

    println(part1(testInput))
    check(part1(testInput) == 6)
    println(part1(input))

    val part2Result = part2(input)
    println(part2Result)

}