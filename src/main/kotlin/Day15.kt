import java.io.File
import java.util.*
import kotlin.io.path.Path

object Day15 {
    const val VALUE = 1105

    private open class Hash {
        protected lateinit var initial: List<String>
        protected val result = LinkedList<Int>()

        fun set(scanner: Scanner) {
            initial = scanner.nextLine().split(',')
        }

        fun countHash(): Int {
            for (l in initial) {
                val currentValue = hash(l)
                result.add(currentValue)
            }
            return result.fold(0) { sum, value -> sum + value }
        }

        protected fun hash(line: String): Int {
            var currentValue = 0
            for (c in line) {
//                println(currentValue)
//                println("code = ${c.code}")
                currentValue = (currentValue + c.code) * 17 % 256
            }
//            println("$currentValue\n")
            return currentValue
        }
    }

    fun count(): Int {
        val path = MAIN_INPUT_PATH.format("Day15")
        return count(path)
    }

    fun count(path: String): Int {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        val hash = Hash()
        hash.set(scanner)
        return hash.countHash()
    }

    private class HashMap: Hash() {
        private val hashMap = HashMap<Int, LinkedList<Pair<String, Int>>>()

        private companion object {
            const val REMOVE = '-'
            const val SET = '='
        }

        fun box() {
            for (l in initial) {
                when {
                    l.contains(REMOVE)  -> {
                        remove(l)
                    }
                    l.contains(SET)     -> {
                        set(l)
                    }
                }
            }
            println(hashMap)
        }

        private fun remove(l: String) {
            val line = l.split(REMOVE)
            val label = line[0]
            val box = hash(label)
            val content = hashMap[box] ?: LinkedList<Pair<String, Int>>()
            for (i in 0 until content.size) {
                if (content[i].first == label) {
                    content.removeAt(i)
                    hashMap.remove(box)
                    hashMap[box] = content
                    break
                }
            }
        }

        private fun set(l: String) {
            val line = l.split(SET)
            val label = line[0]
            val box = hash(label)
            val focalLength = line[1].toInt()
            val content = hashMap[box] ?: LinkedList<Pair<String, Int>>()
            hashMap.remove(box)
            for (i in 0 until content.size) {
                if (content[i].first == label) {
                    content[i] = Pair(label, focalLength)
                    hashMap[box] = content
                    return
                }
            }
            content.add(Pair(label, focalLength))
            hashMap[box] = content
        }

        fun focusingPower(): Int {
            var power = 0
            for (e in hashMap.entries) {
                var boxPower = 0
                val boxNumber = e.key + 1
                for (i in 0 until e.value.size) {
                    boxPower += boxNumber * (i + 1) * e.value[i].second
                }
                power += boxPower
//                println(power)
            }
            return power
        }
    }

    fun countV2(): Int {
        val path = MAIN_INPUT_PATH.format("Day15")
        return countV2(path)
    }

    fun countV2(path: String): Int {
        val input = Path(path)
        val file = File(input.toUri())
        val scanner = Scanner(file)
        val hashMap = HashMap()
        hashMap.set(scanner)
        hashMap.box()
        return hashMap.focusingPower()
    }
}