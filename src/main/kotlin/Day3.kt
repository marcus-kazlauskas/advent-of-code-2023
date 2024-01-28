import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.io.path.Path

object Day3 {
    const val VALUE = 1086
    private const val DEFAULT_SYMBOL = '.'
    private const val ASTERISK_SYMBOL = '*'
    private const val WINDOW_WIDTH = 3
    private const val UP_LINE_INDEX = 0         // left
    private const val CURRENT_LINE_INDEX = 1    // center
    private const val DOWN_LINE_INDEX = 2       // right
    private const val DEFAULT_POS = -1

    abstract class SearchWindow() {
        protected val window = LinkedList<String>()
        protected var pos = DEFAULT_POS

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

        protected fun isLastPos(): Boolean {
            return pos == (window[CURRENT_LINE_INDEX].length - 1)
        }
    }

    open class PartsSearcher(): SearchWindow() {
        private val symbol = ArrayList<Char>()
        private val prevSymbol = ArrayList<Char>()
        private val numberInConstruction = LinkedList<Char>()
        private var adjacent = false
        private val stringNumbers = LinkedList<String>()

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
        val partsSearcher = PartsSearcher()
        partsSearcher.firstLine(scanner)
        while (scanner.hasNext()) {
            partsSearcher.nextLine(scanner)
            partsSearcher.findPartNumbers()
        }
        partsSearcher.lastLine()
        partsSearcher.findPartNumbers()
        return partsSearcher.sumNumbers()
    }

    class GearsSearcher(): SearchWindow() {
        private val neighbourhood = defaultNeighbourhood()
        private var gearCount = 0
        private var symbol = DEFAULT_SYMBOL
        private var prevSymbol = DEFAULT_SYMBOL
        private val charNumber0 = LinkedList<Char>()
        private val charNumber1 = LinkedList<Char>()
        private val ratios = LinkedList<Long>()

        private fun defaultNeighbourhood(): ArrayList<ArrayList<Char>> {
            val area = ArrayList<ArrayList<Char>>()
            for (i in 0 until WINDOW_WIDTH) {
                val list = createEmptyList()
                area.add(list)
            }
            return area
        }

        private fun createEmptyList(): ArrayList<Char> {
            val list = ArrayList<Char>()
            list.addAll(CharArray(WINDOW_WIDTH) { DEFAULT_SYMBOL }.toList())
            return list
        }

        fun findGears() {
            for (i in window[CURRENT_LINE_INDEX].indices) {
                nextSymbol()
                checkSymbol()
            }
        }

        private fun nextSymbol() {
            pos++
            setNeighbourhood()
        }

        private fun setNeighbourhood() {
            for (i in 0 until WINDOW_WIDTH) {
                if (pos == 0) {
                    neighbourhood[i][UP_LINE_INDEX] = DEFAULT_SYMBOL
                } else {
                    neighbourhood[i][UP_LINE_INDEX] = window[i][pos - 1]
                }
                neighbourhood[i][CURRENT_LINE_INDEX] = window[i][pos]
                if (isLastPos()) {
                    neighbourhood[i][DOWN_LINE_INDEX] = DEFAULT_SYMBOL
                } else {
                    neighbourhood[i][DOWN_LINE_INDEX] = window[i][pos + 1]
                }
            }
        }

        private fun checkSymbol() {
            if (isAsterisk()) {
                val upLine = neighbourhood[UP_LINE_INDEX]

            }
        }

        private fun isAsterisk(): Boolean {
            return window[CURRENT_LINE_INDEX][pos] == ASTERISK_SYMBOL
        }

        private fun checkLine(line: ArrayList<Char>) {
            if (!line[CURRENT_LINE_INDEX].isDigit()) {
                if (line[UP_LINE_INDEX].isDigit()) {
                    gearCount += 1
                }
                if (line[DOWN_LINE_INDEX].isDigit()) {
                    gearCount += 1
                }
            } else {
                gearCount += 1
            }
        }

        private fun addRatio() {
            if (isGear()) {
                val ratio = charNumber0.joinToString("").toLong() *
                        charNumber1.joinToString("").toLong()
                println(ratio)
                ratios.add(ratio)
                gearCount = 0
            }
            charNumber0.clear()
            charNumber1.clear()
        }

        private fun isGear(): Boolean {
            return gearCount == 2
        }

        fun sumRatios(): Long {
            return ratios.fold(0L) { sum, value -> sum + value }
        }
    }

    fun countV2(): Long {
        val path = MAIN_INPUT_PATH.format("Day3")
        return countV2(path)
    }

    fun countV2(path: String): Long {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        val gearsSearcher = GearsSearcher()
        gearsSearcher.firstLine(scanner)
        while (scanner.hasNext()) {
            gearsSearcher.nextLine(scanner)
            gearsSearcher.findGears()
        }
        gearsSearcher.lastLine()
        gearsSearcher.findGears()
        return gearsSearcher.sumRatios()
    }
}