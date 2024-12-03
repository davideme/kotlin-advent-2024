import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>): Int {
        val safe = mutableListOf<Boolean>()

        input.forEach { it ->
            val split = it.split(" ").map { it.toInt() }
            var isSafe = true
            val isIncreasing = split[0] < split[1]

            split.windowed(2).forEach { column ->
                val absoluteValue = (column.first() - column.last()).absoluteValue
                val isWindowIncreasing = column.first() < column.last()

                isSafe = isSafe && absoluteValue in 1..3 && (isIncreasing == isWindowIncreasing)
            }
            safe.add(isSafe)
        }

        return safe.filter { it }.size
    }

//    fun part2(input: List<String>): Int {
//        val safe = mutableListOf<Int>()
//
//        input.forEach { it ->
//            val split = it.split(" ").map { it.toInt() }
//
//            var errors = 0
//
//            split.windowed(3, 2, true).forEach { column ->
//                val first = column[0]
//                if (column.size > 1) {
//                    val second = column[1]
//                    val third = column.getOrNull(2)
//                    val isFirstWindowIncreasing = first < second
//                    val isSecondWindowIncreasing = if (third != null) second < third else isFirstWindowIncreasing
//                    if (isFirstWindowIncreasing != isSecondWindowIncreasing) {
//                        errors++
//                    }
//                    val isFirstAbsoluteValue = (first - second).absoluteValue in 1..3
//                    val isSecondAbsoluteValue = if (third != null) (second - third).absoluteValue in 1..3 else true
//                    val isFixedAbsoluteValue = if (third != null) (first - third).absoluteValue in 1..3 else true
//                    if (!isFirstAbsoluteValue && isSecondAbsoluteValue) {
//                        errors++
//                    }
//                    if ((!isFirstAbsoluteValue || !isSecondAbsoluteValue) && !isFixedAbsoluteValue) {
//                        errors++
//                    }
//                    if (!isSecondAbsoluteValue) {
//                        errors++
//                    }
//
//                }
//            }
//            if (errors > 1) {
////                println(split)
//            }
//            safe.add(errors)
//        }
//
//        return safe.filter { it in 0..1 }.size
//    }

    fun part2(input: List<String>): Int {
        val safe = mutableListOf<Int>()

        input.forEach { it ->
            val split = it.split(" ").map { it.toInt() }

            var errors = 0

            split.windowed(3).forEach { column ->
                val first = column[0]
                val second = column[1]
                val third = column[2]
                val isFirstWindowIncreasing = first < second
//                val isSecondWindowIncreasing = second < third
                val isThirdWindowIncreasing = first < third
                if (isFirstWindowIncreasing != isThirdWindowIncreasing) {
                    errors++
                }
                val isFirstAbsoluteValue = (first - second).absoluteValue in 1..3
                val isSecondAbsoluteValue = (second - third).absoluteValue in 1..3
                val isFixedAbsoluteValue = (first - third).absoluteValue in 1..3
                if (!isFirstAbsoluteValue && isSecondAbsoluteValue) {
                    errors++ // first is wrong
                    if (!isFixedAbsoluteValue) {
                        errors++
                    }
                } else if (!isFirstAbsoluteValue && isFixedAbsoluteValue) {
                    errors++ // second or third
                }
            }
            if (errors > 1) {
                println(split)
            }
            safe.add(errors)
        }

        return safe.filter { it in 0..1 }.size
    }

// Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    part1(testInput).println()
//    check(part1(testInput) == 2)
    part2(testInput).println()
    check(part2(testInput) == 5)

//     Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
//    part1(input).println()
////    702 too low
////    742 too high maybe
    part2(input).println()
}
