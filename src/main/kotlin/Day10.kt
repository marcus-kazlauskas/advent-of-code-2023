import java.io.File
import java.util.*
import kotlin.io.path.Path

object Day10 {
    const val VALUE = 1105

    fun count(): Int {
        val path = MAIN_INPUT_PATH.format("Day10")
        return count(path)
    }

    // TODO
    fun count(path: String): Int {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        println("Unfinished")
        return 0
    }
}