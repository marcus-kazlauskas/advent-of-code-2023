/**
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
    val scanner = Scanner(System.`in`)
    when (scanner.nextInt()) {
        1   -> println(Day1.count())
        2   -> println(Day1.countV2())
        3   -> println("Not prepared yet")
    }
}