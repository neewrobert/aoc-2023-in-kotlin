package AoC2023.day05


import println
import readInput
import kotlin.math.abs


data class MapRange(
    val destinationRangeStart: Long,
    val sourceRangeStart: Long,
    val length: Long
) {
    fun sourceRangeEnd() = sourceRangeStart + length - 1

    fun containsSourceIndex(index: Long): Boolean {
        return (index <= sourceRangeEnd() && index >= sourceRangeStart)
    }


    fun getCorrespondentDestinationValueFromSourceValue(value: Long): Long {
        if (containsSourceIndex(value)) {

            val destinationIndex = abs(sourceRangeStart - value)
            return destinationRangeStart + destinationIndex;

        }
        return -1
    }

}

fun getSeedsFromInput(input: List<String>): List<Long> {

    for (line in input) {
        if (line.contains("seeds:")) {
            return line.split("seeds:")[1].trim().split(" ").map { it.toLong() }
        }
    }
    return listOf()
}


fun getMapRanges(input: List<String>): MutableMap<String, MutableList<MapRange>> {


    val mapRanges = mutableMapOf<String, MutableList<MapRange>>();
    var currentMapName = ""

    for (line in input) {
        if (line.contains("map:")) {
            currentMapName = line.split("map:")[0].trim()
            mapRanges[currentMapName] = mutableListOf()
        } else if (line.contains("seeds:").not() && line.isNotBlank()) {
            val parts = line.split(" ").map { it.toLong() }
            if (parts.size == 3) {
                val (destinationRangeStart, sourceRangeStart, length) = parts
                mapRanges[currentMapName]!!.add(MapRange(destinationRangeStart, sourceRangeStart, length))
            }
        }
    }

    return mapRanges
}

fun getCorrespondentFromMapRange(source: Long, mapRanges: List<MapRange>): Long {
    for (mapRange in mapRanges) {
        if (mapRange.containsSourceIndex(source)) {
            return mapRange.getCorrespondentDestinationValueFromSourceValue(source)
        }
    }
    return source
}


fun part1(input: List<String>): Long {
    val mapRanges = getMapRanges(input)
    val seeds = getSeedsFromInput(input).asSequence()

    return getLocations(seeds, mapRanges).min()

}

private fun getLocations(
    seeds: Sequence<Long>,
    mapRanges: MutableMap<String, MutableList<MapRange>>
): Sequence<Long> {
    return seeds.map { seed ->
        sequenceOf(seed)
            .map { getCorrespondentFromMapRange(it, mapRanges["seed-to-soil"]!!) }
            .map { getCorrespondentFromMapRange(it, mapRanges["soil-to-fertilizer"]!!) }
            .map { getCorrespondentFromMapRange(it, mapRanges["fertilizer-to-water"]!!) }
            .map { getCorrespondentFromMapRange(it, mapRanges["water-to-light"]!!) }
            .map { getCorrespondentFromMapRange(it, mapRanges["light-to-temperature"]!!) }
            .map { getCorrespondentFromMapRange(it, mapRanges["temperature-to-humidity"]!!) }
            .map { getCorrespondentFromMapRange(it, mapRanges["humidity-to-location"]!!) }
            .last()
    }
}

fun createPairsFromList(listOfLongs: List<Long>): List<Pair<Long, Long>> {
    return listOfLongs.windowed(size = 2, step = 2, partialWindows = false) {
        Pair(it[0], it[1])
    }
}

fun createSeedsSequence(seedsInPairs: List<Pair<Long, Long>>): Sequence<Long> {
    return seedsInPairs.asSequence().flatMap { (first, second) ->
        val end = first + (second - 1L)
        (first..end).asSequence()
    }
}

fun part2(input: List<String>): Long {
    val mapRanges = getMapRanges(input)
    val seeds = getSeedsFromInput(input).asSequence()
    val seedsInPairs = createPairsFromList(seeds.toList())

    val newSeeds = createSeedsSequence(seedsInPairs)


    return getLocations(newSeeds, mapRanges).min()

}

fun main() {

    val testInput = readInput("Day05_test", "day05")

    println(part1(testInput))
    check(part1(testInput) == 35L)
    println(part2(testInput))
    check(part2(testInput) == 46L)


    val input = readInput("Day05", "day05")
    println("Part 1: " + part1(input))

    println("_______________________________________ PART 2")
    part2(input).println()
}