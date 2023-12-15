import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.io.File
import java.util.*
import kotlin.io.path.Path

class MainTest {
    @Test
    fun testDay1Count() {
        assertEquals(
            142,
            Day1.count(TEST_INPUT_PATH.format("Day1"))
        )
    }

    @Test
    fun testDay1CountV2() {
        assertEquals(
            281,
            Day1.countV2(TEST_INPUT2_PATH.format("Day1"))
        )
    }

    @Test
    fun testDay2Count() {
        assertEquals(
            8,
            Day2.count(TEST_INPUT_PATH.format("Day2"))
        )
    }

    @Test
    fun testDay2CountV2() {
        assertEquals(
            2286,
            Day2.countV2(TEST_INPUT_PATH.format("Day2"))
        )
    }

    @Test
    fun testDay3Count() {
        assertEquals(
            4361,
            Day3.count(TEST_INPUT_PATH.format("Day3"))
        )
    }

    @Test
    fun testDay4Count() {
        assertEquals(
            13,
            Day4.count(TEST_INPUT_PATH.format("Day4"))
        )
    }

    @Test
    fun testDay5Count() {
        assertEquals(
            35,
            Day5.count(TEST_INPUT_PATH.format("Day5"))
        )
    }

    @Test
    fun testDay5CountV2() {
        assertEquals(
            46,
            Day5.countV2(TEST_INPUT_PATH.format("Day5"))
        )
    }

    @Test
    fun testDay6Count() {
        assertEquals(
            288,
            Day6.count(TEST_INPUT_PATH.format("Day6"))
        )
    }

    @Test
    fun testDay7Count() {
        assertEquals(
            6440,
            Day7.count(TEST_INPUT_PATH.format("Day7"))
        )
    }

    @Test
    fun testDay8Count() {
        assertEquals(
            6,
            Day8.count(TEST_INPUT_PATH.format("Day8"))
        )
    }

    @Test
    fun testDay9Count() {
        assertEquals(
            114,
            Day9.count(TEST_INPUT_PATH.format("Day9"))
        )
    }

    @Test
    fun testDay10Count() {
        assertEquals(
            8,
            Day10.count(TEST_INPUT_PATH.format("Day10"))
        )
    }

    @Test
    fun testDay11Count() {
        assertEquals(
            374L,
            Day11.count(TEST_INPUT_PATH.format("Day11"))
        )
    }

    @Test
    fun testDay11CountV2_10() {
        assertEquals(
            1030L,
            Day11.countV2(TEST_INPUT_PATH.format("Day11"), 10)
        )
    }

    @Test
    fun testDay11CountV2_100() {
        assertEquals(
            8410L,
            Day11.countV2(TEST_INPUT_PATH.format("Day11"), 100)
        )
    }

    @Test
    fun testDay12Count() {
        assertEquals(
            21,
            Day12.count(TEST_INPUT_PATH.format("Day12"))
        )
    }

    private fun checkCountV2(path: String, isUnfolded: Boolean): Long {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        var sumOfAllPossibleArrangements = 0L
        while (scanner.hasNext()) {
            val linesPair = scanner.nextLine().split(' ')
            println(linesPair)
            val counter = Day12.Counter(linesPair)
            if (isUnfolded) counter.unfold(5)
            counter.checkV2()
            val validCount = counter.getValidCount()
            println(validCount)
            println()
            sumOfAllPossibleArrangements += validCount
        }
        return sumOfAllPossibleArrangements
    }

    @Test
    fun testDay12checkCountV2Folded() {
        assertEquals(
            21,
            checkCountV2(TEST_INPUT_PATH.format("Day12"), false)
        )
    }

    @Test
    fun testDay12CountV2() {
        assertEquals(
            525152,
            Day12.countV2(TEST_INPUT_PATH.format("Day12"))
        )
    }

    @Test
    fun testDay13Count() {
        assertEquals(
            405,
            Day13.count(TEST_INPUT_PATH.format("Day13"))
        )
    }
}