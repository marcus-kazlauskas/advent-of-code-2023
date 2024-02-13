import java.io.File
import java.util.*
import kotlin.collections.HashSet
import kotlin.io.path.Path

object Day16 {
    const val VALUE = 1093

    private class Tile(
        /*
        direction
        . . ^ . .
        . . 0 . .
        < 3 + 1 >
        . . 2 . .
        . . v . .
         */
        var direction: HashSet<Int>,
        var tile: Char
    )

    private class Contraption {
        private companion object {
            const val EMPTY = '.'
            const val UP_MIRROR = '/'
            const val DOWN_MIRROR = '\\'
            const val HORIZONTAL_SPLITTER = '|'
            const val VERTICAL_SPLITTER = '-'
            const val ENERGIZED = '#'
        }

        private val layout = LinkedList<LinkedList<Tile>>()

        fun set(scanner: Scanner) {
            while (scanner.hasNext()) {
                val l = scanner.nextLine().toCharArray()
                val line = LinkedList<Tile>()
                for (c in l) {
                    val tile = Tile(HashSet(), c)
                    line.add(tile)
                }
                layout.add(line)
            }
        }

        fun through() {
            through(0, 0, 1)
        }

        fun through(i: Int, j: Int, direction: Int) {  // это ужасно...
            when (direction) {
                0 -> {
                    when (layout[i][j].tile) {
                        UP_MIRROR -> {
                            if (isNotOneMoreBeam(i, j, direction)) {
                                addBeam(i, j, direction)
                                if (isNotLastColumn(j)) {
                                    /*
                                . . . .
                                . / > .
                                . ^ . .
                                . . . .
                                 */
                                    through(i, j + 1, 1)
                                }
                            }
                        }
                        DOWN_MIRROR -> {
                            if (isNotOneMoreBeam(i, j, direction)) {
                                addBeam(i, j, direction)
                                if (isNotFirstColumn(j)) {
                                    /*
                                . . . .
                                . < \ .
                                . . ^ .
                                . . . .
                                 */
                                    through(i, j - 1, 3)
                                }
                            }
                        }
                        VERTICAL_SPLITTER -> {
                            if (isNotOneMoreBeam(i, j, direction)) {
                                addBeam(i, j, direction)
                                if (isNotFirstColumn(j)) {
                                    /*
                                . . . .
                                . < - .
                                . . ^ .
                                . . . .
                                 */
                                    through(i, j - 1, 3)
                                }
                                if (isNotLastColumn(j)) {
                                    /*
                                . . . .
                                . - > .
                                . ^ . .
                                . . . .
                                 */
                                    through(i, j + 1, 1)
                                }
                            }
                        }
                        EMPTY, HORIZONTAL_SPLITTER -> {
                            if (isNotOneMoreBeam(i, j, direction)) {
                                addBeam(i, j, direction)
                                if (isNotFirstRow(i)) {
                                    through(i - 1, j, 0)
                                }
                            }
                        }
                    }
                }
                1 -> {
                    when (layout[i][j].tile) {
                        UP_MIRROR -> {
                            if (isNotOneMoreBeam(i, j, direction)) {
                                addBeam(i, j, direction)
                                if (isNotFirstRow(i)) {
                                    /*
                                . . . .
                                . . ^ .
                                . > / .
                                . . . .
                                 */
                                    through(i - 1, j, 0)
                                }
                            }
                        }
                        DOWN_MIRROR -> {
                            if (isNotOneMoreBeam(i, j, direction)) {
                                addBeam(i, j, direction)
                                if (isNotLastRow(i)) {
                                    /*
                                . . . .
                                . > \ .
                                . . v .
                                . . . .
                                 */
                                    through(i + 1, j, 2)
                                }
                            }
                        }
                        HORIZONTAL_SPLITTER -> {
                            if (isNotOneMoreBeam(i, j, direction)) {
                                addBeam(i, j, direction)
                                if (isNotFirstRow(i)) {
                                    /*
                                . . . .
                                . . ^ .
                                . > | .
                                . . . .
                                 */
                                    through(i - 1, j, 0)
                                }
                                if (isNotLastRow(i)) {
                                    /*
                                . . . .
                                . > | .
                                . . v .
                                . . . .
                                 */
                                    through(i + 1, j, 2)
                                }
                            }
                        }
                        EMPTY, VERTICAL_SPLITTER -> {
                            if (isNotOneMoreBeam(i, j, direction)) {
                                addBeam(i, j, direction)
                                if (isNotLastColumn(j)) {
                                    through(i, j + 1, 1)
                                }
                            }
                        }
                    }
                }
                2 -> {
                    when (layout[i][j].tile) {
                        UP_MIRROR -> {
                            if (isNotOneMoreBeam(i, j, direction)) {
                                addBeam(i, j, direction)
                                if (isNotFirstColumn(j)) {
                                    /*
                                . . . .
                                . . v .
                                . < / .
                                . . . .
                                 */
                                    through(i, j - 1, 3)
                                }
                            }
                        }
                        DOWN_MIRROR -> {
                            if (isNotOneMoreBeam(i, j, direction)) {
                                addBeam(i, j, direction)
                                if (isNotLastColumn(j)) {
                                    /*
                                . . . .
                                . v . .
                                . \ > .
                                . . . .
                                 */
                                    through(i, j + 1, 1)
                                }
                            }
                        }
                        VERTICAL_SPLITTER -> {
                            if (isNotOneMoreBeam(i, j, direction)) {
                                addBeam(i, j, direction)
                                if (isNotFirstColumn(j)) {
                                    /*
                                . . . .
                                . . v .
                                . < - .
                                . . . .
                                 */
                                    through(i, j - 1, 3)
                                }
                                if (isNotLastColumn(j)) {
                                    /*
                                . . . .
                                . v . .
                                . - > .
                                . . . .
                                 */
                                    through(i, j + 1, 1)
                                }
                            }
                        }
                        EMPTY, HORIZONTAL_SPLITTER -> {
                            if (isNotOneMoreBeam(i, j, direction)) {
                                addBeam(i, j, direction)
                                if (isNotLastRow(i)) {
                                    through(i + 1, j, 2)
                                }
                            }
                        }
                    }
                }
                3 -> {
                    when (layout[i][j].tile) {
                        UP_MIRROR -> {
                            if (isNotOneMoreBeam(i, j, direction)) {
                                addBeam(i, j, direction)
                                if (isNotLastRow(i)) {
                                    /*
                                . . . .
                                . / < .
                                . v . .
                                . . . .
                                 */
                                    through(i + 1, j, 2)
                                }
                            }
                        }
                        DOWN_MIRROR -> {
                            if (isNotOneMoreBeam(i, j, direction)) {
                                addBeam(i, j, direction)
                                if (isNotFirstRow(i)) {
                                    /*
                                . . . .
                                . ^ . .
                                . \ < .
                                . . . .
                                 */
                                    through(i - 1, j, 0)
                                }
                            }
                        }
                        HORIZONTAL_SPLITTER -> {
                            if (isNotOneMoreBeam(i, j, direction)) {
                                addBeam(i, j, direction)
                                if (isNotLastRow(i)) {
                                    /*
                                . . . .
                                . | < .
                                . v . .
                                . . . .
                                 */
                                    through(i + 1, j, 2)
                                }
                                if (isNotFirstRow(i)) {
                                    /*
                                . . . .
                                . ^ . .
                                . | < .
                                . . . .
                                 */
                                    through(i - 1, j, 0)
                                }
                            }
                        }
                        EMPTY, VERTICAL_SPLITTER -> {
                            if (isNotOneMoreBeam(i, j, direction)) {
                                addBeam(i, j, direction)
                                if (isNotFirstColumn(j)) {
                                    through(i, j - 1, 3)
                                }
                            }
                        }
                    }
                }
            }
        }

