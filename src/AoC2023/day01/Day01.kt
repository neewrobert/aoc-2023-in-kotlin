package AoC2023.day01

import println
import readInput

fun main() {

    val wordsToNumber = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    fun String.filterDigits(useWords: Boolean): String {
        if (!useWords) return this.filter { it.isDigit() }

        return this.asSequence()
            .mapIndexed { index, char ->
                if (char.isDigit()) char.toString() else {
                    wordsToNumber.entries.firstOrNull { this.startsWith(it.key, index) }?.value ?: ""
                }
            }
            .joinToString("")
    }


    fun List<String>.sumFilteredDigits(withWords: Boolean) = this.sumOf { line ->
        val digits = line.filterDigits(withWords)
        "${digits.first()}${digits.last()}".toInt()
    }

    fun part1(input: List<String>): Int {
        return input.sumFilteredDigits(false)
    }

    fun part2(input: List<String>): Int {
        return input.sumFilteredDigits(true)
    }


    val testInputPartOne = readInput("Day01_test", "day01")
    val testInputPartTwo = readInput("Day01_test_part2", "day01")
    check(part1(testInputPartOne) == 142)
    println(part2(testInputPartTwo))
    check(part2(testInputPartTwo) == 281)


    val inputDayOne = readInput("Day01", "AoC2023/day01")
    part1(inputDayOne).println()

    println("_______________________________________ PART 2")
    part2(inputDayOne).println()
}
