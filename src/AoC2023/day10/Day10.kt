package AoC2023.day10

import readInput


fun part1(input: List<String>): Int {
    val grid = input.toCharArray()
    val (rows, cols) = grid.size()
    val directions = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))
    val oppositeDirections = intArrayOf(2, 3, 0, 1)
    val pathSymbols = "|-LJ7F".toCharArray()
    val connectionStates = arrayOf(
        "0101", "1010", "1001", "0011", "0110", "1100"
    ).map { it.reversed().toInt(2) }

    return findLoop(grid, rows, cols, directions, oppositeDirections, pathSymbols, connectionStates)?.let { loop ->
        loop.size / 2
    }!!
}

fun Array<CharArray>.size(): Pair<Int, Int> {
    val rowCount = size
    val columnCount = first().size

    forEachIndexed { rowIndex, row ->
        require(row.size == columnCount) {
            "Row $rowIndex has size ${row.size}, but expected size is $columnCount"
        }
    }

    return Pair(rowCount, columnCount)
}

fun List<String>.toCharArray() = Array(size) { get(it).toCharArray() }
fun findLoop(
    grid: Array<CharArray>,
    rows: Int,
    cols: Int,
    directions: List<Pair<Int, Int>>,
    oppositeDirections: IntArray,
    pathSymbols: CharArray,
    connectionStates: List<Int>
): List<Pair<Int, Int>>? {
    for (i in grid.indices) {
        for (j in grid[i].indices) {
            if (grid[i][j] == 'S') {
                return traverseLoop(
                    grid,
                    i,
                    j,
                    rows,
                    cols,
                    directions,
                    oppositeDirections,
                    pathSymbols,
                    connectionStates
                )
            }
        }
    }
    return null
}

fun traverseLoop(
    grid: Array<CharArray>,
    startX: Int,
    startY: Int,
    rows: Int,
    cols: Int,
    directions: List<Pair<Int, Int>>,
    oppositeDirections: IntArray,
    pathSymbols: CharArray,
    connectionStates: List<Int>
): List<Pair<Int, Int>> {
    var x = startX
    var y = startY
    var currentConnectionState = 15
    var previousX = -1
    var previousY = -1
    val loopPath = mutableListOf<Pair<Int, Int>>()

    while (true) {
        loopPath.add(Pair(x, y))
        var found = false

        for (d in directions.indices) {
            if (((1 shl d) and currentConnectionState) != 0) {
                val newX = x + directions[d].first
                val newY = y + directions[d].second

                if (!isValidPosition(newX, newY, rows, cols) || isRevisiting(previousX, previousY, newX, newY)) {
                    continue
                }

                if (newX == startX && newY == startY) {
                    return loopPath
                }

                val symbolIndex = pathSymbols.indexOf(grid[newX][newY])
                if (symbolIndex < 0) continue

                val nextConnectionState = connectionStates[symbolIndex]
                if (((1 shl oppositeDirections[d]) and nextConnectionState) == 0) continue

                previousX = x
                previousY = y
                x = newX
                y = newY
                currentConnectionState = nextConnectionState
                found = true
                break
            }
        }

        check(found) { "($x,$y) -> ${grid[x][y]}" }
    }
}

fun isValidPosition(x: Int, y: Int, rows: Int, cols: Int): Boolean {
    return x in 0 until rows && y in 0 until cols
}

fun isRevisiting(prevX: Int, prevY: Int, newX: Int, newY: Int): Boolean {
    return newX == prevX && newY == prevY
}

fun main() {
    val testInput = readInput("day10_test", "AoC2023/day10")
    check(part1(testInput) == 8)

    val input = readInput("day10", "AoC2023/day10")
    println(part1(input))

}

