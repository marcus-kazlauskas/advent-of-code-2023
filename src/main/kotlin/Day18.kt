import java.io.File
import java.util.*
import kotlin.io.path.Path
import kotlin.math.abs

object Day18 {
    /*
    Решение задачи похоже на алгоритм игры Xonix.
    Я заебался искать все возможные случаи заполнение пустот внутри границы.
    В последующие дни идут ещё более сложные задачи.
    На этом решение AoC 2023 завершается:-(((
     */
    const val VALUE = 1121
    private const val UP = "U"
    private const val RIGHT = "R"
    private const val DOWN = "D"
    private const val LEFT = "L"
    private const val NE_CORNER = 'L'
    private const val NW_CORNER = 'J'
    private const val SW_CORNER = '7'
    private const val SE_CORNER = 'F'

    private class Corner(
        val i: Int,
        val j: Int,
        val corner: Char,
        val clockwise: Boolean,
        val color: String
    ) {
        override fun toString(): String {
            return "[($i, $j) - ($corner, clockwise=$clockwise) - (#$color)]"
        }
    }

    private class Lagoon {
        val input = LinkedList<Triple<String, Int, String>>()
        val border = LinkedList<Corner>()

        fun set(scanner: Scanner) {
            while (scanner.hasNext()) {
                val line = scanner.nextLine().split(' ')
                val direction = line[0]
                val distance = line[1].toInt()
                val color = (line[2].split(Regex("(\\(#)|(\\))")))[1]
                input.add(Triple(direction, distance, color))
            }
            input.add(input.first())
        }

        fun buildBorder() {
            border.clear()
            var iCurrent = 0
            var jCurrent = 0
            for (k in 1 until input.size) {
                val section = input[k]
                val direction = section.first
                val sectionPrev = input[k - 1]
                val directionPrev = sectionPrev.first
                val distancePrev = sectionPrev.second
                val colorPrev = sectionPrev.third
                when (direction) {
                    UP -> {
                        when (directionPrev) {
                            RIGHT -> {
                                iCurrent += distancePrev
                                val corner = Corner(iCurrent, jCurrent, NW_CORNER, false, colorPrev)
                                border.add(corner)
                            }
                            LEFT -> {
                                iCurrent -= distancePrev
                                val corner = Corner(iCurrent, jCurrent, NE_CORNER, true, colorPrev)
                                border.add(corner)
                            }
                        }
                    }
                    RIGHT -> {
                        when (directionPrev) {
                            UP -> {
                                jCurrent -= distancePrev
                                val corner = Corner(iCurrent, jCurrent, SE_CORNER, true, colorPrev)
                                border.add(corner)
                            }
                            DOWN -> {
                                jCurrent += distancePrev
                                val corner = Corner(iCurrent, jCurrent, NE_CORNER, false, colorPrev)
                                border.add(corner)
                            }
                        }
                    }
                    DOWN -> {
                        when (directionPrev) {
                            RIGHT -> {
                                iCurrent += distancePrev
                                val corner = Corner(iCurrent, jCurrent, SW_CORNER, true, colorPrev)
                                border.add(corner)
                            }
                            LEFT -> {
                                iCurrent -= distancePrev
                                val corner = Corner(iCurrent, jCurrent, SE_CORNER, false, colorPrev)
                                border.add(corner)
                            }
                        }
                    }
                    LEFT -> {
                        when (directionPrev) {
                            UP -> {
                                jCurrent -= distancePrev
                                val corner = Corner(iCurrent, jCurrent, SW_CORNER, false, colorPrev)
                                border.add(corner)
                            }
                            DOWN -> {
                                jCurrent += distancePrev
                                val corner = Corner(iCurrent, jCurrent, NW_CORNER, true, colorPrev)
                                border.add(corner)
                            }
                        }
                    }
                }
            }
        }

        fun closeCavity() {
            var cavityFound = true
            while (cavityFound) {
                cavityFound = false
                for (k in 0 until border.size) {
                    for (l in 0 until k) {
                        if (isAdjacent(k, l)) {
                            replaceCorners(k, l)
                        }
                    }
                }
            }
        }

        private fun isAdjacent(k: Int, l: Int): Boolean {
            val kCorner = border[k]
            val lCorner = border[l]
            return abs(kCorner.i - lCorner.i) == 1 || abs(kCorner.j - lCorner.j) == 1
        }

        private fun replaceCorners(k: Int, l: Int) {
            val kCorner = border[k]
            val lCorner = border[l]
            /* TODO
            Тут должен быть свитч, в котором в зависимости от kCorner и lCorner,
            завершающего и открывающего полость углов, считая по направлению обхода границы,
            граница вокруг пустоты заменятся на перемычку, с учётом того что в границе
            сохранены только угловые элементы, а прямые подразумеваются между ними.
            Например:

            ---7 F->                 ---7 F->
            ..FJ |.                  ..FJ |.
            ..|  |.                  ..|  |.
            ..|F-J.                  ..L--J.
            ..||...  превращается в  .......
            .FJL7..                  .......
            .|  |..                  .......
            .L--J..                  .......
            .......                  .......

            > -- направление обхода границы
            . -- область внутри границы
            <space> -- область за пределами границы
             */
        }

        fun square(): Int {
            /* TODO
            Тут должен быть алгоритм интегрирования фигуры по угловым точкам
             */
            return 0
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
        lagoon.buildBorder()
        lagoon.closeCavity()
        return lagoon.square()
    }
}