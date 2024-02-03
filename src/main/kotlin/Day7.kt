import java.io.File
import java.util.*
import kotlin.collections.HashSet
import kotlin.io.path.Path

object Day7 {
    const val VALUE = 1110
    private val hands = LinkedList<Hand>()

    private class Hand(type: String, bid: Int): Comparable<Hand> {
        companion object {
            private const val DEFAULT_ORDER = -1
            private val ORDER = mapOf(
                '2' to 2,
                '3' to 3,
                '4' to 4,
                '5' to 5,
                '6' to 6,
                '7' to 7,
                '8' to 8,
                '9' to 9,
                'T' to 10,
                'J' to 11,
                'Q' to 12,
                'K' to 13,
                'A' to 14
            )
            private const val DEFAULT_STRENGTH = -1
        }

        val type: String
        val bid: Int
        var strength: Int
            private set

        init {
            this.type = type
            this.bid = bid
            this.strength = DEFAULT_STRENGTH
        }

        fun setStrength() {
            val cards = HashSet<Char>()
            for (t in type) {
                cards.add(t)
            }
            when (cards.size) {
                5   -> strength = 0
                4   -> strength = 1
                3   -> {
                    for (c in cards) {
                        var count = 0
                        for (t in type) {
                            if (t == c) count++
                        }
                        if (count == 2) strength = 2
                        else if (count == 3) strength = 3
                    }
                }
                2   -> {
                    for (c in cards) {
                        var count = 0
                        for (t in type) {
                            if (t == c) count++
                        }
                        if (count == 3) strength = 4
                        else if (count == 4) strength = 5
                    }
                }
                1   -> strength = 6
                else -> strength = DEFAULT_STRENGTH
            }
        }

        override fun compareTo(other: Hand): Int {
            if (strength != other.strength) {
                return strength.compareTo(other.strength)
            } else {
                for (i in type.indices) {
                    val card = type[i]
                    val otherCard = other.type[i]
                    if (card != otherCard) {
                        val order = ORDER.getOrDefault(card, DEFAULT_ORDER)
                        val otherOrder = ORDER.getOrDefault(otherCard, DEFAULT_ORDER)
                        return order.compareTo(otherOrder)
                    }
                }
                return strength.compareTo(other.strength)
            }
        }

        override fun toString(): String {
            return "($type, $bid, $strength)"
        }
    }

    fun count(): Long {
        val path = MAIN_INPUT_PATH.format("Day7")
        return count(path)
    }

    fun count(path: String): Long {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        while (scanner.hasNext()) {
            setHand(scanner)
        }
        sortByRank()
        return countResult()
    }

    private fun setHand(scanner: Scanner) {
        val line = scanner.nextLine().split(Regex("( )+"))
        val hand = Hand(line[0], line[1].toInt())
        hand.setStrength()
        println(hand)
        hands.add(hand)
    }

    private fun sortByRank() {
        hands.sort()
    }

    private fun countResult(): Long {
        var result = 0L
        for (i in hands.indices) {
            result += hands[i].bid * (i + 1)
        }
        return result
    }
}