        private fun isNotFirstRow(i: Int): Boolean {
            return i > 0
        }

        private fun isNotLastRow(i: Int): Boolean {
            return i < lastRow()
        }

        fun lastRow(): Int {
            return layout.size - 1
        }

        private fun isNotFirstColumn(j: Int): Boolean {
            return j > 0
        }

        private fun isNotLastColumn(j: Int): Boolean {
            return j < lastColumn()
        }

        fun lastColumn(): Int {
            return layout[0].size - 1
        }

        private fun isNotOneMoreBeam(i: Int, j: Int, direction: Int): Boolean {
            return !layout[i][j].direction.contains(direction)
        }

        private fun addBeam(i: Int, j: Int, direction: Int) {
            layout[i][j].direction.add(direction)
        }

        fun countEnergized(): Int {
            var count = 0
            for (l in layout) {
                for (tile in l) {
                    if (tile.direction.isNotEmpty()) {
                        count++
                    }
                }
            }
            return count
        }

        fun printEnergized() {
            for (l in layout) {
                for (tile in l) {
                    if (tile.direction.isNotEmpty()) {
                        print(ENERGIZED)
                    } else {
                        print(EMPTY)
                    }
                }
                println()
            }
        }

        @Suppress("unused")
        fun printBean() {
            for (l in layout) {
                for (tile in l) {
                    if (setOf(UP_MIRROR,
                            DOWN_MIRROR,
                            HORIZONTAL_SPLITTER,
                            VERTICAL_SPLITTER).contains(tile.tile)) {
                        print(tile.tile)
                    } else if (tile.direction.isNotEmpty()) {
                        print(ENERGIZED)
                    } else {
                        print(EMPTY)
                    }
                }
                println()
            }
        }

