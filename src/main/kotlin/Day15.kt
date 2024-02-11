import java.io.File
import java.util.*
import kotlin.io.path.Path

object Day15 {
    const val VALUE = 1105

    private class Hash {
        private lateinit var initial: List<String>
        private val result = LinkedList<Int>()

        fun set(scanner: Scanner) {
            initial = scanner.nextLine().split(',')
        }

        fun countHash(): Int {
            for (l in initial) {
                var currentValue = 0
                for (c in l) {
                    println(currentValue)
                    println("code = ${c.code}")
                    currentValue = (currentValue + c.code) * 17 % 256
                }
                println("$currentValue\n")
                result.add(currentValue)
            }
            return result.fold(0) { sum, value -> sum + value }
        }
    }

    fun count(): Int {
        val path = MAIN_INPUT_PATH.format("Day15")
        return count(path)
    }

    fun count(path: String): Int {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        val hash = Hash()
        hash.set(scanner)
        return hash.countHash()
    }
}