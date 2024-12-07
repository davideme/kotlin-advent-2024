fun main() {

    fun part1(input: List<String>): Long {
        val equations = input.map {
            val zipWithNext = it.split(": ").zipWithNext { a, b ->
                Pair(a.toLong(), b.split(" ").map(String::toLong))
            }.first()
            zipWithNext
        }

        fun generate(current: List<String>, remaining: Int, operators: List<String>): List<List<String>> {
            if (remaining == 0) return listOf(current)

            return operators.flatMap { op ->
                generate(current + op, remaining - 1, operators)
            }
        }

        val filter = equations.filter { tuple ->

            val combination = generate(emptyList(), tuple.second.size - 1, listOf("+", "*"))

            val units = combination.map { op ->
                val reduceIndexed = tuple.second.reduceIndexed { index, acc, i ->
                    if (index == 1) {
                        print(acc)
                    }

                    val pos = index - 1
                    val i1 = when {
                        index == 0 -> i // First element is the starting number
                        op[pos] == "*" -> {
                            print(" * ")
                            acc * i
                        }

                        op[pos] == "+" -> {
                            print(" + ")
                            acc + i
                        }

                        else -> acc // Skip non-operator cases
                    }
                    print(i)

                    i1
                }
                println()
                reduceIndexed
            }
            units.any { tuple.first.toLong() == it }
        }

        return filter.sumOf { it.first }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }


    val testInput = readInput("Day07_test")
    val part1 = part1(testInput)
    part1.println()
    check(part1 == 3749L)
//    val part2 = part2(testInput)
//    part2.println()
//    check(part2 == 123)

//    val input = readInput("Day07")
////    3213 too high
//    part1(input).println()
//    part2(input).println()
}
