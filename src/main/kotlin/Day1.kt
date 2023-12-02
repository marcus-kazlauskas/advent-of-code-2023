import java.io.File
import java.util.LinkedList
import java.util.Scanner
import kotlin.io.path.Path

object Day1 {
    private const val MIN_DIGIT = 1
    private const val MAX_DIGIT = 9
    private const val EMPTY_DIGIT = -1

    private val DIGITS: Map<String, Int> = mapOf(
        "one"   to 1,
        "two"   to 2,
        "three" to 3,
        "four"  to 4,
        "five"  to 5,
        "six"   to 6,
        "seven" to 7,
        "eight" to 8,
        "nine"  to 9
    )

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

    fun countV2(): Int {
        val path = MAIN_INPUT_PATH.format("Day1")
        return countV2(path)
    }

    fun countV2(path: String): Int {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        var sum = 0
        while (scanner.hasNext()) {
            sum += readNumberV2(scanner)
        }
        return sum
    }

    //  дальше идёт говнокод прям как в Росреестре
    private fun readNumberV2(scanner: Scanner): Int {
        val line = scanner.nextLine()
        val emptyPos = line.length
        println(line)

        val chars = line.chars()
            .map(fun(x: Int): Int {
                return x - '0'.code
            }).toArray()
        val intDigits = LinkedList<Int>()
        val intDigitsPos = LinkedList<Int>()
        for (i in chars.indices) {
            val char = chars[i]
            if (isDigit(char)) {
                intDigits.add(char)
                intDigitsPos.add(i)
            }
        }
        val intDigitFirst =
            if (intDigits.isEmpty()) EMPTY_DIGIT
            else intDigits.first
        val intDigitFirstPos =
            if (intDigitsPos.isEmpty()) emptyPos
            else intDigitsPos.first
        val intDigitLast =
            if (intDigits.isEmpty()) EMPTY_DIGIT
            else intDigits.last
        val intDigitLastPos =
            if (intDigitsPos.isEmpty()) emptyPos
            else chars.size - 1 - intDigitsPos.last
        println("int: first=$intDigitFirst, pos=$intDigitFirstPos; last=$intDigitLast, pos=$intDigitLastPos")

        var stringDigitFirst = EMPTY_DIGIT
        var stringDigitFirstPos = emptyPos
        var stringDigitLast = EMPTY_DIGIT
        var stringDigitLastPos = emptyPos
        for (stringDigit in DIGITS.keys) {
            val result = line.split(stringDigit)
            val resultLengthFirst = result[0].length
            val resultLengthLast = result[result.size - 1].length
            if (stringDigitFirst == EMPTY_DIGIT || stringDigitFirstPos > resultLengthFirst) {
                stringDigitFirst = DIGITS[stringDigit]!!
                stringDigitFirstPos = resultLengthFirst
            }
            if (stringDigitLast == EMPTY_DIGIT || stringDigitLastPos > resultLengthLast) {
                stringDigitLast = DIGITS[stringDigit]!!
                stringDigitLastPos = resultLengthLast
            }
        }
        println("string: first=$stringDigitFirst, pos=$stringDigitFirstPos; last=$stringDigitLast, pos=$stringDigitLastPos")

        val first =
            if (intDigitFirstPos < stringDigitFirstPos) intDigitFirst
            else stringDigitFirst
        val last =
            if (intDigitLastPos < stringDigitLastPos) intDigitLast
            else stringDigitLast
        println("summary: first=$first; last=$last")

        return first * 10 + last
    }
}