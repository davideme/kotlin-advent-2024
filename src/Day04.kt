fun main() {
    fun horizontalCheck(
        wordSearch: List<List<Char>>,
        i: Int,
        j: Int,
    ): Boolean {
        var h = 1
        if (j + 3 >= wordSearch[i].size) {
            return false
        }
        for (letter in "MAS") {
            if (wordSearch[i][j + h] == letter) {
                //print(letter)
                h++
            } else {
                return false
            }
        }
        return true
    }

    fun verticalCheck(wordSearch: List<List<Char>>, i: Int, j: Int): Boolean {
        var h = 1
        if (i + 3 >= wordSearch.size) {
            return false
        }
        for (letter in "MAS") {
            if (wordSearch[i + h++][j] == letter) {
                //print(letter)
            } else {
                return false
            }
        }
        return true
    }

    // i:4 j:6
    fun backwardsHorCheck(wordSearch: List<List<Char>>, i: Int, j: Int): Boolean {
        var h = 1
        if (j - 3 < 0) {
            return false
        }
        for (letter in "MAS") {
            if (wordSearch[i][j - h++] == letter) {
                //print(letter)
            } else {
                return false
            }
        }
        return true
    }

    fun backwardsVerCheck(wordSearch: List<List<Char>>, i: Int, j: Int): Boolean {
        var h = 1
        if (i - 3 < 0) {
            return false
        }
        for (letter in "MAS") {
            if (wordSearch[i - h++][j] == letter) {
                //print(letter)
            } else {
                return false
            }
        }
        return true
    }

    fun diagonalLeftTopCheck(wordSearch: List<List<Char>>, i: Int, j: Int): Boolean {
        var h = 1
        if (i - 3 < 0 || j - 3 < 0) {
            return false
        }
        for (letter in "MAS") {
            if (wordSearch[i - h][j - h] == letter) {
                //print(letter)
            } else {
                return false
            }
            h++
        }
        return true
    }

    fun diagonalRightDownCheck(wordSearch: List<List<Char>>, i: Int, j: Int): Boolean {
        var h = 1
        if (i + 3 >= wordSearch.size || j + 3 >= wordSearch[i].size) {
            return false
        }
        for (letter in "MAS") {
            if (wordSearch[i + h][j + h] == letter) {
                //print(letter)
            } else {
                return false
            }
            h++
        }
        return true
    }

    fun diagonalRightTopCheck(wordSearch: List<List<Char>>, i: Int, j: Int): Boolean {
        var h = 1
        if (i + 3 >= wordSearch.size || j - 3 < 0) {
            return false
        }
        for (letter in "MAS") {
            if (wordSearch[i + h][j - h] == letter) {
                //print(letter)
            } else {
                return false
            }
            h++
        }
        return true
    }

    fun diagonalLeftDownCheck(wordSearch: List<List<Char>>, i: Int, j: Int): Boolean {
        var h = 1
        if (i - 3 < 0 || j + 3 >= wordSearch.size) {
            return false
        }
        for (letter in "MAS") {
            if (wordSearch[i - h][j + h] == letter) {
                //print(letter)
            } else {
                return false
            }
            h++
        }
        return true
    }

    fun part1(input: List<String>): Int {
        var xmasCounter = 0
        val wordSearch = input.map { it.toList() }
        wordSearch.forEachIndexed { i, line ->
            line.forEachIndexed { j, elt ->
                //XMAS
                if (elt == 'X') {
//                    print(elt)
                    // horizontal
                    if (horizontalCheck(wordSearch, i, j)) xmasCounter++
                    // vertical
                    if (verticalCheck(wordSearch, i, j)) xmasCounter++
                    // diagonal
                    if (diagonalRightDownCheck(wordSearch, i, j)) xmasCounter++
                    if (diagonalRightTopCheck(wordSearch, i, j)) xmasCounter++
                    // backwards
                    if (backwardsHorCheck(wordSearch, i, j)) xmasCounter++
                    if (backwardsVerCheck(wordSearch, i, j)) xmasCounter++
                    if (diagonalLeftTopCheck(wordSearch, i, j)) xmasCounter++
                    if (diagonalLeftDownCheck(wordSearch, i, j)) xmasCounter++
                }
            }
//            println()
        }
        return xmasCounter
    }

    fun masmasCheck(wordSearch: List<List<Char>>, i: Int, j: Int, p1: Char, p2: Char, p3: Char, p4: Char): Boolean {
        return wordSearch[i - 1][j - 1] == p1 &&
                wordSearch[i - 1][j + 1] == p2 &&
                wordSearch[i + 1][j - 1] == p3 &&
                wordSearch[i + 1][j + 1] == p4
    }

    fun part2(input: List<String>): Int {
        var xmasCounter = 0
        val wordSearch = input.map { it.toList() }
        wordSearch.forEachIndexed { i, line ->
            line.forEachIndexed { j, elt ->
                //XMAS
                if (elt == 'A') {
                    if (i - 1 >= 0 && j - 1 >= 0 && i + 1 < wordSearch.size && j + 1 < wordSearch[i].size) {
                        // search 4 variants
                        if (masmasCheck(wordSearch, i, j, 'M', 'M', 'S', 'S')) xmasCounter++
                        if (masmasCheck(wordSearch, i, j, 'S', 'S', 'M', 'M')) xmasCounter++
                        if (masmasCheck(wordSearch, i, j, 'M', 'S', 'M', 'S')) xmasCounter++
                        if (masmasCheck(wordSearch, i, j, 'S', 'M', 'S', 'M')) xmasCounter++
                    }
                }
            }
        }
        return xmasCounter
    }


// Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day04_test")
    part1(testInput).println()
    check(part1(testInput) == 18)
    part2(testInput).println()
    check(part2(testInput) == 9)

//     Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
//    3213 too high
    part1(input).println()
    part2(input).println()
}
