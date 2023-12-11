import java.io.File
import java.util.*
import kotlin.io.path.Path

object Day3 {
    const val VALUE = 1086
    private const val DEFAULT_SYMBOL = '.'
    private const val UP_LINE_INDEX = 0
    private const val CURRENT_LINE_INDEX = 1
    private const val DOWN_LINE_INDEX = 2

    class SearchWindow() {
        private val window = LinkedList<String>()
        private var stringNumbersInLine = LinkedList<String>()
        private val partNumbers = LinkedList<Int>()

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

        fun lastLine() {
            val length = window[1].length
            val emptyLine = createEmptyLine(length)
            window.poll()
            window.add(emptyLine)
        }

        private fun createEmptyLine(length: Int): String {
            return CharArray(length){DEFAULT_SYMBOL}.concatToString()
        }

        fun findPartNumbers() {
            println(window[UP_LINE_INDEX])
            println(window[CURRENT_LINE_INDEX])
            println(window[DOWN_LINE_INDEX])
            val stringNumbers = window[CURRENT_LINE_INDEX].split(Regex("[^0-9]+"))
            stringNumbersInLine = LinkedList<String>()
            for (stringNumber in stringNumbers) {
                val count = count(stringNumber)
                stringNumbersInLine.add(stringNumber)
                print(stringNumber)
                if (isPartNumber(stringNumber, count) && stringNumber != "") {
                    println(" yes")
                    val number = stringNumber.toInt()
                    partNumbers.add(number)
                } else println(" no")
            }
        }

        private fun count(stringNumber: String): Int {
            var i = 0
            for (numberInLine in stringNumbersInLine) {
                if (numberInLine == stringNumber) i++
            }
            return i
        }

        private fun isPartNumber(stringNumber: String, count: Int): Boolean {
            val regex = Regex("[^0-9]?$stringNumber[^0-9]?")
            var match = regex.find(window[CURRENT_LINE_INDEX])!!
            var i = 0
            while (i < count) {
                match = match.next()!!
                i++
            }
            val range = match.range
            if (match.value == ".192.") {
                println()
                println("stringNumbersInLine = $stringNumbersInLine")
                println("count = $count")
                println("match = ${match.value}")
                println(range)
            }
            var check = false
            for (i in range) {
                if (charIsSymbol(UP_LINE_INDEX, i)
                    || charIsSymbol(CURRENT_LINE_INDEX, i)
                    || charIsSymbol(DOWN_LINE_INDEX, i)) {
                    check = true
                    break
                }
            }
            return check
        }

        private fun charIsSymbol(lineInWindow: Int, i: Int): Boolean {
            return window[lineInWindow][i] != DEFAULT_SYMBOL
                    && !window[lineInWindow][i].isDigit()
        }

        fun sum(): Int {
            var sum = 0
            for (number in partNumbers) sum += number
            return sum
        }
    }

    fun count(): Int {
        val path = MAIN_INPUT_PATH.format("Day3")
        return count(path)
    }

    fun count(path: String): Int {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        val searchWindow = SearchWindow()
        searchWindow.firstLine(scanner)
        while (scanner.hasNext()) {
            searchWindow.nextLine(scanner)
            searchWindow.findPartNumbers()
            println()
        }
        searchWindow.lastLine()
        searchWindow.findPartNumbers()
        println()
        return searchWindow.sum()
    }


}