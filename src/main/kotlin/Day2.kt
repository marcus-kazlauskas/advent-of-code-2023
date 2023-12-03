import java.io.File
import java.util.*
import kotlin.collections.HashMap
import kotlin.io.path.Path

object Day2 {
    const val VALUE = 1082
    private const val GAME_PREFIX = "Game"
    private const val RED_COLOR = "red"
    private const val GREEN_COLOR = "green"
    private const val BLUE_COLOR = "blue"
    private const val DEFAULT_COLOR = "default"
    private val CUBE_TO_NUMBER = mapOf(
        RED_COLOR to 12,
        GREEN_COLOR to 13,
        BLUE_COLOR to 14
    )
    private const val MAX_NUMBER = 100
    private const val DEFAULT_NUMBER = 0
    private const val GAME_DELIMITER = ": "
    private const val SET_DELIMITER = "; "
    private const val CUBES_DELIMITER = ", "
    private const val NUMBER_DELIMITER = " "
    private const val EMPTY_GAME_ID = -1
    private const val GAME_ID_POS = 5

    class Cube(inputColor: String, inputNumber: Int) {
        val color: String = inputColor
        val number: Int = inputNumber
    }

    fun count(): Int {
        val path = MAIN_INPUT_PATH.format("Day2")
        return count(path)
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
            if (isNotSet(set)) {
                id = getId(set)
            } else if (!checkSet(set)) {
                id = EMPTY_GAME_ID
            }
        }
        return id
    }

    private fun isNotSet(set: String): Boolean {
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

    fun countV2(): Int {
        val path = MAIN_INPUT_PATH.format("Day2")
        return countV2(path)
    }

    fun countV2(path: String): Int {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        val lineList = LinkedList<List<String>>()
        while (scanner.hasNext()) {
            val line = scanner.nextLine().split(GAME_DELIMITER, SET_DELIMITER)
            lineList.add(line)
        }
        var powerSum = 0
        for (line in lineList) {
            powerSum += checkGameV2(line)
        }
        return powerSum
    }

    private fun checkGameV2(line: List<String>): Int {
        val gameResult = newMap()
        for (set in line) {
            if (isSet(set)) {
                setNumber(checkSetV2(set), gameResult)
            }
        }
        println(gameResult.entries.joinToString(" | "))
        return multiply(gameResult)
    }

    private fun newMap(): MutableMap<String, Int> {
        val gameResult = HashMap<String, Int>()
        gameResult[RED_COLOR] = DEFAULT_NUMBER
        gameResult[GREEN_COLOR] = DEFAULT_NUMBER
        gameResult[BLUE_COLOR] = DEFAULT_NUMBER
        return gameResult
    }

    private fun isSet(set: String): Boolean {
        return !set.startsWith(GAME_PREFIX)
    }

    private fun setNumber(
        checkSet: Map<String, Int>,
        gameResult: MutableMap<String, Int>
    ) {
        for (color in gameResult.keys) {
            val checkNumber = checkSet.getOrDefault(color, DEFAULT_NUMBER)
            if (checkNumber > gameResult.getOrDefault(color, DEFAULT_NUMBER))
                gameResult[color] = checkNumber
        }
    }

    private fun multiply(gameResult: Map<String, Int>): Int {
        var multi = 1
        for (color in gameResult.keys) {
            multi *= gameResult.getOrDefault(color, 0)
        }
        println(multi)
        return multi
    }

    private fun checkSetV2(set: String): Map<String, Int> {
        val cubesLine = set.split(CUBES_DELIMITER)
        val cubesMap = HashMap<String, Int>()
        for (cubeLine in cubesLine) {
            val cube = checkCubeV2(cubeLine)
            cubesMap[cube.color] = cube.number
        }
        return cubesMap
    }

    private fun checkCubeV2(cube: String): Cube {
        val cubeInfo = cube.split(NUMBER_DELIMITER)
        return if (cubeInfo.size == 2) {
            val number = cubeInfo[0].toInt()
            val color = cubeInfo[1]
            Cube(color, number)
        } else Cube(DEFAULT_COLOR, DEFAULT_NUMBER)
    }
}