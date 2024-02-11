import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.io.path.Path

object Day14 {
    const val VALUE = 946
    private const val MAX_CYCLE = 1000000000

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
            printPanel()
        }

        fun printPanel() {
            for (line in panel) {
                println(line.joinToString(""))
            }
            println()
        }

        fun tiltNorth() {
            for (j in 0 until panel[0].size) {
                tiltColumnNorth(j)
            }
//            printPanel()
        }

        private fun tiltColumnNorth(j: Int) {
            var countOfRounded = 0
            for (i in (0 until panel.size).reversed()) {
                when (panel[i][j]) {
                    ROUNDED     -> {
                        panel[i][j] = EMPTY
                        countOfRounded++
                        if (i == 0) {
                            setRoundedColumn(0, countOfRounded - 1, j)
                        }
                    }
                    CUBE        -> {
                        setRoundedColumn(i + 1, i + countOfRounded, j)
                        countOfRounded = 0
                    }
                    EMPTY       -> {
                        if (i == 0) {
                            setRoundedColumn(0, countOfRounded - 1, j)
                        }
                    }
                }
            }
        }

        private fun setRoundedColumn(iStart: Int, iEnd: Int, j: Int) {
            for (k in iStart..iEnd) {
                panel[k][j] = ROUNDED
            }
        }

        fun tiltEast() {
            for (i in 0 until panel.size) {
                tiltRowEast(i)
            }
//            printPanel()
        }

        private fun tiltRowEast(i: Int) {
            var countOfRounded = 0
            val lastPos = panel[0].size - 1
            for (j in (0..lastPos)) {
                when (panel[i][j]) {
                    ROUNDED     -> {
                        panel[i][j] = EMPTY
                        countOfRounded++
                        if (j == lastPos) {
                            setRoundedRow(i, lastPos - countOfRounded + 1, lastPos)
                        }
                    }
                    CUBE        -> {
                        setRoundedRow(i, j - countOfRounded, j - 1)
                        countOfRounded = 0
                    }
                    EMPTY       -> {
                        if (j == lastPos) {
                            setRoundedRow(i, lastPos - countOfRounded + 1, lastPos)
                        }
                    }
                }
            }
        }

        private fun setRoundedRow(i: Int, jStart: Int, jEnd: Int) {
            for (k in jStart..jEnd) {
                panel[i][k] = ROUNDED
            }
        }

        fun tiltSouth() {
            for (j in 0 until panel[0].size) {
                tiltColumnSouth(j)
            }
//            printPanel()
        }

        private fun tiltColumnSouth(j: Int) {
            var countOfRounded = 0
            val lastPos = panel.size - 1
            for (i in (0..lastPos)) {
                when (panel[i][j]) {
                    ROUNDED     -> {
                        panel[i][j] = EMPTY
                        countOfRounded++
                        if (i == lastPos) {
                            setRoundedColumn(lastPos - countOfRounded + 1, lastPos, j)
                        }
                    }
                    CUBE        -> {
                        setRoundedColumn(i - countOfRounded, i - 1, j)
                        countOfRounded = 0
                    }
                    EMPTY       -> {
                        if (i == lastPos) {
                            setRoundedColumn(lastPos - countOfRounded + 1, lastPos, j)
                        }
                    }
                }
            }
        }

        fun tiltWest() {
            for (i in 0 until panel.size) {
                tiltRowWest(i)
            }
//            printPanel()
        }

        private fun tiltRowWest(i: Int) {
            var countOfRounded = 0
            for (j in (0 until panel[0].size).reversed()) {
                when (panel[i][j]) {
                    ROUNDED     -> {
                        panel[i][j] = EMPTY
                        countOfRounded++
                        if (j == 0) {
                            setRoundedRow(i, 0, countOfRounded - 1)
                        }
                    }
                    CUBE        -> {
                        setRoundedRow(i, j + 1, j + countOfRounded)
                        countOfRounded = 0
                    }
                    EMPTY       -> {
                        if (j == 0) {
                            setRoundedRow(i, 0, countOfRounded - 1)
                        }
                    }
                }
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

        fun set(platform: Platform) {
            if (panel.isEmpty()) {
                for (i in 0 until platform.panel.size) {
                    val row = ArrayList<Char>()
                    for (j in 0 until platform.panel[0].size) {
                        row.add(platform.panel[i][j])
                    }
                    panel.add(row)
                }
            } else {
                for (i in 0 until panel.size) {
                    for (j in 0 until panel[0].size) {
                        panel[i][j] = platform.panel[i][j]
                    }
                }
            }
        }

        override fun equals(other: Any?): Boolean {
            val platform = other as Platform
            if (panel.size > 0 && platform.panel.size > 0) {
                if (panel[0].size != platform.panel[0].size) {
                    return false
                }
            } else {
                return false
            }
            for (i in 0 until panel.size) {
                for (j in 0 until panel[0].size) {
                    if (panel[i][j] != platform.panel[i][j]) {
                        return false
                    }
                }
            }
            return true
        }

        override fun hashCode(): Int {
            return panel.hashCode()
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

    fun countV2(): Int {
        val path = MAIN_INPUT_PATH.format("Day14")
        return countV2(path)
    }

    fun countV2(path: String): Int {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        val platform = Platform()
        platform.set(scanner)
        var cycle = 0
        val loadList = LinkedList<Int>()
        var currentLoad = platform.load()
        val hashCodeList = LinkedList<Int>()
        var currentHashCode = platform.hashCode()
        while (!hashCodeList.contains(currentHashCode) && (cycle < 20)) {
            loadList.add(currentLoad)
            hashCodeList.add(currentHashCode)
            // cycle start
            platform.tiltNorth()
            platform.tiltWest()
            platform.tiltSouth()
            platform.tiltEast()
            // cycle end
            platform.printPanel()
            currentLoad = platform.load()
            currentHashCode = platform.hashCode()
            cycle++
        }
        loadList.add(currentLoad)
        hashCodeList.add(currentHashCode)
//        println(hashCodeList)
        return loadAfterMaxCycle(loadList, hashCodeList)
        // 95053 too high
    }

    private fun loadAfterMaxCycle(loadList: LinkedList<Int>, hashCodeList: LinkedList<Int>): Int {
        var startLoopPos = 0
        val maxPos = loadList.size - 1
        for (i in 0..maxPos) {
            if (hashCodeList[i] == hashCodeList.last) {
                startLoopPos = i
                break
            }
        }
        return if (startLoopPos == maxPos) {
            loadList.last
        } else {
            val loadPos = (MAX_CYCLE - startLoopPos) % (maxPos - startLoopPos) + startLoopPos
//            println("$startLoopPos $maxPos $loadPos")
            loadList[loadPos]
        }
    }
}