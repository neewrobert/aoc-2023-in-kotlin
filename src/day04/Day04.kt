package day04

import println
import readInput

fun countMatches(cardLine: String): Int {
    val (_, cards) = cardLine.split(": ")
    val (win, mine) = cards.split("|").map { group ->
        group.trim().split("\\s+".toRegex()).map { num ->
            num.toInt()
        }
    }
    val winSet = win.toSet()
    return mine.count { it in winSet }
}

fun part1(input: List<String>): Int = input.sumOf { line ->
    val matches = countMatches(line)
    Math.pow(2.0, (matches - 1).toDouble()).toInt()
}

fun part2(input: List<String>): Int {
    val cardCopies = IntArray(input.size) { 1 }
    for ((index, line) in input.withIndex()) {
        val matches = countMatches(line)
        if (matches > 0) {
            for (i in 1..matches) {
                if (index + i >= cardCopies.size) break
                cardCopies[index + i] += cardCopies[index]
            }
        }
    }
    return cardCopies.sum()
}

fun main() {

    val input = readInput("Day04", "day04")
    part1(input).println()

    println("_______________________________________ PART 2")
    part2(input).println()
}