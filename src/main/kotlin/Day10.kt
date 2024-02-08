import java.io.File
import java.util.*
import kotlin.collections.HashMap
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
    private val border = HashMap<Int, LinkedList<Int>>()

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
            X 0 X   Вместо for и when стоило бы просто написать подряд 4 варианта
            3 S 1
            X 2 X
             */
            when (i) {
                0   -> {
                    if (start.first != 0) {
                        val point = Pair(start.first - 1, start.second)
                        val pipe = getPipe(point)
                        if (!setOf(
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
                        if (!setOf(
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
                        if (!setOf(
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
                        if (!setOf(
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
        addToBorder(point)
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
        addToBorder(point)
    }

    fun countV2(): Int {
        val path = MAIN_INPUT_PATH.format("Day10")
        return countV2(path)
    }

    fun countV2(path: String): Int {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        setField(scanner)
        firstStep()
        while (getPipe(point) != START) {
            nextStep()
        }
        return countSquare()
    }

    private fun addToBorder(point: Pair<Int, Int>) {
        if (border.containsKey(point.first)) {
            border[point.first]?.add(point.second)
        } else {
            val list = LinkedList<Int>()
            list.add(point.second)
            border[point.first] = list
        }
    }

    private fun countSquare(): Int {
        var square = 0
        for (e in border.entries) {
            val i = e.key
            val slice = e.value
            slice.sort()
            square += checkSlices(i, slice)
        }
        return square
    }

    private fun checkSlices(i: Int, js: LinkedList<Int>): Int {
        println("border[$i] = $js")
        var sliceSum = 0
        var borderCount = 0
        var k = 0
        var prevJ: Int
        var j = js[0]
        var startSlicePipe = getPipe(Pair(i, j))
        if (startSlicePipe == NS_PIPE) {
            borderCount++
            startSlicePipe = GROUND
        }
        while (k < js.size - 1) {
            k++
            prevJ = j
            j = js[k]
            when (getPipe(Pair(i, j))) {
                NS_PIPE     -> {
                    if (startSlicePipe == START) {
                        borderCount++
                    }
                    if (borderCount % 2 == 1) {
                        sliceSum += j - prevJ - 1
                    }
                    borderCount++
                }
                NE_PIPE     -> {
                    if (startSlicePipe == START) {
                        borderCount++
                    }
                    if (borderCount % 2 == 1) {
                        sliceSum += j - prevJ - 1
                    }
                    startSlicePipe = NE_PIPE
                }
                NW_PIPE     -> {
                    if (startSlicePipe == SE_PIPE || startSlicePipe == START) {
                        borderCount++
                    }
                    startSlicePipe = GROUND
                }
                SW_PIPE     -> {
                    if (startSlicePipe == NE_PIPE || startSlicePipe == START) {
                        borderCount++
                    }
                    startSlicePipe = GROUND
                }
                SE_PIPE     -> {
                    if (startSlicePipe == START) {
                        borderCount++
                    }
                    if (borderCount % 2 == 1) {
                        sliceSum += j - prevJ - 1
                    }
                    startSlicePipe = SE_PIPE
                }
                START       -> {
                    startSlicePipe = START
                }
            }
        }
        return sliceSum
    }
}