import java.io.File
import java.util.*
import kotlin.collections.HashMap
import kotlin.io.path.Path

object Day18 {
    const val VALUE = 1121

    private enum class Border {
        HORIZONTAL,
        VERTICAL,
        UP_CORNER,
        DOWN_CORNER,
        UNKNOWN
    }

    private class Hole(val j: Int, val direction: Border, val color: String): Comparable<Hole> {
        override fun compareTo(other: Hole): Int {
            return j.compareTo(other.j)
        }

        override fun toString(): String {
            return "[$j - $direction - (#$color)]"
        }
    }

    private class Lagoon {
        private companion object {
            const val UP = "U"
            const val RIGHT = "R"
            const val DOWN = "D"
            const val LEFT = "L"

            fun createHole(j: Int, direction: String, color: String): Hole {
                return when (direction) {
                    UP, DOWN -> {
                        Hole(j, Border.VERTICAL, color)
                    }
                    RIGHT, LEFT -> {
                        Hole(j, Border.HORIZONTAL, color)
                    }
                    else -> {
                        Hole(j, Border.UNKNOWN, color)
                    }
                }
            }

            fun createCornerHole(j: Int, direction: String, directionNext: String, color: String): Hole {
                return when (setOf(direction, directionNext)) {
                    setOf(RIGHT, DOWN), setOf(LEFT, UP) -> {
                        Hole(j, Border.DOWN_CORNER, color)
                    }
                    setOf(DOWN, LEFT), setOf(UP, RIGHT) -> {
                        Hole(j, Border.UP_CORNER, color)
                    }
                    else -> {
                        Hole(j, Border.UNKNOWN, color)
                    }
                }
            }
        }

        val input = LinkedList<Triple<String, Int, String>>()
        val plan = HashMap<Int, LinkedList<Hole>>()

        fun set(scanner: Scanner) {
            while (scanner.hasNext()) {
                val line = scanner.nextLine().split(' ')
                val direction = line[0]
                val distance = line[1].toInt()
                val color = (line[2].split(Regex("(\\(#)|(\\))")))[1]
//                println(Hole(0, direction, color))
                input.add(Triple(direction, distance, color))
            }
            input.add(input.first())
        }

        fun buildPlan() {
            val lastPos = input.size - 2
            var iCurrent = 0
            var jCurrent = 0
            for (k in 0..lastPos) {
                val value = input.poll()
                val direction = value.first
                val distance = value.second
                val color = value.third
                val directionNext = input.first().first
                when (direction) {
                    UP -> {
                        val iStart = iCurrent - 1
                        val iEnd = iCurrent - distance
                        for (i in iStart downTo iEnd) {
                            if (i != iEnd) {
                                val hole = createHole(jCurrent, direction, color)
                                add(i, hole)
                            } else {
                                val cornerHole = createCornerHole(jCurrent, direction, directionNext, color)
                                add(i, cornerHole)
                            }
                        }
                        iCurrent = iEnd
                    }
                    RIGHT -> {
                        val jStart = jCurrent + 1
                        val jEnd = jCurrent + distance
                        for (j in jStart..jEnd) {
                            if (j != jEnd) {
                                val hole = createHole(j, direction, color)
                                add(iCurrent, hole)
                            } else {
                                val cornerHole = createCornerHole(j, direction, directionNext, color)
                                add(iCurrent, cornerHole)
                            }
                        }
                        jCurrent = jEnd
                    }
                    DOWN -> {
                        val iStart = iCurrent + 1
                        val iEnd = iCurrent + distance
                        for (i in iStart..iEnd) {
                            if (i != iEnd) {
                                val hole = createHole(jCurrent, direction, color)
                                add(i, hole)
                            } else {
                                val cornerHole = createCornerHole(jCurrent, direction, directionNext, color)
                                add(i, cornerHole)
                            }
                        }
                        iCurrent = iEnd
                    }
                    LEFT -> {
                        val jStart = jCurrent - 1
                        val jEnd = jCurrent - distance
                        for (j in jStart downTo jEnd) {
                            if (j != jEnd) {
                                val hole = createHole(j, direction, color)
                                add(iCurrent, hole)
                            } else {
                                val cornerHole = createCornerHole(j, direction, directionNext, color)
                                add(iCurrent, cornerHole)
                            }
                        }
                        jCurrent = jEnd
                    }
                }
            }
        }

        private fun add(i: Int, hole: Hole) {
            if (!plan.contains(i)) {
                plan[i] = LinkedList<Hole>()
            }
            plan[i]?.add(hole)
        }

        fun square(): Int {
            var countSquare = 0
            for (e in plan.entries) {
                val line = e.value
                line.sort()
                println("${e.key}) $line")
                var countBorder = 1
                val jEnd = line.size - 1
                var holePrev = line[0]
                var isBorder = setOf(Border.UP_CORNER, Border.DOWN_CORNER).contains(holePrev.direction)
                for (j in 1..jEnd) {
                    val hole = line[j]
                    when (setOf(holePrev.direction, hole.direction)) {
                        setOf(Border.UP_CORNER),
                        setOf(Border.DOWN_CORNER) -> {
                            if (countBorder % 2 == 1 && !isBorder) {
                                countSquare += hole.j - holePrev.j - 1
                                countBorder++
                            } else if (isBorder) {
                                isBorder = false
                            } else {
                                countBorder++
                            }
                            holePrev = hole
                        }

                        setOf(Border.UP_CORNER, Border.VERTICAL),
                        setOf(Border.DOWN_CORNER, Border.VERTICAL),
                        setOf(Border.VERTICAL) -> {
                            if (countBorder % 2 == 1) {
                                countSquare += hole.j - holePrev.j - 1
                            }
                            countBorder++
                            holePrev = hole
                        }
                    }
                }
//                    println("square = $countSquare, countBorder = $countBorder")
                countSquare += line.size
                println("square = $countSquare, countBorder = $countBorder")
            }
            return countSquare
        }
    }

    fun count(): Int {
        val path = MAIN_INPUT_PATH.format("Day18")
        return count(path)
    }

    fun count(path: String): Int {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        val lagoon = Lagoon()
        lagoon.set(scanner)
        lagoon.buildPlan()
        return lagoon.square()
    }
    // 44037 too low
}