import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

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
}