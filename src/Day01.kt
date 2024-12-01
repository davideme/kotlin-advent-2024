import kotlin.math.absoluteValue

fun main() {
    fun pair(input: List<String>): Pair<MutableList<Int>, MutableList<Int>> {
        val column0 = mutableListOf<Int>()
        val column1 = mutableListOf<Int>()
        input.forEach {
            val split = it.split("   ")

            val element0 = split[0].toInt()
            val i = column0.binarySearch(element0)
            if (i >= 0) column0.add(i, element0)
            else column0.add(-(i + 1), element0)

            val element1 = split[1].toInt()
            val j = column1.binarySearch(element1)
            if (j >= 0) column1.add(j, element1)
            else column1.add(-(j + 1), element1)
        }
        return Pair(column0, column1)
    }

    fun part1(input: List<String>): Int {
        val (column0, column1) = pair(input)

        val map = column0.zip(column1).map {
            (it.first - it.second).absoluteValue
        }


        return map.sum()
    }

    fun part2(input: List<String>): Int {
        val (column0, column1) = pair(input)

        val map = column0.map { elt0 ->
            val indexOfFirst = column1.indexOfFirst { it == elt0 }
            val similarity = if (indexOfFirst < 0) 0
            else {
                val indexOfLast = column1.indexOfLast { it == elt0 }
                (indexOfLast - indexOfFirst) + 1
            }
            elt0 * similarity
        }

        return map.sum()
    }

//    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)
//
//    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    part1(testInput).println()
    check(part1(testInput) == 11)
    part2(testInput).println()
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
