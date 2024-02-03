import java.io.File
import java.util.*
import kotlin.collections.HashMap
import kotlin.io.path.Path

object Day8 {
    const val VALUE = 940
    private const val LEFT_CMD = 'L'
    private const val START = "AAA"
    private const val START_SUF = 'A'
    private const val FINISH = "ZZZ"
    private const val FINISH_SUF = 'Z'

    private class Navigator(val instructions: String) {
        private val nodes = HashMap<String, Pair<String, String>>()

        fun addNode(key: String, node: Pair<String, String>) {
            nodes[key] = node
        }

        private fun next(key: String, command: Char): String {
            val node = nodes[key]
            return if (command == LEFT_CMD) node?.first ?: FINISH
            else node?.second ?: FINISH
        }

        fun countStepsToDest(): Long {
            var key = START
            var count = 0L
            while (key != FINISH) {
                for (i in instructions) {
                    if (key == FINISH) break
                    key = next(key, i)
                    count++
                }
            }
            return count
        }

        fun countStepsToDestV2(): Long {
            val startNodes = HashMap<String, Pair<String, String>>()
            for (e in nodes.entries) {
                if (e.key.endsWith(START_SUF)) {
                    startNodes[e.key] = e.value
                }
            }
            val periods = LinkedList<Long>()
            for (k in startNodes.keys) {
                var key = k
                var count = 0L
                while (!key.endsWith(FINISH_SUF)) {
                    for (i in instructions) {
                        if (key == FINISH) break
                        key = next(key, i)
                        count++
                    }
                }
                periods.add(count)
            }
            periods.sort()
            var lcm = periods.last
            for (p in periods) {
                if (lcm % p != 0L) {
                    for (i in 2L..p) {
                        if (lcm % i != 0L && (lcm * i) % p == 0L) {
                            lcm *= i
                            break
                        }
                    }
                }
            }
            return lcm
        }
    }

    fun count(): Long {
        val path = MAIN_INPUT_PATH.format("Day8")
        return count(path)
    }

    fun count(path: String): Long {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        val instructions = scanner.nextLine()
        val navigator = Navigator(instructions)
        scanner.nextLine()
        while (scanner.hasNext()) {
            val line = scanner.nextLine().split(Regex("(( )+=( )+\\()|\\)"))
            val key = line[0]
            val pair = line[1].split(Regex(",( )+"))
            navigator.addNode(key, Pair(pair[0], pair[1]))
            println("$key = $pair")
        }
        return navigator.countStepsToDest()
    }

    fun countV2(): Long {
        val path = MAIN_INPUT_PATH.format("Day8")
        return countV2(path)
    }

    fun countV2(path: String): Long {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        val instructions = scanner.nextLine()
        val navigator = Navigator(instructions)
        scanner.nextLine()
        while (scanner.hasNext()) {
            val line = scanner.nextLine().split(Regex("(( )+=( )+\\()|\\)"))
            val key = line[0]
            val pair = line[1].split(Regex(",( )+"))
            navigator.addNode(key, Pair(pair[0], pair[1]))
            println("$key = $pair")
        }
        return navigator.countStepsToDestV2()
    }
}