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
//        val rowList = CharArray(l) { '?' }.toList()
        private val row = linesPair[0].toCharArray()
        private val groupsOfDamaged = groupsOfDamaged(linesPair[1])
        private val maxCount = maxCount()
        private var validCount = 0

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
//            println(assumption)
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

        fun getValidCount(): Int {
            return validCount
        }
    }

    fun count(): Int {
        val path = MAIN_INPUT_PATH.format("Day12")
        return count(path)
    }

    fun count(path: String): Int {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        var sumOfAllPossibleArrangements = 0
        while (scanner.hasNext()) {
            val linesPair = scanner.nextLine().split(' ')
//            println(linesPair)
            val counter = Counter(linesPair)
            counter.check()
            val validCount = counter.getValidCount()
//            println(validCount)
            sumOfAllPossibleArrangements += validCount
        }
        return sumOfAllPossibleArrangements
    }
}