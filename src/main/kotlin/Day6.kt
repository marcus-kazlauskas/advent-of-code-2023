import java.io.File
import java.util.*
import kotlin.io.path.Path
import kotlin.math.min

object Day6 {
    const val VALUE = 938

    class Race {
        private val races = LinkedList<Pair<Int, Int>>()
        private val numberOfWays = LinkedList<Int>()

        fun setData(scanner: Scanner) {
            val rawLineOfTimes = scanner.nextLine().split(Regex("Time:( )+"))
            val stringTimes = getLine(rawLineOfTimes)
            val rawLineOfDistances = scanner.nextLine().split(Regex("Distance:( )+"))
            val stringDistances = getLine(rawLineOfDistances)
            val numberOfRaces = min(stringTimes.size, stringDistances.size)
            for (i in 0 until numberOfRaces) {
                val pair = Pair(stringTimes[i].toInt(), stringDistances[i].toInt())
                races.add(pair)
            }
            println(races)
        }

        private fun getLine(rawLine: List<String>): List<String> {
            return rawLine[1].split(Regex("( )+"))
        }

        fun countNumOfWays() {
            for (p in races) {
                var count = 0
                val time = p.first
                val record = p.second
                for (t in 0..time) {
                    val distance = distance(time, t)
                    if (distance > record) count++
                }
                numberOfWays.add(count)
            }
            println(numberOfWays)
        }

        private fun distance(time: Int, charge: Int): Int {
            return (time - charge) * charge
        }

        fun multi(): Long {
            return numberOfWays.fold(1L) { multi, value -> multi * value }
        }
    }

    fun count(): Long {
        val path = MAIN_INPUT_PATH.format("Day6")
        return count(path)
    }

    fun count(path: String): Long {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        val race = Race()
        race.setData(scanner)
        race.countNumOfWays()
        return race.multi()
    }

    class RaceV2 {
        private var race = Pair(0L, 0L)
        var numberOfWays = 0L
            private set

        fun setData(scanner: Scanner) {
            val rawLineOfTime = scanner.nextLine().split(Regex("Time:( )+"))
            val stringTime = getLine(rawLineOfTime)
            val time = stringTime.joinToString("").toLong()
            val rawLineOfDistance = scanner.nextLine().split(Regex("Distance:( )+"))
            val stringDistance = getLine(rawLineOfDistance)
            val distance = stringDistance.joinToString("").toLong()
            race = Pair(time, distance)
            println(race)
        }

        private fun getLine(rawLine: List<String>): List<String> {
            return rawLine[1].split(Regex("( )+"))
        }

        fun countNumOfWays() {
            var charge = 0L
            while (distance(charge) <= race.second) {
                charge++
            }
            numberOfWays = race.first - 2 * charge + 1
        }

        private fun distance(charge: Long): Long {
            return (race.first - charge) * charge
        }
    }

    fun countV2(): Long {
        val path = MAIN_INPUT_PATH.format("Day6")
        return countV2(path)
    }

    fun countV2(path: String): Long {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        val race = RaceV2()
        race.setData(scanner)
        race.countNumOfWays()
        return race.numberOfWays
    }
}