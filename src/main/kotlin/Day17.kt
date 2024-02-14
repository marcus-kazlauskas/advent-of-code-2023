import java.io.File
import java.util.*
import kotlin.io.path.Path

object Day17 {
    const val VALUE = 1090

    private class Map {
        val map = LinkedList<List<Int>>()

        fun set (scanner: Scanner) {
            while (scanner.hasNext()) {
                val line = scanner.nextLine()
                    .toCharArray()
                    .map { c -> c.code }
                map.add(line)
            }
        }
    }

    fun count(): Int {
        val path = MAIN_INPUT_PATH.format("Day17")
        return count(path)
    }

    fun count(path: String): Int {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        val map = Map()
        map.set(scanner)
        return 0
    }
}