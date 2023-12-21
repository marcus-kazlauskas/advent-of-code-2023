import java.io.File
import java.util.*
import kotlin.io.path.Path

object Day12 {
    const val VALUE = 1097
    /*
        это самая сложная задачав aoc по мнению людей из реддита
        я убил на эту хуйню дней десять
    */
    private const val OPERATIONAL = '.'
    private const val DAMAGED = '#'
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

        protected fun groupsOfDamaged(line: String): List<Int> {
            return line.split(',')
                .map { x -> x.toInt() }
                .toList()
        }

        open fun check() {
            check(0, OPERATIONAL, 0)
        }

        private fun check(pos: Int, prevSymbol: Char, posInGroups: Int) {
            if (isEnoughSpace(pos, posInGroups)) {
                if (row[pos] == UNKNOWN) { // текущий символ -- symbol
                    if (prevSymbol == DAMAGED) {
                        check(pos + 1, OPERATIONAL, posInGroups)
                    } else {
                        if (isEnoughGroups(posInGroups)) {
                            val delta = groupsOfDamaged[posInGroups]
                            check(pos + delta, DAMAGED, posInGroups + 1)
                        }
                        check(pos + 1, OPERATIONAL, posInGroups)
                    }
                } else { // symbol == DAMAGED
                    if (prevSymbol != DAMAGED) {
                        if (isEnoughGroups(posInGroups)) {
                            val delta = groupsOfDamaged[posInGroups]
                            check(pos + delta, DAMAGED, posInGroups + 1)
                        }
                    }
                } /*
                    symbol не может быть OPERATIONAL,
                    потому что мы делим строки на непересекающиеся группы,
                    а затем считаем произведение перестановок в каждой такой группе
                */
            } else if (!isEnoughGroups(posInGroups)) {
                validCount++
            }
        }

        private fun isEnoughSpace(pos: Int, posInGroups: Int): Boolean {
            return if (pos <= posMax()) {
                var spaceMin = -1
                val space = posMax() - pos + 1
                for (i in posInGroups..posInGroupsMax()) {
                    spaceMin += groupsOfDamaged[i] + 1
                }
                space >= spaceMin
            } else false
        }

        private fun posMax(): Int {
            return row.length - 1
        }

        private fun posInGroupsMax(): Int {
            return groupsOfDamaged.size - 1
        }

        private fun isEnoughGroups(posInGroups: Int): Boolean {
            return posInGroups <= posInGroupsMax()
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
            val counter = SuperCounter()
            counter.init(line)
            counter.check()
            val validCount = counter.validCount
            println("$validCount\n")
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
            return line.split(Regex("[$OPERATIONAL]+"))
                .filter { g -> g != "" }
                .toList()
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

        override fun check() {
            return checkRow(0, 0, LinkedList<Long>())
        }

        private fun checkRow(rowPos: Int, damagedPos: Int, validCountList: LinkedList<Long>) {
            if (notAllGroupsOfRowsChecked(rowPos)) {
                val currentRow = groupsOfRows[rowPos]
                val currentCounter = Counter()
                if (canBeWithoutDamaged(currentRow)) {
                    validCountList.add(1L)
                    checkRow(rowPos + 1, damagedPos, validCountList)
                    validCountList.pollLast()
                }
                for (i in damagedPos..currentDamagedPosMax(rowPos, damagedPos)) {
                    currentCounter.init(currentRow, currentDamaged(damagedPos, i))
                    currentCounter.check()
                    validCountList.add(currentCounter.validCount)
                    checkRow(rowPos + 1, i + 1, validCountList)
                    validCountList.pollLast()
                }
            } else if (allGroupsOfDamagedChecked(damagedPos)) {
                validCount += multiply(validCountList)
            }
        }

        private fun notAllGroupsOfRowsChecked(rowPos: Int): Boolean {
            return rowPos <= rowPosMax()
        }

        private fun canBeWithoutDamaged(currentRow: String): Boolean {
            return !currentRow.contains(DAMAGED)
        }

        private fun currentDamagedPosMax(rowPos: Int, damagedPos: Int): Int {
            var i = damagedPos
            val maxDamagedPos = groupsOfDamaged.size - 1
            var length = -1
            val maxLength = groupsOfRows[rowPos].length
            while (length <= maxLength) {
                if (i <= maxDamagedPos) length += groupsOfDamaged[i++] + 1
                else return maxDamagedPos
            }
            return i - 2
        }

        private fun currentDamaged(damagedPos: Int, i: Int): LinkedList<Int> {
            val currentDamaged = LinkedList<Int>()
            for (j in damagedPos..i) {
                val damaged = groupsOfDamaged[j]
                currentDamaged.add(damaged)
            }
            return currentDamaged
        }

        private fun allGroupsOfDamagedChecked(damagedPos: Int): Boolean {
            return damagedPos > damagedPosMax()
        }

        private fun rowPosMax(): Int {
            return groupsOfRows.size - 1
        }

        private fun damagedPosMax(): Int {
            return groupsOfDamaged.size - 1
        }

        private fun multiply(validCountList: LinkedList<Long>): Long {
            return validCountList.fold(1L) {multi, value -> multi * value}
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
            superCounter.check()
            val validCount = superCounter.validCount
            println("$validCount\n")
            sumOfAllPossibleArrangements += validCount
        }
        return sumOfAllPossibleArrangements
    }
    // раньше ????.?????????? 1,1,2,2 считалось больше 10 часов
    // в новой версии пока непонятно
}