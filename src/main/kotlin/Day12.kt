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

    class Counter(linesPair: List<String>) {
        private var row = linesPair[0]
        private var groupsOfDamaged = groupsOfDamaged(linesPair[1])
        private val maxCount = maxCount()
        private var validCount = 0L

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

        private fun groupsOfDamaged(line: String): List<Int> {
            return line.split(',').stream()
                .map { x -> x.toInt() }
                .toList()
        }

        fun check() {
            for (i in 0..maxCount) {
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

        fun getValidCount(): Long {
            return validCount
        }

        fun unfold(n: Int) {
            val newRow = LinkedList<String>()
            val newGroupsOfDamaged = LinkedList<Int>()
            for (i in 0 until n) {
                newRow.add(row)
                newGroupsOfDamaged.addAll(groupsOfDamaged)
            }
            row = newRow.joinToString("$UNKNOWN")
            groupsOfDamaged = newGroupsOfDamaged
        }

        // проблема с #?????.???????#.??? 1,2,1,1,2,2 долго считается
        fun checkV2() {
            checkV2(0, OPERATIONAL, 0, 0, minSpace())
        }

        private fun minSpace(): Int {
            var minSpace = -1
            for (g in groupsOfDamaged) {
                minSpace += g + 1
            }
            return minSpace
        }

        private fun checkV2(pos: Int, prevSymbol: Char, posInGroups: Int, prevCountInGroup: Int, minSpace: Int) {
            val nextPos = pos + 1
            val nextPosInGroups = posInGroups + 1
            val nextMinSpace = minSpace - 1
            val maxPos = row.length - 1
            if ((maxPos - pos + 1) >= minSpace) {
                if (pos <= maxPos) {
                    when (row[pos]) { // when (symbol)
                        UNKNOWN -> {
                            if (prevSymbol == DAMAGED) {
                                if (isEndGroupOk(posInGroups, prevCountInGroup)) {
                                    checkV2(nextPos, OPERATIONAL, nextPosInGroups, 0, nextMinSpace)
                                } else {
                                    checkV2(nextPos, DAMAGED, posInGroups, prevCountInGroup + 1, nextMinSpace)
                                }
                            } else {
                                if (isStartGroupOk(pos, posInGroups)) {
                                    val countInGroup = groupsOfDamaged[posInGroups] // TODO
                                    checkV2(pos + countInGroup, DAMAGED, posInGroups, countInGroup, minSpace - countInGroup)
                                }
                                checkV2(nextPos, OPERATIONAL, posInGroups, 0, minSpace)
                            }
                        }

                        DAMAGED -> {
                            if (prevSymbol == DAMAGED) {
                                if (!isEndGroupOk(posInGroups, prevCountInGroup)) {
                                    checkV2(nextPos, DAMAGED, posInGroups, prevCountInGroup + 1, nextMinSpace)
                                }
                            } else {
                                checkV2(nextPos, DAMAGED, posInGroups, 1, nextMinSpace)
                            }
                        }

                        OPERATIONAL -> {
                            if (prevSymbol == DAMAGED) {
                                if (isEndGroupOk(posInGroups, prevCountInGroup)) {
                                    checkV2(nextPos, OPERATIONAL, nextPosInGroups, 0, nextMinSpace)
                                }
                            } else {
                                checkV2(nextPos, OPERATIONAL, posInGroups, 0, minSpace)
                            }
                        }
                    }
                } else {
                    if (prevSymbol == DAMAGED) {
                        if (isTerminalDamagedOk(posInGroups, prevCountInGroup)) {
                            validCount++
                        }
                    } else {
                        if (isTerminalOperationalOk(posInGroups)) {
                            validCount++
                        }
                    }
                }
            }
        }

        private fun isEndGroupOk(posInGroups: Int, prevCountInGroup: Int): Boolean {
            return if (posInGroups < groupsOfDamaged.size) {
                prevCountInGroup == groupsOfDamaged[posInGroups] // prevCountInGroup == countInGroup
            } else {
                false
            }
        }

        private fun isStartGroupOk(pos: Int, posInGroups: Int): Boolean {
            if (posInGroups < groupsOfDamaged.size) {
                var delta = 0
                var symbol = UNKNOWN
                for (i in pos until row.length) {
                    symbol = row[i]
                    if (symbol != UNKNOWN) {
                        delta = i - pos
                        break
                    }
                }
                return if (symbol == OPERATIONAL) {
                    delta >= groupsOfDamaged[posInGroups]
                } else {
                    true
                }
            } else {
                return false
            }
        }

        private fun isTerminalDamagedOk(posInGroups: Int, prevCountInGroup: Int): Boolean {
            return if (posInGroups == groupsOfDamaged.size - 1) {
                prevCountInGroup == groupsOfDamaged[posInGroups]
            } else {
                false
            }
        }

        private fun isTerminalOperationalOk(posInGroups: Int): Boolean {
            return posInGroups == groupsOfDamaged.size
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
            val linesPair = scanner.nextLine().split(' ')
            println(linesPair)
            val counter = Counter(linesPair)
            counter.check()
            val validCount = counter.getValidCount()
            println(validCount)
            println()
            sumOfAllPossibleArrangements += validCount
        }
        return sumOfAllPossibleArrangements
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
            val linesPair = scanner.nextLine().split(' ')
            println(linesPair)
            val counter = Counter(linesPair)
            counter.unfold(5)
            counter.checkV2()
            val validCount = counter.getValidCount()
            println(validCount)
            println()
            sumOfAllPossibleArrangements += validCount
        }
        return sumOfAllPossibleArrangements
    }
}