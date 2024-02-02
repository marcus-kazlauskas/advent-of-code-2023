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
    fun testDay3CountV2() {
        assertEquals(
            467835,
            Day3.countV2(TEST_INPUT_PATH.format("Day3"))
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
    fun testDay4CountV2() {
        assertEquals(
            30,
            Day4.countV2(TEST_INPUT_PATH.format("Day4"))
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
    fun testDay6CountV2() {
        assertEquals(
            71503,
            Day6.countV2(TEST_INPUT_PATH.format("Day6"))
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

    @Test
    fun testDay14Count() {
        assertEquals(
            136,
            Day14.count(TEST_INPUT_PATH.format("Day14"))
        )
    }

    @Test
    fun testDay15Count() {
        assertEquals(
            1320,
            Day15.count(TEST_INPUT_PATH.format("Day15"))
        )
    }

    @Test
    fun testDay16Count() {
        assertEquals(
            46,
            Day16.count(TEST_INPUT_PATH.format("Day16"))
        )
    }

    @Test
    fun testDay17Count() {
        assertEquals(
            102,
            Day17.count(TEST_INPUT_PATH.format("Day17"))
        )
    }

    @Test
    fun testDay18Count() {
        assertEquals(
            62,
            Day18.count(TEST_INPUT_PATH.format("Day18"))
        )
    }

    @Test
    fun testDay19Count() {
        assertEquals(
            19114,
            Day19.count(TEST_INPUT_PATH.format("Day19"))
        )
    }

    @Test
    fun testDay20Count() {
        assertEquals(
            11687500,
            Day20.count(TEST_INPUT_PATH.format("Day20"))
        )
    }

    @Test
    fun testDay21Count() {
        assertEquals(
            -1,
            Day21.count(TEST_INPUT_PATH.format("Day21"))
        )
    }

    @Test
    fun testDay22Count() {
        assertEquals(
            -1,
            Day22.count(TEST_INPUT_PATH.format("Day22"))
        )
    }

    @Test
    fun testDay23Count() {
        assertEquals(
            -1,
            Day23.count(TEST_INPUT_PATH.format("Day23"))
        )
    }

    @Test
    fun testDay24Count() {
        assertEquals(
            -1,
            Day24.count(TEST_INPUT_PATH.format("Day24"))
        )
    }

    @Test
    fun testDay25Count() {
        assertEquals(
            -1,
            Day25.count(TEST_INPUT_PATH.format("Day25"))
        )
    }
}