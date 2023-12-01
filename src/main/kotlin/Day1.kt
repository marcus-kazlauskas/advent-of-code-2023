import java.io.File
import java.util.LinkedList
import java.util.Scanner
import kotlin.io.path.Path

object Day1 {
    private const val MIN_DIGIT = 0
    private const val MAX_DIGIT = 9

    fun count(): Int {
        val path = MAIN_INPUT_PATH.format("Day1")
        return count(path)
    }

    fun count(path: String): Int {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        var sum = 0
        while (scanner.hasNext()) {
            sum += readNumber(scanner)
        }
        return sum
    }

    private fun readNumber(scanner: Scanner): Int {
        val line = scanner.nextLine()
//        println(line)
        val chars = line.chars()
            .map(fun(x: Int): Int {
                return x - '0'.code
            }).toArray()
//        println(chars.contentToString())
        val digits = LinkedList<Int>()
        for (char: Int in chars) {
            if (isDigit(char)) digits.add(char)
        }
        val number = digits.first * 10 + digits.last
//        println(number)
        return number
    }

    private fun isDigit(char: Int): Boolean {
        return char in MIN_DIGIT..MAX_DIGIT
    }
}