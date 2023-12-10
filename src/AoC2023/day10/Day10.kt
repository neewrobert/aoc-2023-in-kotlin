package AoC2023.day10

import readInput

val directions = listOf(0 to 1, 1 to 0, 0 to -1, -1 to 0)
val oppositeDirections = intArrayOf(2, 3, 0, 1)
val pathSymbols = "|-LJ7F".toCharArray()
val connectionStates = arrayOf("0101", "1010", "1001", "0011", "0110", "1100")
    .map { it.reversed().toInt(2) }

fun main() {
    val testInput = readInput("day10_test", "AoC2023/day10")
    check(part1(testInput) == 8)
    check(part2(testInput) == 1)

    val input = readInput("day10", "AoC2023/day10")
    println(part1(input))

    println(part2(input))

}

fun part1(input: List<String>): Int {
    val grid = input.toCharArray()
    val (rows, cols) = grid.size()

    return findLoop(grid, rows, cols, directions, oppositeDirections, pathSymbols, connectionStates)?.let { loop ->
        loop.size / 2
    }!!
}

fun part2(input: List<String>): Int {
    val grid = input.toCharArray()
    val (rows, cols) = grid.size()
    val oppositeDirections = listOf(2 to 3, 3 to 0, 0 to 1, 1 to 2)
    val connections = arrayOf("0101", "1010", "1001", "0011", "0110", "1100")
        .map { it.reversed().toInt(2) }

    outerLoop@ for (i in 0 until rows) {
        for (j in 0 until cols) {
            if (grid[i][j] == 'S') {
                return findLoopPath(grid, i, j, rows, cols, directions, oppositeDirections, pathSymbols, connections)
            }
        }
    }

    return 0
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


fun isValidPosition(x: Int, y: Int, rows: Int, cols: Int) = x in 0 until rows && y in 0 until cols

fun isRevisiting(prevX: Int, prevY: Int, newX: Int, newY: Int) = newX == prevX && newY == prevY

fun isPreviousPosition(x: Int, y: Int, prevX: Int, prevY: Int) = x == prevX && y == prevY

fun isNewPositionStart(x: Int, y: Int, startX: Int, startY: Int) = x == startX && y == startY

fun updatePositionsAndConnection(oldX: Int, oldY: Int, newX: Int, newY: Int, newConnection: Int) =
    Quintuple(oldX, oldY, newX, newY, newConnection)


fun findLoopPath(
    grid: Array<CharArray>, startX: Int, startY: Int, rows: Int, cols: Int,
    directions: List<Pair<Int, Int>>, oppositeDirections: List<Pair<Int, Int>>,
    pathSymbols: CharArray, connections: List<Int>
): Int {
    var x = startX
    var y = startY
    var currentConnection = 15
    var previousX = -1
    var previousY = -1
    val path = mutableListOf<Pair<Int, Int>>()

    loop@ while (true) {
        path.add(x to y)
        var found = false

        for ((dIndex, direction) in directions.withIndex()) {
            if ((1 shl dIndex) and currentConnection != 0) {
                val newX = x + direction.first
                val newY = y + direction.second
                if (!isValidPosition(newX, newY, rows, cols) || isPreviousPosition(newX, newY, previousX, previousY))
                    continue
                if (isNewPositionStart(newX, newY, startX, startY)) break@loop
                val symbolIndex = pathSymbols.indexOf(grid[newX][newY])
                if (symbolIndex < 0) continue
                val newConnection = connections[symbolIndex]
                if ((1 shl oppositeDirections[dIndex].first) and newConnection == 0) continue

                updatePositionsAndConnection(x, y, newX, newY, newConnection).also {
                    previousX = it.first
                    previousY = it.second
                    x = it.third
                    y = it.fourth
                    currentConnection = it.fifth
                }
                found = true
                break
            }
        }

        check(found) { "($x,$y) -> ${grid[x][y]}" }
    }

    return countInsidePoints(path, rows, cols)
}


fun countInsidePoints(path: List<Pair<Int, Int>>, rows: Int, cols: Int): Int {
    val b = Array(rows) { IntArray(cols) { -1 } }
    for ((k, point) in path.withIndex()) {
        val (i, j) = point
        b[i][j] = k
    }

    var cnt = 0
    for (i in 0 until rows) {
        var inside = false
        var enter = ' '
        for (j in 0 until cols) {
            val k = b[i][j]
            if (k >= 0) {
                val (pi, pj) = path[(k - 1 + path.size) % path.size]
                val (ni, nj) = path[(k + 1) % path.size]
                val up = ni < i || pi < i
                val dn = ni > i || pi > i
                val lt = nj < j || pj < j
                val rt = nj > j || pj > j
                when {
                    up && dn -> inside = !inside
                    lt && rt -> {}
                    dn && rt -> enter = 'D'
                    dn && lt -> if (enter == 'U') inside = !inside
                    up && rt -> enter = 'U'
                    up && lt -> if (enter == 'D') inside = !inside
                    else -> error("Unexpected path configuration")
                }
            } else {
                if (inside) cnt++
            }
        }
    }

    return cnt
}

data class Quintuple<A, B, C, D, E>(val first: A, val second: B, val third: C, val fourth: D, val fifth: E)
