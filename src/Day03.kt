import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>): Int {
        val map = input.map { line ->
            val regex = """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()
            val groupValues = regex.findAll(line)
            val sum = groupValues.map { it.groupValues[1].toInt() * it.groupValues[2].toInt() }.sum()
            sum
        }
        return map.sum()
    }

    fun part2(input: List<String>): Int {
        var enabled = true
        val map = input.map { line ->
            val regex = """mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)""".toRegex()
            val groupValues = regex.findAll(line)
            val sum = mutableListOf<Int>()
            groupValues.forEach {
                val s = it.groupValues[0]
                when {
                    s == "do()" -> {
                        enabled = true
                    }
                    s == "don't()" -> {
                        enabled = false
                    }
                    s.startsWith("mul") && enabled-> {
                        sum.add(it.groupValues[1].toInt() * it.groupValues[2].toInt())
                    }
                }
            }
            sum.sum()
        }
        return map.sum()
    }

// Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day03_test")
    part1(testInput).println()
    check(part1(testInput) == 161)
    part2(testInput).println()
    check(part2(testInput) == 48)

//     Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
//  105264641  too high
    part2(input).println()
}
