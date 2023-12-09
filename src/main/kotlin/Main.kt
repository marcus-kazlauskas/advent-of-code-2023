/**
 * https://adventofcode.com/2023
 *
 * .."""....."""
 * ."...."."...."
 * ."....."....."
 * .."........."
 * ..."......."
 * ....."..."
 * ......."
 * */

import java.util.Scanner

const val MAIN_INPUT_PATH = "./src/main/resources/%s/main_input.txt"
const val TEST_INPUT_PATH = "./src/main/resources/%s/test_input.txt"
const val TEST_INPUT2_PATH = "./src/main/resources/%s/test_input2.txt"

fun main() {
    print("Enter number:\n" +
            "(number of the day) * 2 - 1 for the first puzzle\n" +
            "(number of the day) * 2 for the second puzzle\n")
    val scanner = Scanner(System.`in`)
    when (scanner.nextInt()) {
        0   -> println(message())
        1   -> println(Day1.count())
        2   -> println(Day1.countV2())
        3   -> println(Day2.count())
        4   -> println(Day2.countV2())
        5   -> println(Day3.count())
//        6   -> println(Day3.countV2())
        7   -> println(Day4.count())
//        8   -> println(Day4.countV2())
        9   -> println(Day5.count())
        10  -> println(Day5.countV2())
        11  -> println(Day6.count())
//        12  -> println(Day6.countV2())
        13  -> println(Day7.count())
//        14  -> println(Day7.countV2())
        15  -> println(Day8.count())
//        16  -> println(Day8.countV2())
        17  -> println(Day9.count())
//        18  -> println(Day9.countV2())
//        19  -> println(Day10.count())
//        20  -> println(Day10.countV2())
//        21  -> println(Day11.count())
//        22  -> println(Day11.countV2())
//        23  -> println(Day12.count())
//        24  -> println(Day12.countV2())
//        25  -> println(Day13.count())
//        26  -> println(Day13.countV2())
//        27  -> println(Day14.count())
//        28  -> println(Day14.countV2())
//        29  -> println(Day15.count())
//        30  -> println(Day15.countV2())
//        31  -> println(Day16.count())
//        32  -> println(Day16.countV2())
//        33  -> println(Day17.count())
//        34  -> println(Day17.countV2())
//        35  -> println(Day18.count())
//        36  -> println(Day18.countV2())
//        37  -> println(Day19.count())
//        38  -> println(Day19.countV2())
//        39  -> println(Day20.count())
//        40  -> println(Day20.countV2())
//        41  -> println(Day21.count())
//        42  -> println(Day21.countV2())
//        43  -> println(Day22.count())
//        44  -> println(Day22.countV2())
//        45  -> println(Day23.count())
//        46  -> println(Day23.countV2())
//        47  -> println(Day24.count())
//        48  -> println(Day24.countV2())
//        49  -> println(Day25.count())
//        50  -> println(Day25.countV2())
        else    -> println("Puzzle not found!")
    }
}

fun message(): String {
    val list = listOf(
        Day1.VALUE,
        Day2.VALUE,
        Day3.VALUE,
        Day4.VALUE,
        Day5.VALUE,
        Day6.VALUE,
        Day7.VALUE,
        Day8.VALUE,
        Day9.VALUE
    )
    for (i in list.indices) {
        println("%4s".format(list[i]))
    }
    return "To be continued..."
}