import java.io.File
import java.util.*
import kotlin.io.path.Path

object Day8 {
    const val VALUE = 940

    fun count(): Int {
        val path = MAIN_INPUT_PATH.format("Day8")
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