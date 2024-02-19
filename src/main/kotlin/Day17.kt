import java.io.File
import java.util.*
import kotlin.io.path.Path

object Day17 {
    const val VALUE = 1090

    private enum class Direction {
        START,
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    private class Step(
        val direction: EnumMap<Direction, Int>,
        val loss: Int
    )

    private class Map(
        val minStepsNum: Int,
        val maxStepsNum: Int
    ) {
        val map = ArrayList<List<Step>>()
        val ways = LinkedList<Triple<Int, Int, Direction>>()

        fun set (scanner: Scanner) {
            while (scanner.hasNext()) {
                val line = scanner.nextLine()
                    .toCharArray()
                    .map { c -> Step(EnumMap(Direction::class.java), c.toString().toInt()) }
                map.add(line)
            }
        }

        fun computeWays() {
            next(0, 0, Direction.START)
            while (ways.isNotEmpty()) {
                val way = ways.poll()
                val i = way.first
                val j = way.second
                val direction = way.third
                if (i < maxRowPos() || j < maxColumnPos()) {
                    next(i, j, direction)
                }
            }
        }

        private fun next(i: Int, j: Int, direction: Direction) {
            when (direction) {
                Direction.START -> {
                    for (delta in minStepsNum..maxStepsNum) {
                        // rightward direction
                        val jStart = j + 1
                        val jEnd = j + delta
                        if (jEnd <= maxColumnPos()) {
                            var loss = getLoss(i, j, Direction.START)
                            for (jStep in jStart..jEnd) {
                                loss += map[i][jStep].loss
                            }
                            if (setWay(i, jEnd, Direction.RIGHT, loss)) {
                                ways.add(Triple(i, jEnd, Direction.RIGHT))
                            }
                        }
                        // downward direction
                        val iStart = i + 1
                        val iEnd = i + delta
                        if (iEnd <= maxRowPos()) {
                            var loss = getLoss(i, j, Direction.START)
                            for (iStep in iStart..iEnd) {
                                loss += map[iStep][j].loss
                            }
                            if (setWay(iEnd, j, Direction.DOWN, loss)) {
                                ways.add(Triple(iEnd, j, Direction.DOWN))
                            }
                        }
                    }
                }
                Direction.UP, Direction.DOWN -> {
                    for (delta in minStepsNum..maxStepsNum) {
                        // rightward direction
                        var jStart = j + 1
                        var jEnd = j + delta
                        if (jEnd <= maxColumnPos()) {
                            var loss = getLoss(i, j, direction)
                            for (jStep in jStart..jEnd) {
                                loss += map[i][jStep].loss
                            }
                            if (setWay(i, jEnd, Direction.RIGHT, loss)) {
                                ways.add(Triple(i, jEnd, Direction.RIGHT))
                            }
                        }
                        // leftward direction
                        jStart = j - 1
                        jEnd = j - delta
                        if (jEnd >= 0) {
                            var loss = getLoss(i, j, direction)
                            for (jStep in jStart downTo jEnd) {
                                loss += map[i][jStep].loss
                            }
                            if (setWay(i, jEnd, Direction.LEFT, loss)) {
                                ways.add(Triple(i, jEnd, Direction.LEFT))
                            }
                        }
                    }
                }
                Direction.RIGHT, Direction.LEFT -> {
                    for (delta in minStepsNum..maxStepsNum) {
                        // upward direction
                        var iStart = i - 1
                        var iEnd = i - delta
                        if (iEnd >= 0) {
                            var loss = getLoss(i, j, direction)
                            for (iStep in iStart downTo iEnd) {
                                loss += map[iStep][j].loss
                            }
                            if (setWay(iEnd, j, Direction.UP, loss)) {
                                ways.add(Triple(iEnd, j, Direction.UP))
                            }
                        }
                        // downward direction
                        iStart = i + 1
                        iEnd = i + delta
                        if (iEnd <= maxRowPos()) {
                            var loss = getLoss(i, j , direction)
                            for (iStep in iStart..iEnd) {
                                loss += map[iStep][j].loss
                            }
                            if (setWay(iEnd, j, Direction.DOWN, loss)) {
                                ways.add(Triple(iEnd, j, Direction.DOWN))
                            }
                        }
                    }
                }
            }
        }

        private fun maxColumnPos(): Int {
            return map[0].size - 1
        }

        private fun maxRowPos(): Int {
            return map.size - 1
        }

        private fun getLoss(i: Int, j: Int, direction: Direction): Int {
//            println("startLoss[$i][$j].$direction found? ${map[i][j].direction.contains(direction)}")
            return map[i][j].direction.getOrDefault(direction, 0)
        }

        private fun setWay(i: Int, j: Int, direction: Direction, loss: Int): Boolean {
            val oldLoss = map[i][j].direction.getOrDefault(direction, Int.MAX_VALUE)
            return if (loss < oldLoss) {
//                println("loss < (endLoss[$i][$j].$direction = oldLoss), $loss < $oldLoss")
                map[i][j].direction[direction] = loss
                true
            } else false
        }

        fun leastHeatLoss(): Int {
            val finish = map[maxRowPos()][maxColumnPos()]
            return finish.direction.values.min()
        }
    }

    fun count(): Int {
        val path = MAIN_INPUT_PATH.format("Day17")
        return count(path)
    }

    fun count(path: String): Int {
        return countLeastHeatLoss(path, 1, 3)
    }

    private fun countLeastHeatLoss(path: String, minStepsCount: Int, maxStepsCount: Int): Int {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        val map = Map(minStepsCount, maxStepsCount)
        map.set(scanner)
        map.computeWays()
        return map.leastHeatLoss()
    }

    fun countV2(): Int {
        val path = MAIN_INPUT_PATH.format("Day17")
        return countV2(path)
    }

    fun countV2(path: String): Int {
        return countLeastHeatLoss(path, 4, 10)
    }
}