package AoC2023.day07

import readInput

data class Hand(val cards: String, val hasJoker: Boolean = false) : Comparable<Hand> {

    private val cardOrder = if (!hasJoker) {
        listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
    } else {
        listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')
    }


    private fun handType(cards: String): Int {
        val groups = cards.groupBy { it }.entries.map { (key, value) ->
            key to value.size
        }.sortedByDescending {
            it.second
        }.toMutableList()

        if (hasJoker) {
            val jokerIndex = groups.indexOfFirst { it.first == 'J' }
            val biggerIndex = groups.indexOfFirst { it.first != 'J' }
            if (jokerIndex >= 0 && biggerIndex >= 0) {
                groups[biggerIndex] =
                    groups[biggerIndex].first to (groups[biggerIndex].second + groups[jokerIndex].second)
                groups.removeAt(jokerIndex)
            }
        }

        return when {
            groups.size == 1 -> 1
            groups.size == 2 && groups.first().second == 4 -> 2
            groups.size == 2 && groups.first().second == 3 -> 3
            groups.size == 3 && groups.first().second == 3 -> 4
            groups.size == 3 && groups.first().second == 2 -> 5
            groups.size == 4 && groups.first().second == 2 -> 6
            else -> 7
        }
    }

    override fun compareTo(other: Hand): Int {
        val thisHandType = handType(this.cards)
        val otherHandType = handType(other.cards)

        if (thisHandType != otherHandType) {
            return thisHandType.compareTo(otherHandType)
        } else {
            for (index in this.cards.indices) {
                val thisCardOrder = cardOrder.indexOf(this.cards[index])
                val otherCardOrder = cardOrder.indexOf(other.cards[index])

                if (thisCardOrder != otherCardOrder) {
                    return thisCardOrder.compareTo(otherCardOrder)
                }
            }
        }
        return 0
    }


}

fun totalWinning(input: List<String>, hasJoker: Boolean): Long {
    val size = input.size
    return input.map { line ->
        val (cards, bid) = line.split(" ").map { it.trim() }
        Hand(cards, hasJoker) to bid.toLong()
    }.sortedBy {
        it.first
    }.mapIndexed { index, (_, bid) ->
        (size - index) * bid
    }.sum()
}

fun part1(input: List<String>): Long {

    return totalWinning(input, false)
}

fun part2(input: List<String>): Long {
    return totalWinning(input, true)
}

fun main() {
    val inputTest = readInput("day07_test", "day07")
    check(part1(inputTest) == 6440L)
    check(part2(inputTest) == 5905L)

    println(part1(readInput("day07", "day07")))
    println(part2(readInput("day07", "day07")))

}