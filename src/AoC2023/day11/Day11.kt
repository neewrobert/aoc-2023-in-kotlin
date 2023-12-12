package AoC2023.day11

import readInput
import kotlin.math.abs

fun main() {


    val input = readInput("day11", "AoC2023/day11")

    println(part1(input))
    println(part2(input))


}

fun part1(input: List<String>) = calculateTotalPathDistance(input, 2)

fun part2(input: List<String>, expansion: Int = 1_000_000) = calculateTotalPathDistance(input, expansion)

fun calculateTotalPathDistance(galaxyMap: List<String>, expansionFactor: Int): Long {
    val galaxies = findGalaxies(galaxyMap)
    expandGalaxies(galaxies, galaxyMap, expansionFactor)
    return calculatePathDistances(galaxies)
}

private fun findGalaxies(galaxyMap: List<String>): List<Galaxy> {
    val galaxies = mutableListOf<Galaxy>()
    galaxyMap.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { colIndex, cell ->
            if (cell == '#') {
                galaxies += Galaxy(rowIndex, colIndex)
            }
        }
    }
    return galaxies
}

private fun expandGalaxies(galaxies: List<Galaxy>, galaxyMap: List<String>, expansionFactor: Int) {
    val (rows, cols) = galaxyMap.size to galaxyMap.first().length
    val emptyRows = (0 until rows).filterNot { row -> galaxyMap[row].contains('#') }
    val emptyCols = (0 until cols).filterNot { col -> galaxyMap.any { it[col] == '#' } }

    galaxies.forEach { galaxy ->
        galaxy.row += calculateExpansion(galaxy.row, emptyRows, expansionFactor)
        galaxy.col += calculateExpansion(galaxy.col, emptyCols, expansionFactor)
    }
}

private fun calculateExpansion(index: Int, emptyIndices: List<Int>, expansionFactor: Int): Int {
    return (index downTo 0).count { it in emptyIndices } * (expansionFactor - 1)
}

private fun calculatePathDistances(galaxies: List<Galaxy>): Long {
    return galaxies.allPairs().sumOf { (gal1, gal2) ->
        (abs(gal1.row - gal2.row) + abs(gal1.col - gal2.col)).toLong()
    }
}

private fun <T> List<T>.allPairs(): List<Pair<T, T>> {
    return flatMapIndexed { index, element ->
        subList(index + 1, size).map { element to it }
    }
}

data class Galaxy(var row: Int, var col: Int)