package day03

import readInput

private val directions = listOf(-1, 0, 1)

fun main() {

    val testInput = readInput("Day03_test", "day03")
    check(part1(testInput) == 4361L)
    check(part2(testInput) == 467835L)

    val input = readInput("Day03", "day03")
    println(part1(input))
    println(part2(input))

}

fun part1(input: List<String>): Long {
    val board = input.addPadding()

    return board.mapIndexed { row, line ->
        processLine(line, row, board)
    }.sum()
}

fun part2(input: List<String>): Long {
    val board = input.addPadding()
    val partsByGear = mutableMapOf<Pair<Int, Int>, MutableList<Long>>()

    board.forEachIndexed { row, line ->
        processLineForParts(line, row, board, partsByGear)
    }

    return partsByGear.values
        .filter { it.size == 2 }
        .sumOf { it.reduce(Long::times) }
}

private fun processLineForParts(
    line: String,
    row: Int,
    board: List<String>,
    partsByGear: MutableMap<Pair<Int, Int>, MutableList<Long>>
) {
    var currentNumber = StringBuilder()
    val parts = mutableSetOf<Pair<Int, Int>>()

    line.forEachIndexed { column, cell ->
        if (cell.isDigit()) {
            currentNumber.append(cell)
            board.checkAdjacent(column, row) { adj, x, y ->
                if (adj == '*') parts.add(x to y)
            }
        } else if (currentNumber.isNotEmpty()) {
            updatePartsByGear(partsByGear, parts, currentNumber.toString())
            currentNumber.clear()
            parts.clear()
        }
    }
}

private fun updatePartsByGear(
    partsByGear: MutableMap<Pair<Int, Int>, MutableList<Long>>,
    parts: Set<Pair<Int, Int>>,
    currentNumber: String
) {
    if (parts.isNotEmpty()) {
        val partNumber = currentNumber.toLong()
        parts.forEach { pos ->
            partsByGear.getOrPut(pos) { mutableListOf() }.add(partNumber)
        }
    }
}


private fun processLine(line: String, row: Int, board: List<String>): Long {
    var sum = 0L
    var currentNumber = StringBuilder()
    var hasSymbolNear = false

    line.forEachIndexed { column, cell ->
        if (cell.isDigit()) {
            currentNumber.append(cell)
            hasSymbolNear = hasSymbolNear || hasAdjacentSymbol(board, column, row)
        } else {
            sum += sumIfValidNumber(currentNumber, hasSymbolNear)
            currentNumber.clear()
            hasSymbolNear = false
        }
    }

    return sum
}

private fun hasAdjacentSymbol(board: List<String>, column: Int, row: Int): Boolean {
    var symbolFound = false
    board.checkAdjacent(column, row) { adj, _, _ ->
        if (!adj.isDigit() && adj != '.') {
            symbolFound = true
        }
    }
    return symbolFound
}

private fun sumIfValidNumber(currentNumber: StringBuilder, hasSymbolNear: Boolean): Long {
    return if (currentNumber.isNotEmpty() && hasSymbolNear) {
        currentNumber.toString().toLong()
    } else {
        0L
    }
}

fun List<String>.addPadding(): List<String> {
    val lineLength = this.firstOrNull()?.length?.plus(1) ?: 0
    val paddedLine = ".".repeat(lineLength)
    return listOf(paddedLine) + this.map { "$it." } + paddedLine
}

fun List<String>.checkAdjacent(column: Int, row: Int, onAdjacent: (Char, Int, Int) -> Unit) {
    val height = this.size
    val width = this.firstOrNull()?.length ?: 0

    directions.forEach { dx ->
        directions.forEach { dy ->
            if (dx != 0 || dy != 0) {
                val newRow = row + dx
                val newCol = column + dy

                if (newRow in 0 until height && newCol in 0 until width) {
                    onAdjacent(this[newRow][newCol], newRow, newCol)
                }
            }
        }
    }
}
