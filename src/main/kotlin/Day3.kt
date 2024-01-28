import java.io.File
import java.util.*
import kotlin.io.path.Path

object Day3 {
    const val VALUE = 1086
    private const val DEFAULT_SYMBOL = '.'
    private const val WINDOW_WIDTH = 3
    private const val UP_LINE_INDEX = 0
    private const val CURRENT_LINE_INDEX = 1
    private const val DOWN_LINE_INDEX = 2
    private const val DEFAULT_POS = -1

    class SearchWindow() {
        private val window = LinkedList<String>()
        private var pos = DEFAULT_POS
        private val symbol = ArrayList<Char>()
        private val prevSymbol = ArrayList<Char>()
        private val numberInConstruction = LinkedList<Char>()
        private var adjacent = false
        private val stringNumbers = LinkedList<String>()

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
            val length = window[CURRENT_LINE_INDEX].length
            val emptyLine = createEmptyLine(length)
            window.poll()
            window.add(emptyLine)
        }

        private fun createEmptyLine(length: Int): String {
            return CharArray(length) { DEFAULT_SYMBOL }.concatToString()
        }

        fun findPartNumbers() {
            setSymbolDefault()
            for (i in window[CURRENT_LINE_INDEX].indices) {
                nextSymbol()
                checkSymbol()
            }
        }

        private fun setSymbolDefault() {
            pos = DEFAULT_POS
            if (symbol.isEmpty()) {
                repeat(WINDOW_WIDTH) {
                    symbol.add(DEFAULT_SYMBOL)
                }
            } else {
                for (i in 0 until WINDOW_WIDTH) {
                    symbol[i] = DEFAULT_SYMBOL
                }
            }
            if (prevSymbol.isEmpty()) {
                repeat(WINDOW_WIDTH) {
                    prevSymbol.add(DEFAULT_SYMBOL)
                }
            }
        }

        private fun nextSymbol() {
            pos++
            for (i in 0 until WINDOW_WIDTH) {
                prevSymbol[i] = symbol[i]
                symbol[i] = window[i][pos]
            }
        }

        private fun checkSymbol() {
            if (symbol[CURRENT_LINE_INDEX].isDigit()) {
                numberInConstruction.add(symbol[CURRENT_LINE_INDEX])
                if (!prevSymbol[CURRENT_LINE_INDEX].isDigit()) {
                    checkFrontEdge()
                } else {
                    checkIntermediateEdge()
                }
                if (isLastPos()) {
                    checkBackEdge()
                    addNumber()
                }
            } else if (prevSymbol[CURRENT_LINE_INDEX].isDigit()) {
                checkIntermediateEdge()
                checkBackEdge()
                addNumber()
            }
        }

        private fun isLastPos(): Boolean {
            return pos == (window[CURRENT_LINE_INDEX].length - 1)
        }

        private fun isSymbol(symbol: Char): Boolean {
            return !symbol.isDigit() && symbol != DEFAULT_SYMBOL
        }

        private fun checkFrontEdge() {
            adjacent = adjacent ||
                    isSymbol(prevSymbol[UP_LINE_INDEX]) ||
                    isSymbol(prevSymbol[CURRENT_LINE_INDEX]) ||
                    isSymbol(prevSymbol[DOWN_LINE_INDEX])
        }

        private fun checkIntermediateEdge() {
            adjacent = adjacent ||
                    isSymbol(prevSymbol[UP_LINE_INDEX]) ||
                    isSymbol(prevSymbol[DOWN_LINE_INDEX])
        }

        private fun checkBackEdge() {
            adjacent = adjacent ||
                    isSymbol(symbol[UP_LINE_INDEX]) ||
                    isSymbol(symbol[CURRENT_LINE_INDEX]) ||
                    isSymbol(symbol[DOWN_LINE_INDEX])
        }

        private fun addNumber() {
            if (adjacent) {
                val stringNumber = numberInConstruction.joinToString("")
                println(stringNumber)
                stringNumbers.add(stringNumber)
                adjacent = false
            }
            numberInConstruction.clear()
        }

        fun sumNumbers(): Long {
            return stringNumbers.fold(0L) { sum, value -> sum + value.toLong() }
        }
    }

    fun count(): Long {
        val path = MAIN_INPUT_PATH.format("Day3")
        return count(path)
    }

    fun count(path: String): Long {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        val searchWindow = SearchWindow()
        searchWindow.firstLine(scanner)
        while (scanner.hasNext()) {
            searchWindow.nextLine(scanner)
            searchWindow.findPartNumbers()
        }
        searchWindow.lastLine()
        searchWindow.findPartNumbers()
        return searchWindow.sumNumbers()
    }


}