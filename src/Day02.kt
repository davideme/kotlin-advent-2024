import kotlin.math.absoluteValue

fun main() {
    fun isLineSafe(numbers: List<Int>): Boolean {
        val diffs = numbers.zipWithNext { a, b -> a - b }
        return diffs.all { it in -3..3 } && (diffs.all { it > 0 } || diffs.all { it < 0 })
    }

    fun part1(input: List<String>): Int {
        val lines = input.map { it -> it.split(" ").map { it.toInt() } }
        val safe = lines.map { isLineSafe(it) }
        return safe.count { it }
    }

    fun part2(input: List<String>): Int {
        val lines = input.map { it -> it.split(" ").map { it.toInt() } }
        return lines.count { numbers ->
            numbers.indices.any {
                val skipped = numbers.toMutableList().apply { removeAt(it) }
                isLineSafe(skipped)
            }
        }
    }

// Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    part1(testInput).println()
    check(part1(testInput) == 2)
    part2(testInput).println()
    check(part2(testInput) == 5)

//     Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
////    702 too low
////    742 too high maybe
    part2(input).println()
}
