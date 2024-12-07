fun main() {

    fun part1(input: List<String>): Int {
        val splitIndex = input.indexOfFirst { it.isEmpty() }

        val rules = input.slice(0..<splitIndex).map {
            val (x, y) = it.split("|").map { it.toInt() }
            Rule(x, y)
        }

        val precedenceMap = mutableMapOf<Int, MutableSet<Int>>()
        rules.forEach{
            precedenceMap.computeIfAbsent(it.before) { mutableSetOf() }.add(it.after)
        }

        val updates = input.slice(splitIndex + 1..input.lastIndex).map { it.split(",").map { it.toInt() } }

        fun isValidOrder(update: List<Int>): Boolean {
            val indexMap = update.withIndex().associate { it.value to it.index }
            for ((x, ySet) in precedenceMap) {
                if (x in indexMap) {
                    for (y in ySet) {
                        if (y in indexMap && indexMap[x]!! > indexMap[y]!!) {
                            return false
                        }
                    }
                }
            }
            return true
        }

        val filtered = updates
            .filter { isValidOrder(it) } // Keep only valid updates

        val sum = filtered.sumOf { it[(it.size - 1) / 2] }

        return sum
    }

    fun part2(input: List<String>): Int {
        return input.size
    }


    val testInput = readInput("Day05_test")
    val part1 = part1(testInput)
    part1.println()
    check(part1 == 143)
//    val part2 = part2(testInput)
//    part2.println()
//    check(part2 == 123)

    val input = readInput("Day05")
//    3213 too high
    part1(input).println()
//    part2(input).println()
}

data class Rule(val before: Int, val after: Int)
