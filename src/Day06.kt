fun main() {

    fun part1(input: List<String>): Int {
        val map = input.map {
            val zipWithNext = it.split(": ").zipWithNext { a, b ->
                Pair(a.toLong(), b.split(" ").map(String::toLong))
            }.first()
            zipWithNext
        }
        return input.size
    }

    val testInput = readInput("Day06_test")
    val part1 = part1(testInput)
    part1.println()
    check(part1 == 41)
}
