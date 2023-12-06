import java.io.File
import java.util.*
import java.util.stream.Collectors
import kotlin.io.path.Path

object Day5 {
    const val VALUE = 949
    private const val SEEDS_HEADER = "seeds:"
    private val HEADER_REGEX = Regex(".* map:")
    private const val DESTINATION_INDEX = 0
    private const val SOURCE_INDEX = 1
    private const val RANGE_INDEX = 2

    class SuperMap() {
        private val mappingList = LinkedList<Array<Long>>()
        private var sorted = false

        fun add(newMapValues: List<String>) {
            val array = arrayOf(
                newMapValues[DESTINATION_INDEX].toLong(),
                newMapValues[SOURCE_INDEX].toLong(),
                newMapValues[RANGE_INDEX].toLong()
            )
            mappingList.add(array)
            sorted = false
        }

        fun getDestination(inputSource: Long): Long {
            val maxLowerKey = mappingList.stream()
                .filter { x ->
                    x[SOURCE_INDEX] <= inputSource
                }.collect(Collectors.toList())
                .maxByOrNull { x ->
                    x[SOURCE_INDEX]
                }
            return if (maxLowerKey.isNullOrEmpty()) {
                inputSource
            } else {
                val startRange = maxLowerKey[SOURCE_INDEX]
                val endRange = startRange + maxLowerKey[RANGE_INDEX]
                if (inputSource < endRange) {
                    val delta = inputSource - startRange
                    maxLowerKey[DESTINATION_INDEX] + delta
                } else {
                    inputSource
                }
            }
        }

        /*  c отсортированным массивом работает в четыре раза быстрее
            (сравнение происходило при выключенной печати) */
        fun getSource(inputDestination: Long): Long {
            if (!sorted) sortByDestination()
            var maxLowerKey = emptyArray<Long>()
            for (mapValue in mappingList) {
                if (mapValue[DESTINATION_INDEX] <= inputDestination) {
                    maxLowerKey = mapValue
                } else break
            }
            return if(maxLowerKey.isEmpty()) {
                inputDestination
            } else {
                val startRange = maxLowerKey[DESTINATION_INDEX]
                val endRange = startRange + maxLowerKey[RANGE_INDEX]
                if (inputDestination < endRange) {
                    val delta = inputDestination - startRange
                    maxLowerKey[SOURCE_INDEX] + delta
                } else {
                    inputDestination
                }
            }
        }

        private fun sortByDestination() {
            mappingList.sortBy { x ->
                x[DESTINATION_INDEX]
            }
            sorted = true
        }
    }

    fun count(): Long {
        val path = MAIN_INPUT_PATH.format("Day5")
        return count(path)
    }

    fun count(path: String): Long {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        val superMapsList = LinkedList<SuperMap>()
        val seeds = readSeedNumbers(scanner)
        while (scanner.hasNext()) {
            readBlock(scanner, superMapsList)
        }
        val locations = LinkedList<Long>()
        for (seed in seeds) {
            locations.add(getLocation(superMapsList, seed))
        }
        return locations.min()
    }

    private fun readSeedNumbers(scanner: Scanner): LinkedList<Long> {
        val line = scanner.nextLine().split(' ')
        val numbers = LinkedList<Long>()
        for (seed in line) {
            if (seed != SEEDS_HEADER) {
                numbers.add(seed.toLong())
            }
        }
        scanner.nextLine()
        return numbers
    }

    private fun readBlock(scanner: Scanner, superMapsList: LinkedList<SuperMap>) {
        var line = scanner.nextLine()
        val header = line
        val superMap = SuperMap()
        if (header.contains(HEADER_REGEX)) {
            while (scanner.hasNext()) {
                line = scanner.nextLine()
                if (line.equals("")) break
                val newMapValues = line.split(' ')
                superMap.add(newMapValues)
            }
            superMapsList.add(superMap)
        }
    }

    private fun getLocation(superMapsList: LinkedList<SuperMap>, seed: Long): Long {
        var link = seed
        for (superMap in superMapsList) {
            print("$link -> ")
            link = superMap.getDestination(link)
        }
        println(link)
        return link
    }

    fun countV2(): Long {
        val path = MAIN_INPUT_PATH.format("Day5")
        return countV2(path)
    }

    fun countV2(path: String): Long {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        val superMapsList = LinkedList<SuperMap>()
        val seeds = readSeedNumbers(scanner)
        while (scanner.hasNext()) {
            readBlock(scanner, superMapsList)
        }
        val seedPairs = onesToPairs(seeds)
        for (i in 0 until Int.MAX_VALUE) {
            val location = i.toLong()
            val seed = getSeed(superMapsList, location)
            if (checkSeed(seedPairs, seed)) return location
        }
        return -1L
    }

    private fun onesToPairs(ones: LinkedList<Long>): LinkedList<Pair<Long, Long>> {
        val pairs = LinkedList<Pair<Long, Long>>()
        while (containsPairs(ones)) {
            val start = ones.poll()
            val range = ones.poll()
            val pair = Pair(start, range)
            pairs.add(pair)
        }
        return pairs
    }

    private fun containsPairs(ones: LinkedList<Long>): Boolean {
        return ones.size > 1
    }

    private fun getSeed(superMapsList: LinkedList<SuperMap>, location: Long): Long {
        var link = location
        for (superMap in superMapsList.reversed()) {
            link = superMap.getSource(link)
        }
        // без печати работает в четыре раза быстрее!
//        println("$link <- .. <- $location")
        return link
    }

    private fun checkSeed(seedPairs: LinkedList<Pair<Long, Long>>, seedToCheck: Long): Boolean {
        for (seedPair in seedPairs) {
            val seed = seedPair.first
            val range = seedPair.second
            if (seedToCheck in seed until (seed + range)) return true
        }
        return false
    }
}