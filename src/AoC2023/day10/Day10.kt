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

//a loop is a path that starts and ends at the same point, in this case the starting point is 'S'
// adapted from EDX Introduction to Artificial Intelligence (AI) with Python course
fun findLoop(
    grid: Array<CharArray>,
    rows: Int,
    cols: Int,
    directions: List<Pair<Int, Int>>,
    oppositeDirections: IntArray,
    pathSymbols: CharArray,
    connectionStates: List<Int>
): List<Pair<Int, Int>>? {
    for (x in grid.indices) { //X axis
        for (y in grid[x].indices) { //Y axis
            if (grid[x][y] == 'S') { // starting point
                return traverseLoop(
                    grid,
                    x,
                    y,
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

//First time using TAIL REC!!!!!!
tailrec fun traverseLoop(
    grid: Array<CharArray>,
    startX: Int,
    startY: Int,
    rows: Int,
    cols: Int,
    directions: List<Pair<Int, Int>>,
    oppositeDirections: IntArray,
    pathSymbols: CharArray,
    connectionStates: List<Int>,
    x: Int = startX,
    y: Int = startY,
    previousX: Int = -1,
    previousY: Int = -1,
    currentConnectionState: Int = 15,
    loopPath: MutableList<Pair<Int, Int>> = mutableListOf()
): List<Pair<Int, Int>> {
    loopPath.add(x to y)

    directions.forEachIndexed { index, (dx, dy) ->
        if (((1 shl index) and currentConnectionState) != 0) {
            val newX = x + dx
            val newY = y + dy

            if (!isValidPosition(newX, newY, rows, cols) || isRevisiting(previousX, previousY, newX, newY)) {
                return@forEachIndexed
            }

            if (newX == startX && newY == startY) {
                return loopPath
            }

            val symbolIndex = pathSymbols.indexOf(grid[newX][newY])
            if (symbolIndex >= 0 && ((1 shl oppositeDirections[index]) and connectionStates[symbolIndex]) != 0) {
                return traverseLoop(
                    grid, startX, startY, rows, cols, directions, oppositeDirections, pathSymbols, connectionStates,
                    newX, newY, x, y, connectionStates[symbolIndex], loopPath
                )
            }
        }
    }

    check(false) { "($x,$y) -> ${grid[x][y]}" }
    return loopPath
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
    check(part1(input) == 6701)

}

