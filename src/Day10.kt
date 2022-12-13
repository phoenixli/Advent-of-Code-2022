fun main() {
    fun part1(input: List<String>): Int {
        return sumSignalStrength(input)
    }

    fun part2(input: List<String>): Int {
        draw(input)
        return input.size
    }

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}

fun sumSignalStrength(input: List<String>): Int {
    val cycles = listOf(20, 60, 100, 140, 180, 220)
    var cycle = 0
    var x = 1
    var sum = 0

    input.forEach{
        if (it == "noop") {
            cycle++
            if (cycle in cycles) {
                sum += cycle * x
            }
        } else {
            cycle++
            if (cycle in cycles) {
                sum += cycle * x
            }
            cycle++
            if (cycle in cycles) {
                sum += cycle * x
            }
            x += it.split(" ")[1].toInt()
        }
    }

    return sum
}

fun draw(input: List<String>) {
    var cycle = 0
    var x = 1
    var currIdx = 0

    input.forEach{
        if (it == "noop") {
            cycle++
            currIdx = printChar(currIdx, x, cycle)
        } else {
            cycle++
            currIdx = printChar(currIdx, x, cycle)
            cycle++
            currIdx = printChar(currIdx, x, cycle)
            x += it.split(" ")[1].toInt()
        }
    }
}

fun printChar(currIdx: Int, x: Int, cycle: Int): Int {
    val newLineCycles = listOf(40, 80, 120, 160, 200, 240)
    var newIdx = currIdx
    if (currIdx == x - 1 || currIdx == x || currIdx == x + 1) {
        print('#')
        newIdx++
    } else {
        print('.')
        newIdx++
    }
    if (cycle in newLineCycles) {
        print('\n')
        newIdx = 0
    }
    return newIdx
}