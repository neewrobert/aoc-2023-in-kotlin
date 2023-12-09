package AoC2023.day09

import println
import readInput


fun calculateExtrapolatedValue(seq: List<Long>, backguard: Boolean): Long {
    // Generating a sequence of pairs where each pair consists of the current list and the factor.

    return generateSequence(seq to 1) { (current, factor) ->
        // The sequence generation stops when all elements of the list are zero.
        if (current.all { it == 0L }) null
        else {
            // Calculate the difference between adjacent elements and update the factor.
            val updatedList = current.windowed(2).map { (a, b) -> b - a }
            val updatedFactor = if (backguard) -factor else factor
            updatedList to updatedFactor
        }
    }.sumOf { (current, factor) ->
        // Sum up the elements based on the backwards flag.
        if (backguard) current.first() * factor else current.last()
    }
}


fun sumOfExtrapolatedValues(input: List<String>, doItBackwards: Boolean): Long {
    // Summing up the extrapolated values for each line in the input.
    return input.sumOf { line ->
        // Splitting each line into numbers and converting them to Long.
        val numbers = line.split("\\s+".toRegex()).map { it.toLong() }
        // Calculating the extrapolated value for each sequence of numbers.
        calculateExtrapolatedValue(numbers, doItBackwards)
    }
}


fun part1(input: List<String>): Long {
    return sumOfExtrapolatedValues(input, false)
}

fun part2(input: List<String>): Long {
    return sumOfExtrapolatedValues(input, true)
}

fun List<Long>.getRatioProgression(): List<Long> {
    if (this.size < 2) return listOf(0)
    return this.sorted().zipWithNext { a, b -> b - a }
}


fun main() {

    val testInput = readInput("day09_test", "AoC2023/day09")
    part1(testInput).println()
    check(part1(testInput) == 114L)

    val input = readInput("day09", "AoC2023/day09")
    println(part1(input))
    println(part2(input))
}