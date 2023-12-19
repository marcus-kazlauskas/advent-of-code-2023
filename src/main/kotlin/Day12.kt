import java.io.File
import java.util.*
import kotlin.io.path.Path

object Day12 {
    const val VALUE = 1097
    private const val OPERATIONAL = '.'
    private const val DAMAGED = '#'
    val numberToSymbol = mapOf(
        0   to DAMAGED,
        1   to OPERATIONAL
    )
    const val UNKNOWN = '?'

    open class Counter {
        protected var row = ""
        protected var groupsOfDamaged = listOf(0)

        var validCount = 0L
            protected set

        open fun init(line: String) {
            val linesPair = line.split(' ')
            val row = linesPair[0]
            val groupsOfDamaged = groupsOfDamaged(linesPair[1])
            init(row, groupsOfDamaged)
        }

        fun init(row: String, groupsOfDamaged: List<Int>) {
            this.row = row
            this.groupsOfDamaged = groupsOfDamaged
            this.validCount = 0L
        }

        private fun maxCount(): Int {
            var count = 0
            for (c in row) {
                if (c == UNKNOWN) count++
            }
            return pow(count) - 1
        }

        private fun pow(n: Int): Int {
            val x = numberToSymbol.size
            var power = 1
            for (i in 1..n) {
                power *= x
            }
            return power
        }

        protected fun groupsOfDamaged(line: String): List<Int> {
            return line.split(',').stream()
                .map { x -> x.toInt() }
                .toList()
        }

        fun check() {
            for (i in 0..maxCount()) {
                checkAssumption(i)
            }
        }

        private fun checkAssumption(i: Int) {
            val assumption = createAssumption(i)
            val groups = createGroups(assumption)
            if (compareGroups(groups)) validCount++
        }

        private fun createAssumption(i: Int): List<Char> {
            var currentCount = i
            val assumption = LinkedList<Char>()
            for (c in row.reversed()) {
                if (c == UNKNOWN) {
                    val radix = numberToSymbol.size
                    val number = currentCount % radix
                    val symbol = numberToSymbol[number]
                    assumption.addFirst(symbol)
                    currentCount /= radix
                } else {
                    assumption.addFirst(c)
                }
            }
            return assumption
        }

        private fun createGroups(assumption: List<Char>): List<Int> {
            val groups = LinkedList<Int>()
            var groupCount = 0
            var prevSymbol = OPERATIONAL
            for (symbol in assumption) {
                if (symbol == DAMAGED) {
                    groupCount++
                } else if (prevSymbol == DAMAGED && symbol == OPERATIONAL) {
                    groups.add(groupCount)
                    groupCount = 0
                }
                prevSymbol = symbol
            }
            if (prevSymbol == DAMAGED) {
                groups.add(groupCount)
            }
            return groups
        }

        private fun compareGroups(groups: List<Int>): Boolean {
            if (groupsOfDamaged.size != groups.size) {
                return false
            }
            for (i in groupsOfDamaged.indices) {
                val groupOfDamaged = groupsOfDamaged[i]
                val group = groups[i]
                if (groupOfDamaged != group) {
                    return false
                }
            }
            return true
        }
    }


    fun count(): Long {
        val path = MAIN_INPUT_PATH.format("Day12")
        return count(path)
    }

    fun count(path: String): Long {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        var sumOfAllPossibleArrangements = 0L
        while (scanner.hasNext()) {
            val line = scanner.nextLine()
            println(line)
            val counter = Counter()
            counter.init(line)
            counter.check()
            val validCount = counter.validCount
            println(validCount)
            println()
            sumOfAllPossibleArrangements += validCount
        }
        return sumOfAllPossibleArrangements
    }

    class SuperCounter() : Counter() {
        private var groupsOfRows = listOf("")

        override fun init(line: String) {
            val linesPair = line.split(' ')
            val row = linesPair[0]
            val groupsOfRows = groupsOfRows(row)
            val groupsOfDamaged = groupsOfDamaged(linesPair[1])
            init(row, groupsOfRows, groupsOfDamaged)
        }

        private fun init(row: String, groupsOfRows: List<String>, groupsOfDamaged: List<Int>) {
            this.row = row
            this.groupsOfRows = groupsOfRows
            this.groupsOfDamaged = groupsOfDamaged
            this.validCount = 0L
        }

        private fun groupsOfRows(line: String): List<String> {
            val groups = line.split(Regex("[$OPERATIONAL]+"))
            println(line)
            println(groups)
            return groups
        }

        fun unfold(n: Int) {
            val newRow = LinkedList<String>()
            val newGroupsOfDamaged = LinkedList<Int>()
            for (i in 0 until n) {
                newRow.add(row)
                newGroupsOfDamaged.addAll(groupsOfDamaged)
            }
            row = newRow.joinToString("$UNKNOWN")
            groupsOfRows = groupsOfRows(row)
            groupsOfDamaged = newGroupsOfDamaged
        }

        fun checkV2() {
            println("checkV2()")
            val validCountList = LinkedList<Long>()
            return checkRow(0, 0, validCountList)
        }

        private fun checkRow(rowPos: Int, damagedPos: Int, validCountList: LinkedList<Long>) {
            val rowPosMax = groupsOfRows.size - 1
            if (rowPos <= rowPosMax) {
                val damagedPosMax = damagedPosMax(rowPos, damagedPos)
                val counter = Counter()
                for (i in damagedPos..damagedPosMax) {
                    println("checkRow($rowPos, $damagedPos, $validCountList) numberOfDamaged = ${i - damagedPos + 1}, rowPosMax=$rowPosMax, damagedPosMax=$damagedPosMax")
                    val currentRow = groupsOfRows[rowPos]
                    val currentDamaged = LinkedList<Int>()
                    for (j in damagedPos..i) {
                        val damaged = groupsOfDamaged[j]
                        currentDamaged.add(damaged)
                    }
                    counter.init(currentRow, currentDamaged)
                    counter.check()
                    validCountList.add(counter.validCount)
                    checkRow(rowPos + 1, i + 1, validCountList)
                    for (j in damagedPos..i) {
                        validCountList.pollLast()
                    }
                }

            } else {
                println("checkRow($rowPos, $damagedPos, $validCountList) stop")

                validCount += multiply(validCountList)
            }
        }

        private fun damagedPosMax(rowPos: Int, damagedPos: Int): Int {
            var length = -1
            val maxLength = groupsOfRows[rowPos].length
            var i = damagedPos
            val maxDamagedPos = groupsOfDamaged.size - 1
            while (length <= maxLength) {
                if (i <= maxDamagedPos)
                    length += groupsOfDamaged[i++] + 1
                else
                    return maxDamagedPos
            }
            return i - 2
        }

        private fun multiply(validCountList: LinkedList<Long>): Long {
            var multi = 1L
            for (countInGroup in validCountList) {
                multi *= countInGroup
            }
            println("+= $multi")
            return multi
        }
    }

    fun countV2(): Long {
        val path = MAIN_INPUT_PATH.format("Day12")
        return countV2(path)
    }

    fun countV2(path: String): Long {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        var sumOfAllPossibleArrangements = 0L
        while (scanner.hasNext()) {
            val line = scanner.nextLine()
            println(line)
            val superCounter = SuperCounter()
            superCounter.init(line)
            superCounter.unfold(5)
            superCounter.checkV2()
            val validCount = superCounter.validCount
            println(validCount)
            println()
            sumOfAllPossibleArrangements += validCount
        }
        return sumOfAllPossibleArrangements
    }
    // раньше ????.?????????? 1,1,2,2 считалось больше 10 часов
    // в новой версии пока непонятно
}