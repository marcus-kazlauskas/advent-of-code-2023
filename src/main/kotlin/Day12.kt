import java.io.File
import java.util.*
import kotlin.io.path.Path

object Day12 {
    const val VALUE = 1097
    const val OPERATIONAL = '.'
    const val DAMAGED = '#'
    const val UNKNOWN = '?'

    class Counter(l: Int) {
        val rowList = CharArray(l) { '?' }.toList()
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
            val row = linesPair[0].toCharArray()
            val groupsOfDamaged = groupsOfDamaged(linesPair[1])
        }
        return 0
    }

    private fun groupsOfDamaged(line: String): List<Int> {
        return line.split(',').stream()
            .map { x -> x.toInt() }
            .toList()
    }
}