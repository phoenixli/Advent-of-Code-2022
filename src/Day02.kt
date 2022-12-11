fun main() {
    fun part1(input: List<String>): Int {
        var totalScore = 0

        input.forEach {
            totalScore += score(it[0], mapXyzToAbc(it[2]))
        }

        return totalScore
    }

    fun part2(input: List<String>): Int {
        var totalScore = 0

        input.forEach {
            totalScore += newStrat(it[0], it[2])
        }

        return totalScore
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

fun mapXyzToAbc(myPlay: Char): Char? {
    return when (myPlay) {
        'X' -> 'A'
        'Y' -> 'B'
        'Z' -> 'C'
        else -> null
    }
}

fun mapPlayToScore(play: Char): Int {
    return when (play) {
        'A' -> 1
        'B' -> 2
        'C' -> 3
        else -> 0
    }
}

fun rockA(otherPlay: Char): Int {
    return when (otherPlay) {
        'A' -> 3
        'B' -> 6
        'C' -> 0
        else -> 0
    }
}

fun paperB(otherPlay: Char): Int {
    return when (otherPlay) {
        'A' -> 0
        'B' -> 3
        'C' -> 6
        else -> 0
    }
}

fun scissorsC(otherPlay: Char): Int {
    return when (otherPlay) {
        'A' -> 6
        'B' -> 0
        'C' -> 3
        else -> 0
    }
}

fun score(firstPlayer: Char, secondPlayer: Char?): Int {
    if (secondPlayer == null) return 0
    val score = when (firstPlayer) {
        'A' -> rockA(secondPlayer)
        'B' -> paperB(secondPlayer)
        'C' -> scissorsC(secondPlayer)
        else -> 0
    }
    return score + mapPlayToScore(secondPlayer)
}

fun lose(play: Char): Char? {
    return when (play) {
        'A' -> 'C'
        'B' -> 'A'
        'C' -> 'B'
        else -> null
    }
}

fun win(play: Char): Char? {
    return when (play) {
        'A' -> 'B'
        'B' -> 'C'
        'C' -> 'A'
        else -> null
    }
}

fun newStrat(firstPlayer: Char, strat: Char): Int {
    val secondPlayer = when (strat) {
        'X' -> lose(firstPlayer)
        'Y' -> firstPlayer
        'Z' -> win(firstPlayer)
        else -> firstPlayer
    }
    if (secondPlayer == null) return 0
    return score(firstPlayer, secondPlayer)
}