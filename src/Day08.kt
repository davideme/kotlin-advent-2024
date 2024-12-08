fun main() {
    data class Point(val frequency: Char, val position: Vec2)
    data class AntiNode(val position: Vec2)

    fun part1(input: List<String>): Int {
//        val matrix = input.mapIndexed { i, line -> line.toList().mapIndexed { j, freq -> Point(freq, i, j) } }
        val matrix = input.flatMapIndexed { i, line -> line.toList().mapIndexed { j, freq -> Point(freq, Vec2(i, j)) } }
        val range = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val antiNodes = emptyList<Vec2>().toMutableList()

        for (frequency in range) {
            val antennas = matrix.filter { p -> p.frequency == frequency }
            antennas.map { antenna ->
                antennas.forEach { other ->
                    if (antenna.position != other.position) {
                        val distance = antenna.position - other.position
                        val element = antenna.position + distance
                        antiNodes.add(element)
                    }
                }
            }
        }
        val filter = antiNodes.distinct().filter { it.x.toInt() in input.indices && it.y.toInt() in input.first().indices }

        return filter.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day08_test")
    val part1 = part1(testInput)
    part1.println()
    check(part1 == 14)
    val part2 = part2(testInput)
//    part2.println()
//    check(part2 == 11387L)

    val input = readInput("Day08")
//380 too high
    part1(input).println()
//    part2(input).println()
}
