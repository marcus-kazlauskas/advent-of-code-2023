import java.io.File
import java.util.*
import kotlin.io.path.Path

object Day3 {
    const val VALUE = 1086
    private const val DEFAULT_SYMBOL = '.'

    class SearchWindow() {
        private val window = LinkedList<String>()

        fun firstLine(scanner: Scanner) {
            val firstLine = scanner.nextLine()
            val length = firstLine.length
            val emptyLine = createEmptyLine(length)
            window.add(emptyLine)
            window.add(emptyLine)
            window.add(firstLine)
        }

        fun nextLine(scanner: Scanner) {
            val nextLine = scanner.nextLine()
            window.poll()
            window.add(nextLine)
        }

        fun lastLine(scanner: Scanner) {
            val length = window[1].length
            val emptyLine = createEmptyLine(length)
            window.poll()
            window.add(emptyLine)
        }

        private fun createEmptyLine(length: Int): String {
            return CharArray(length){DEFAULT_SYMBOL}.concatToString()
        }

        private fun findPartNumbers(): List<Int> {
            return emptyList()
        }

        private fun sum(partNumbers: List<Int>): Int {
            var sum = 0
            for (number in partNumbers) sum += number
            return sum
        }
    }

    fun count(): Int {
        val path = MAIN_INPUT_PATH.format("Day2")
        return count(path)
    }

    // TODO
    fun count(path: String): Int {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        val searchWindow = SearchWindow()
        val partNumbersSum = 0
        searchWindow.firstLine(scanner)
        while (scanner.hasNext()) {
            searchWindow.nextLine(scanner)
        }
        searchWindow.lastLine(scanner)
        println("Unfinished")
        return 0
    }


}