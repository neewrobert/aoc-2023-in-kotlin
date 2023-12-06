package day06

import readInput

fun calculateValidCombinations(times: List<Long>, distances: List<Long>): Long {
    return times.zip(distances).map { (time, distance) ->
        (1L until time).count {
            it * (time - it) > distance
        }
    }.fold(1L) { acc, count -> acc * count }
}

fun part1(input: List<String>): Long {
    val (times, distances) = input.map { line -> line.split("\\s+".toRegex()).drop(1).map { it.toLong() } }
    return calculateValidCombinations(times, distances)
}

fun part2(input: List<String>): Long {
    val (times, distances) = input.map { line ->
        listOf(
            line.split(":").last().replace("\\s+".toRegex(), "").toLong()
        )
    }
    return calculateValidCombinations(times, distances)
}


fun main() {

    val testInput = readInput("day06_test", "day06")

    check(part1(testInput) == 288L)
    check(part2(testInput) == 71503L)

    val input = readInput("day06", "day06")

    println(part1(input))

    println(part2(input))

}