        fun getCopy(): Contraption {
            val contraption = Contraption()
            for (l in layout) {
                val line = LinkedList<Tile>()
                for (t in l) {
                    val direction = HashSet<Int>()
                    for (d in t.direction) {
                        direction.add(d)
                    }
                    val tile = Tile(direction, t.tile)
                    line.add(tile)
                }
                contraption.layout.add(line)
            }
            return contraption
        }
    }

    fun count(): Int {
        val path = MAIN_INPUT_PATH.format("Day16")
        return count(path)
    }

    fun count(path: String): Int {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        val contraption = Contraption()
        contraption.set(scanner)
        contraption.through()
        contraption.printEnergized()
//        println()
//        contraption.printBean()
        return contraption.countEnergized()
    }

    fun countV2(): Int {
        val path = MAIN_INPUT_PATH.format("Day16")
        return countV2(path)
    }

    fun countV2(path: String): Int {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        val contraption = Contraption()
        contraption.set(scanner)
        return maxEnergized(contraption)
        // на 928-м вызове произошло переполнение стека
    }

    private fun maxEnergized(contraption: Contraption): Int {
        var maxEnergized = 0
        var currentEnergized: Int
        var copy = contraption
        for (i in 0..(copy.lastRow())) {
            copy = contraption.getCopy()
            copy.through(i, 0, 1)
            currentEnergized = copy.countEnergized()
            if (currentEnergized > maxEnergized) {
                maxEnergized = currentEnergized
                println("i = $i, left edge; energy = $maxEnergized")
            }
            copy = contraption.getCopy()
            copy.through(i, copy.lastColumn(), 3)
            currentEnergized = copy.countEnergized()
            if (currentEnergized > maxEnergized) {
                maxEnergized = currentEnergized
                println("i = $i, right edge; energy = $maxEnergized")
            }
        }
        for (j in 0..(copy.lastColumn())) {
            copy = contraption.getCopy()
            copy.through(0, j, 2)
            currentEnergized = copy.countEnergized()
            if (currentEnergized > maxEnergized) {
                maxEnergized = currentEnergized
                println("j = $j, up edge; energy = $maxEnergized")
            }
            copy = contraption.getCopy()
            copy.through(copy.lastRow(), j, 0)
            currentEnergized = copy.countEnergized()
            if (currentEnergized > maxEnergized) {
                maxEnergized = currentEnergized
                println("j = $j, down edge; energy = $maxEnergized")
            }
        }
        return maxEnergized
    }
}