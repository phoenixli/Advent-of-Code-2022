fun main() {
    fun part1(input: List<String>): Int {
        var count = 0

        input.forEach{
            val assignments = it.split(",")
            if (isContained(assignments)) {
                count++
            }
        }

        return count
    }

    fun part2(input: List<String>): Int {
        var count = 0

        input.forEach{
            val assignments = it.split(",")
            if (isOverlapped(assignments)) {
                count++
            }
        }

        return count
    }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

fun isContained(assignments: List<String>): Boolean {
    var rangeStrings = assignments[0].split("-")
    val range1 = rangeStrings[0].toInt().rangeTo(rangeStrings[1].toInt())
    rangeStrings = assignments[1].split("-")
    val range2 = rangeStrings[0].toInt().rangeTo(rangeStrings[1].toInt())

    if (range1.first in range2 && range1.last in range2) {
        return true
    }

    if (range2.first in range1 && range2.last in range1) {
        return true
    }

    return false
}

fun isOverlapped(assignments: List<String>): Boolean {
    var rangeStrings = assignments[0].split("-")
    val range1 = rangeStrings[0].toInt().rangeTo(rangeStrings[1].toInt())
    rangeStrings = assignments[1].split("-")
    val range2 = rangeStrings[0].toInt().rangeTo(rangeStrings[1].toInt())

    if (range1.first in range2 || range1.last in range2) {
        return true
    }

    if (range2.first in range1 || range2.last in range1) {
        return true
    }

    return false
}