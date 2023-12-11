import java.io.File
import java.util.*
import kotlin.io.path.Path
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

object Day11 {
    const val VALUE = 1088
    const val EMPTY_SPACE = '.'
    const val GALAXY = '#'

    open class Universe {
        protected val universe = LinkedList<LinkedList<Char>>()
        protected val galaxies = LinkedList<Pair<Int, Int>>()

        fun save(scanner: Scanner) {
            while (scanner.hasNext()) {
                val line = scanner.nextLine().toCharArray().toList()
                universe.add(LinkedList(line))
            }
        }

        fun expand() {
            yExpansion()
            xExpansion()
        }

        private fun yExpansion() {
            var y = 0
            while (y < universe.size) {
                var empty = true
                for (space in universe[y]) {
                    if (space == GALAXY) {
                        empty = false
                        break
                    }
                }
                if (empty) {
                    val emptyLine = createEmptyLine(universe[0].size)
                    universe.add(y, LinkedList(emptyLine))
                    y++
                }
                y++
            }
        }

        private fun xExpansion() {
            var x = 0
            while (x < universe[0].size) {
                var empty = true
                for (y in universe.indices) {
                    if (universe[y][x] == GALAXY) {
                        empty = false
                        break
                    }
                }
                if (empty) {
                    for (y in universe.indices) {
                        universe[y].add(x, EMPTY_SPACE)
                    }
                    x++
                }
                x++
            }
        }

        private fun createEmptyLine(length: Int): List<Char> {
            return CharArray(length){ EMPTY_SPACE }.toList()
        }

        fun findPoints() {
            for (y in universe.indices) {
                for (x in universe[y].indices) {
                    if (universe[y][x] == GALAXY) {
                        galaxies.add(Pair(x, y))
                    }
                }
            }
        }

        fun sumOfDistances(): Long {
            var sum = 0L
            for (i in galaxies.indices) {
                var j = i + 1
                while (j < galaxies.size) {
                    sum += distance(galaxies[i], galaxies[j])
                    j++
                }
            }
            return sum
        }

        private fun distance(p0: Pair<Int, Int>, p1: Pair<Int, Int>): Int {
            val x0 = p0.first
            val y0 = p0.second
            val x1 = p1.first
            val y1 = p1.second
            val distance = abs(x0 - x1) + abs(y0 - y1)
            println("from ($x0, $y0) to ($x1, $y1) $distance steps")
            return distance
        }

//        fun print() {
//            for (line in universe) {
//                println(line)
//            }
//        }
    }

    fun count(): Long {
        val path = MAIN_INPUT_PATH.format("Day11")
        return count(path)
    }

    fun count(path: String): Long {
        val input = Path(path)
        val file = File(input.toUri())
        val universe = Universe()
        val scanner = Scanner(file)
        universe.save(scanner)
        universe.expand()
//        universe.print()
        universe.findPoints()
        return universe.sumOfDistances()
    }

    class UniverseV2 : Universe() {
        fun sumOfDistancesV2(multiply: Int): Long {
            var sum = 0L
            for (i in galaxies.indices) {
                var j = i + 1
                while (j < galaxies.size) {
                    sum += distanceV2(galaxies[i], galaxies[j], multiply)
                    j++
                }
            }
            return sum
        }

        private fun distanceV2(p0: Pair<Int, Int>, p1: Pair<Int, Int>, multiply: Int): Int {
            val xMin = min(p0.first, p1.first)
            val xMax = max(p0.first, p1.first)
            val yMin = min(p0.second, p1.second)
            val yMax = max(p0.second, p1.second)
            var distance = 0
            for (x in xMin until xMax) {
                distance += if (emptyColumn(x)) multiply
                else 1
            }
            for (y in yMin until yMax) {
                distance += if (emptyRow(y)) multiply
                else 1
            }
            return distance
        }

        private fun emptyColumn(x: Int): Boolean {
            var empty = true
            for (y in universe.indices) {
                if (universe[y][x] == GALAXY) {
                    empty = false
                    break
                }
            }
            return empty
        }

        private fun emptyRow(y: Int): Boolean {
            var empty = true
            for (x in universe[y]) {
                if (x == GALAXY) {
                    empty = false
                    break
                }
            }
            return empty
        }
    }

    fun countV2(): Long {
        val path = MAIN_INPUT_PATH.format("Day11")
        return countV2(path, 1000000)
    }

    fun countV2(path: String, multiply: Int): Long {
        val input = Path(path)
        val file = File(input.toUri())
        val universe = UniverseV2()
        val scanner = Scanner(file)
        universe.save(scanner)
        universe.findPoints()
        return universe.sumOfDistancesV2(multiply)
    }
}