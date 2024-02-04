import java.io.File
import java.util.*
import kotlin.io.path.Path

object Day9 {
    const val VALUE = 1095
    private val extrapolations = LinkedList<Int>()

    private class Sequence {
        private val base = LinkedList<Int>()
        private val historyForExtrapolation = LinkedList<Int>()

        fun addValue(value: Int) {
            base.add(value)
        }

        fun setHistoryForExt() {
            var seq = base
            setLastToHistory(seq)
            while (!allZeroes(seq)) {
                val ext = LinkedList<Int>()
                for (i in 0 until (seq.size - 1)) {
                    val diff = seq[i + 1] - seq[i]
                    ext.add(diff)
                }
                seq = ext
                setLastToHistory(seq)
            }
        }

        private fun setLastToHistory(seq: LinkedList<Int>) {
            historyForExtrapolation.addFirst(seq.last)
        }

        private fun allZeroes(seq: LinkedList<Int>): Boolean {
            for (i in seq) {
                if (i != 0) return false
            }
            return true
        }

        fun getExtrapolation(): Int {
            var ext = historyForExtrapolation.first
            for (i in 1 until historyForExtrapolation.size) {
                ext += historyForExtrapolation[i]
            }
            return ext
        }

        fun setHistoryForExtV2() {
            var seq = base
            setFirstToHistory(seq)
            while (!allZeroes(seq)) {
                val ext = LinkedList<Int>()
                for (i in 0 until (seq.size - 1)) {
                    val diff = seq[i + 1] - seq[i]
                    ext.add(diff)
                }
                seq = ext
                setFirstToHistory(seq)
            }
        }

        private fun setFirstToHistory(seq: LinkedList<Int>) {
            historyForExtrapolation.addFirst(seq.first)
        }

        fun getExtrapolationV2(): Int {
            var ext = historyForExtrapolation.first
            for (i in 1 until historyForExtrapolation.size) {
                ext = -ext + historyForExtrapolation[i]
            }
            return ext
        }
    }

    fun count(): Int {
        val path = MAIN_INPUT_PATH.format("Day9")
        return count(path)
    }

    fun count(path: String): Int {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        while (scanner.hasNext()) {
            addExt(getSeq(scanner))
        }
        return extSum()
    }

    private fun getSeq(scanner: Scanner): Sequence {
        val line = scanner.nextLine().split(' ')
        val sequence = Sequence()
        for (l in line) {
            sequence.addValue(l.toInt())
        }
        return sequence
    }

    private fun addExt(sequence: Sequence) {
        sequence.setHistoryForExt()
        val extrapolation = sequence.getExtrapolation()
        extrapolations.add(extrapolation)
    }

    private fun extSum(): Int {
        return extrapolations.fold(0) { sum, value -> sum + value }
    }

    fun countV2(): Int {
        val path = MAIN_INPUT_PATH.format("Day9")
        return countV2(path)
    }

    fun countV2(path: String): Int {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        while (scanner.hasNext()) {
            addExtV2(getSeq(scanner))
        }
        return extSum()
    }

    private fun addExtV2(sequence: Sequence) {
        sequence.setHistoryForExtV2()
        val extrapolation = sequence.getExtrapolationV2()
        extrapolations.add(extrapolation)
    }
}