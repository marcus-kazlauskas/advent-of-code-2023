import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.io.path.Path

object Day14 {
    const val VALUE = 946

    private class Platform {
        private val panel = ArrayList<ArrayList<Char>>()

        private companion object {
            const val ROUNDED = 'O'
            const val CUBE = '#'
            const val EMPTY = '.'
        }

        fun set(scanner: Scanner) {
            while (scanner.hasNext()) {
                val line = scanner.nextLine().toCharArray().toMutableList()
                panel.add(line as ArrayList<Char>)
            }
            print()
        }

        fun print() {
            for (line in panel) {
                println(line.joinToString(""))
            }
            println()
        }

        fun tiltNorth() {
            for (j in 0 until panel[0].size) {
                tiltColumnNorth(j)
            }
            print()
        }

        private fun tiltColumnNorth(j: Int) {
            var countOfRounded = 0
            for (i in (0 until panel.size).reversed()) {
                when (panel[i][j]) {
                    ROUNDED     -> {
                        panel[i][j] = EMPTY
                        countOfRounded++
                        if (i == 0) {
                            setRounded(0, countOfRounded - 1, j)
                        }
                    }
                    CUBE        -> {
                        setRounded(i + 1, i + countOfRounded, j)
                        countOfRounded = 0
                    }
                    EMPTY       -> {
                        if (i == 0) {
                            setRounded(0, countOfRounded - 1, j)
                        }
                    }
                }
            }
        }

        private fun setRounded(iStart: Int, iEnd: Int, j: Int) {
            for (k in iStart..iEnd) {
                panel[k][j] = ROUNDED
            }
        }

        fun load(): Int {
            var load = 0
            for (i in (0 until panel.size)) {
                val distFromSouth = panel.size - i
                var countInLine = 0
                for (c in panel[i]) {
                    if (c == ROUNDED) countInLine++
                }
                load += countInLine * distFromSouth
            }
            return load
        }
    }

    fun count(): Int {
        val path = MAIN_INPUT_PATH.format("Day14")
        return count(path)
    }

    fun count(path: String): Int {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        val platform = Platform()
        platform.set(scanner)
        platform.tiltNorth()
        return platform.load()
    }
}