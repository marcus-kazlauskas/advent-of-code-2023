import java.io.File
import java.util.*
import kotlin.collections.HashSet
import kotlin.io.path.Path

object Day4 {
    const val VALUE = 1107

    class Card {
        private val winNumbers = HashSet<Int>()
        private val numbers = LinkedList<Int>()
        private var winNumbersCount = 0

        fun setNumbers(scanner: Scanner) {
            val rawLine = scanner.nextLine().split(Regex("Card( )+[0-9]+:( )+"))
            val line = rawLine[1].split(Regex("( )+\\|( )+"))
            println(line.joinToString(" | "))
            for (n in getNumbers(line, 0)) {
                winNumbers.add(n)
            }
            for (n in getNumbers(line, 1)) {
                numbers.add(n)
            }
        }

        private fun getNumbers(line: List<String>, index: Int): List<Int> {
            return line[index].split(Regex("( )+"))
                .map { n -> n.toInt() }
        }

        fun countWinNumbers() {
            for (n in numbers) {
                if (winNumbers.contains(n)) winNumbersCount++
            }
        }

        fun getPoints(): Int {
            var points = 0
            if (winNumbersCount > 0) {
                points = 1
                for (i in 1 until winNumbersCount) points *= 2
            }
            println(points)
            return points
        }
    }

    fun count(): Long {
        val path = MAIN_INPUT_PATH.format("Day4")
        return count(path)
    }

    // TODO
    fun count(path: String): Long {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        var points = 0L
        while (scanner.hasNext()) {
            val card = Card()
            card.setNumbers(scanner)
            card.countWinNumbers()
            points += card.getPoints()
        }
        return points
    }
}