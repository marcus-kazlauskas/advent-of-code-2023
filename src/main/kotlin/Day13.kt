import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.io.path.Path

object Day13 {
    const val VALUE = 1113

    private open class Pattern {
        protected val pattern = ArrayList<String>()
        protected var verticalSymmetry = 0
        protected var horizontalSymmetry = 0

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

        protected fun verticalSize(): Int {
            return pattern.size
        }

        protected fun horizontalSize(): Int {
            return pattern[0].length
        }

        protected fun findVertSym() {
            for (j in (0 until horizontalSize() / 2).reversed()) {
                if (checkVertSymFromLeft(j)) {
                    verticalSymmetry = j + 1
                    break
                }
                if (checkVertSymFromRight(j)) {
                    verticalSymmetry = horizontalSize() - (j + 1)
                    break
                }
            }
        }

        protected fun checkVertSymFromLeft(j: Int): Boolean {
            for (i in 0 until verticalSize()) {
                val line = pattern[i]
                if (line.slice(0..j) !=
                    line.slice(((j + 1)..(j * 2 + 1))).reversed()) {
                    return false
                }
            }
            return true
        }

        protected fun checkVertSymFromRight(j: Int): Boolean {
            for (i in 0 until verticalSize()) {
                val line = pattern[i]
                if (line.slice((horizontalSize() - (j + 1) * 2) until horizontalSize() - (j + 1)) !=
                    line.slice((horizontalSize() - (j + 1)) until horizontalSize()).reversed()) {
                    return false
                }
            }
            return true
        }

        protected fun findHorizonSym() {
            for (i in (0 until verticalSize() / 2).reversed()) {
                if (checkHorizonSymFromUp(i)) {
                    horizontalSymmetry = i + 1
                    break
                }
                if (checkHorizonSymFromDown(i)) {
                    horizontalSymmetry = verticalSize() - (i + 1)
                    break
                }
            }
        }

        protected fun checkHorizonSymFromUp(iMax: Int): Boolean {
            for (j in 0 until horizontalSize()) {
                for (i in 0..iMax) {
                    if (pattern[i][j] != pattern[iMax * 2 + 1 - i][j]) {
                        return false
                    }
                }
            }
            return true
        }

        protected fun checkHorizonSymFromDown(iMax: Int): Boolean {
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
    }

    private class PatternV2: Pattern() {
        private var newVertSymSuccess = false
        private var newHorizonSymSuccess = false

        fun findSymmetryV2() {
            findVertSym()
            findVertSymV2()
            findHorizonSym()
            findHorizonSymV2()
            if (!newVertSymSuccess) {
                verticalSymmetry = 0
            }
            if (!newHorizonSymSuccess) {
                horizontalSymmetry = 0
            }
        }

        private fun findVertSymV2() {
            for (j in (0 until horizontalSize() / 2).reversed()) {
                if (checkVertSymFromLeftV2(j)) {
                    val symmetryBuffer = j + 1
                    if (verticalSymmetry != symmetryBuffer) {
                        verticalSymmetry = symmetryBuffer
                        newVertSymSuccess = true
                        break
                    }
                }
                if (checkVertSymFromRightV2(j)) {
                    val symmetryBuffer = horizontalSize() - (j + 1)
                    if (verticalSymmetry != symmetryBuffer) {
                        verticalSymmetry = symmetryBuffer
                        newVertSymSuccess = true
                        break
                    }
                }
            }
        }

        private fun checkVertSymFromLeftV2(jMax: Int): Boolean {
            var one = false
            for (i in 0 until verticalSize()) {
                for (j in 0..jMax) {
                    if (pattern[i][j] != pattern[i][jMax * 2 + 1 - j]) {
                        if (!one) one = true
                        else return false
                    }
                }
            }
            return true
        }

        private fun checkVertSymFromRightV2(jMax: Int): Boolean {
            var one = false
            for (i in 0 until verticalSize()) {
                for (j in 0..jMax) {
                    if (pattern[i][horizontalSize() - (jMax + 1) * 2 + j] != pattern[i][horizontalSize() - 1 - j]) {
                        if (!one) one = true
                        else return false
                    }
                }
            }
            return true
        }

        private fun findHorizonSymV2() {
            for (i in (0 until verticalSize() / 2).reversed()) {
                if (checkHorizonSymFromUpV2(i)) {
                    val symmetryBuffer = i + 1
                    if (horizontalSymmetry != symmetryBuffer) {
                        horizontalSymmetry = symmetryBuffer
                        newHorizonSymSuccess = true
                        break
                    }
                }
                if (checkHorizonSymFromDownV2(i)) {
                    val symmetryBuffer = verticalSize() - (i + 1)
                    if (horizontalSymmetry != symmetryBuffer) {
                        horizontalSymmetry = symmetryBuffer
                        newHorizonSymSuccess = true
                        break
                    }
                }
            }
        }

        private fun checkHorizonSymFromUpV2(iMax: Int): Boolean {
            var one = false
            for (j in 0 until horizontalSize()) {
                for (i in 0..iMax) {
                    if (pattern[i][j] != pattern[iMax * 2 + 1 - i][j]) {
                        if (!one) one = true
                        else return false
                    }
                }
            }
            return true
        }

        private fun checkHorizonSymFromDownV2(iMax: Int): Boolean {
            var one = false
            for (j in 0 until horizontalSize()) {
                for (i in 0..iMax) {
                    if (pattern[verticalSize() - (iMax + 1) * 2 + i][j] != pattern[verticalSize() - 1 - i][j]) {
                        if (!one) one = true
                        else return false
                    }
                }
            }
            return true
        }
    }

    fun countV2(): Long {
        val path = MAIN_INPUT_PATH.format("Day13")
        return countV2(path)
    }

    fun countV2(path: String): Long {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        var count = 0L
        count += nextCountV2(scanner)
        while (scanner.hasNext()) {
            count += nextCountV2(scanner)
        }
        return count
    }

    private fun nextCountV2(scanner: Scanner): Int {
        val pattern = PatternV2()
        pattern.set(scanner)
        pattern.findSymmetryV2()
        return pattern.getCount()
    }
}