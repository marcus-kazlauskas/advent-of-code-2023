import java.io.File
import java.util.*
import kotlin.io.path.Path

object Day2 {
    const val VALUE = 1082
    private const val GAME_PREFIX = "Game"
    private val CUBE_TO_NUMBER = mapOf(
        "red" to 12,
        "green" to 13,
        "blue" to 14
    )
    private const val MAX_NUMBER = 100
    private const val GAME_DELIMITER = ": "
    private const val SET_DELIMITER = "; "
    private const val CUBES_DELIMITER = ", "
    private const val NUMBER_DELIMITER = " "
    private const val EMPTY_GAME_ID = -1
    private const val GAME_ID_POS = 5

    fun count(): Int {
        val path = MAIN_INPUT_PATH.format("Day2")
        return count(path)
//        3316
    }

    fun count(path: String): Int {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        var idSum = 0
        while (scanner.hasNext()) {
            val line = scanner.nextLine().split(GAME_DELIMITER, SET_DELIMITER)
            val id = checkGame(line)
            if (id != EMPTY_GAME_ID) idSum += id
        }
        return idSum
    }

    private fun checkGame(line: List<String>): Int {
//        println(line.joinToString(" | "))
        var id = EMPTY_GAME_ID
        for (set in line) {
            if (isSet(set)) {
                id = getId(set)
            } else if (!checkSet(set)) {
                id = EMPTY_GAME_ID
            }
        }
        return id
    }

    private fun isSet(set: String): Boolean {
        return set.startsWith(GAME_PREFIX)
    }

    private fun getId(set: String): Int {
        return set.substring(GAME_ID_POS).toInt()
    }

    private fun checkSet(set: String): Boolean {
//        println(set)
        val cubes = set.split(CUBES_DELIMITER)
        var check = true
        var i = 0
        while (check && i < cubes.size) {
            check = checkCube(cubes[i])
            i++
        }
        return check
    }

    private fun checkCube(cube: String): Boolean {
//        println(cube)
        val info = cube.split(NUMBER_DELIMITER)
        return if (info.size == 2) {
            val number = info[0].toInt()
            val color = info[1]
            val maxNumber = CUBE_TO_NUMBER.getOrDefault(color, MAX_NUMBER)
            (maxNumber >= number)
        } else false
    }
}