import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.io.path.Path

object Day13 {
    const val VALUE = 1113

    private class Pattern {
        val pattern = ArrayList<String>()
        var verticalSymmetry = 0
        var horizontalSymmetry = 0

        fun set(scanner: Scanner) {
            var line = scanner.nextLine()
            pattern.add(line)
            while (scanner.hasNext() && line != "") {
                line = scanner.nextLine()
                if (line != "") pattern.add(line)
            }
        }

        fun findSymmetry() {
            findVertSym()
            findHorizonSym()
        }

        private fun verticalSize(): Int {
            return pattern.size
        }

        private fun horizontalSize(): Int {
            return pattern[0].length
        }

        private fun findVertSym() {
            for (j in (0 until horizontalSize() / 2).reversed()) {
                if (checkVertSymFromLeft(j) || checkVertSymFromRight(j)) {
                    verticalSymmetry = horizontalSize() - (j + 1)
                    break
                }
            }
        }

        private fun checkVertSymFromLeft(j: Int): Boolean {
            for (i in 0 until verticalSize()) {
                val line = pattern[i]
                if (line.slice(0..j) !=
                    line.slice(((j + 1)..(j * 2 + 1))).reversed()) {
                    return false
                }
            }
            return true
        }

        private fun checkVertSymFromRight(j: Int): Boolean {
            for (i in 0 until verticalSize()) {
                val line = pattern[i]
                if (line.slice((horizontalSize() - (j + 1) * 2) until horizontalSize() - (j + 1)) !=
                    line.slice((horizontalSize() - (j + 1)) until horizontalSize()).reversed()) {
                    return false
                }
            }
            return true
        }

        private fun findHorizonSym() {
            for (i in (0 until verticalSize() / 2).reversed()) {
                // ебучие индексы блять
                // и ремонт ещё этот
                if (checkHorizonSymFromUp(i) || checkHorizonSymFromDown(i)) {
                    horizontalSymmetry = verticalSize() - (i + 1)
                    break
                }
            }
        }

        private fun checkHorizonSymFromUp(iMax: Int): Boolean {
            for (j in 0 until horizontalSize()) {
                for (i in 0..iMax) {
                    if (pattern[i][j] != pattern[iMax * 2 + 1 - i][j]) {
                        return false
                    }
                }
            }
            return true
        }

        private fun checkHorizonSymFromDown(iMax: Int): Boolean {
            for (j in 0 until horizontalSize()) {
                for (i in 0..iMax) {
                    if (pattern[verticalSize() - (iMax + 1) * 2 + i][j] != pattern[verticalSize() - 1 - i][j]) {
                        return false
                    }
                }

            }
            return true
        }

        fun getCount(): Int {
            return verticalSymmetry + horizontalSymmetry * 100
        }
    }

    fun count(): Long {
        val path = MAIN_INPUT_PATH.format("Day13")
        return count(path)
    }

    fun count(path: String): Long {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        var count = 0L
        count += nextCount(scanner)
        while (scanner.hasNext()) {
            count += nextCount(scanner)
        }
        return count
    }

    private fun nextCount(scanner: Scanner): Int {
        val pattern = Pattern()
        pattern.set(scanner)
        pattern.findSymmetry()
        return pattern.getCount()
        // 60457 too high
    }
}