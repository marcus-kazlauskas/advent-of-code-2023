import java.io.File
import java.util.*
import kotlin.io.path.Path

object Day10 {
    const val VALUE = 1105
    private const val NS_PIPE = '|'
    private const val EW_PIPE = '-'
    private const val NE_PIPE = 'L'
    private const val NW_PIPE = 'J'
    private const val SW_PIPE = '7'
    private const val SE_PIPE = 'F'
    private const val GROUND = '.'
    private const val START = 'S'
    private const val POINTS_AROUND = 4
    private val DEFAULT_POINT = Pair(-1, -1)
    private val field = LinkedList<String>()
    private var start = DEFAULT_POINT
    private var prevPoint = DEFAULT_POINT
    private var point = DEFAULT_POINT


    fun count(): Int {
        val path = MAIN_INPUT_PATH.format("Day10")
        return count(path)
    }

    fun count(path: String): Int {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        setField(scanner)
        firstStep()
        var count = 1
        while (getPipe(point) != START) {
            nextStep()
            count++
        }
        return count / 2
    }

    private fun setField(scanner: Scanner) {
        var i = 0
        while (scanner.hasNext()) {
            val line = scanner.nextLine()
            if (line.contains(START)) {
                val j = line.indexOf(START)
                start = Pair(i, j)
            }
            field.add(line)
            i++
        }
    }

    private fun firstStep() {
        prevPoint = start
        for (i in 0 until POINTS_AROUND) {
            /*
            X 0 X
            3 S 1
            X 2 X
             */
            when (i) {
                0   -> {
                    if (start.first != 0) {
                        val point = Pair(start.first - 1, start.second)
                        val pipe = getPipe(point)
                        if (!listOf(
                                EW_PIPE,
                                NE_PIPE,
                                NW_PIPE,
                                GROUND
                            ).contains(pipe)) {
                            this.point = point
                            break
                        }
                    }
                }
                1   -> {
                    if (start.second != field[0].length - 1) {
                        val point = Pair(start.first, start.second + 1)
                        val pipe = getPipe(point)
                        if (!listOf(
                                NS_PIPE,
                                NE_PIPE,
                                SE_PIPE,
                                GROUND
                            ).contains(pipe)) {
                            this.point = point
                            break
                        }
                    }
                }
                2   -> {
                    if (start.first != field[0].length - 1) {
                        val point = Pair(start.first + 1, start.second)
                        val pipe = getPipe(point)
                        if (!listOf(
                                EW_PIPE,
                                SW_PIPE,
                                SE_PIPE,
                                GROUND
                            ).contains(pipe)) {
                            this.point = point
                            break
                        }
                    }
                }
                3   -> {
                    if (start.second != 0) {
                        val point = Pair(start.first, start.second - 1)
                        val pipe = getPipe(point)
                        if (!listOf(
                                NS_PIPE,
                                NW_PIPE,
                                SW_PIPE,
                                GROUND
                            ).contains(pipe)) {
                            this.point = point
                            break
                        }
                    }
                }
            }
        }
    }

    private fun getPipe(point: Pair<Int, Int>): Char {
        val i = point.first
        val j = point.second
        return field[i][j]
    }

    private fun nextStep() {
        when (getPipe(point)) {
            NS_PIPE     -> {
                if (prevPoint.first == point.first + 1) {
                    prevPoint = point
                    point = Pair(point.first - 1, point.second)
                } else {
                    prevPoint = point
                    point = Pair(point.first + 1, point.second)
                }
            }
            EW_PIPE     -> {
                if (prevPoint.second == point.second + 1) {
                    prevPoint = point
                    point = Pair(point.first, point.second - 1)
                } else {
                    prevPoint = point
                    point = Pair(point.first, point.second + 1)
                }
            }
            NE_PIPE     -> {
                if (prevPoint.second == point.second + 1) {
                    prevPoint = point
                    point = Pair(point.first - 1, point.second)
                } else {
                    prevPoint = point
                    point = Pair(point.first, point.second + 1)
                }
            }
            NW_PIPE     -> {
                if (prevPoint.second == point.second - 1) {
                    prevPoint = point
                    point = Pair(point.first - 1, point.second)
                } else {
                    prevPoint = point
                    point = Pair(point.first, point.second - 1)
                }
            }
            SW_PIPE     -> {
                if (prevPoint.second == point.second - 1) {
                    prevPoint = point
                    point = Pair(point.first + 1, point.second)
                } else {
                    prevPoint = point
                    point = Pair(point.first, point.second - 1)
                }
            }
            SE_PIPE     -> {
                if (prevPoint.second == point.second + 1) {
                    prevPoint = point
                    point = Pair(point.first + 1, point.second)
                } else {
                    prevPoint = point
                    point = Pair(point.first, point.second + 1)
                }
            }
        }
    }
}