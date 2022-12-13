fun main() {
    fun part1(input: List<String>): String {
        val crateStacks = populateCrates()

        input.subList(10, input.size).forEach{
            performMove(it, crateStacks)
        }

        var result = ""
        for (stack in crateStacks) {
            result += stack.removeLast()
        }

        return result
    }

    fun part2(input: List<String>): String {
        val crateStacks = populateCrates()

        input.subList(10, input.size).forEach{
            performUpdatedMove(it, crateStacks)
        }

        var result = ""
        for (stack in crateStacks) {
            result += stack.removeLast()
        }

        return result
    }

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

fun printCrates(crateStacks: List<ArrayDeque<Char>>) {
    crateStacks.forEach {
        println(it)
    }
}

fun populateCrates(): List<ArrayDeque<Char>> {
    val crates = mutableListOf<ArrayDeque<Char>>()
    crates.add(ArrayDeque(listOf('D', 'B', 'J', 'V')))
    crates.add(ArrayDeque(listOf('P', 'V', 'B', 'W', 'R', 'D', 'F')))
    crates.add(ArrayDeque(listOf('R', 'G', 'F', 'L', 'D', 'C', 'W', 'Q')))
    crates.add(ArrayDeque(listOf('W', 'J', 'P', 'M', 'L', 'N', 'D', 'B')))
    crates.add(ArrayDeque(listOf('H', 'N', 'B', 'P', 'C', 'S', 'Q')))
    crates.add(ArrayDeque(listOf('R', 'D', 'B', 'S', 'N', 'G')))
    crates.add(ArrayDeque(listOf('Z', 'B', 'P', 'M', 'Q', 'F', 'S', 'H')))
    crates.add(ArrayDeque(listOf('W', 'L', 'F')))
    crates.add(ArrayDeque(listOf('S', 'V', 'F', 'M', 'R')))
    return crates
}

fun performMove(input: String, crates: List<ArrayDeque<Char>>) {
    val inputs = input.split(" ")
    val numCrates = inputs[1].toInt()
    val src = inputs[3].toInt() - 1
    val dst = inputs[5].toInt() -1

    for (i in 1..numCrates) {
        val crate = crates[src].removeLast()
        crates[dst].addLast(crate)
    }
}

fun performUpdatedMove(input: String, crates: List<ArrayDeque<Char>>) {
    var index = input.indexOf(' ') + 1
    val numCrates = input.substring(index, index + input.substring(index).indexOf(' ')).toInt()
    index = input.indexOf(' ', input.indexOf("from")) + 1
    val src = input[index].digitToInt() - 1
    index = input.indexOf(' ', input.indexOf("to")) + 1
    val dst = input[index].digitToInt() -1

    val tmpCrates = ArrayDeque<Char>()
    for (i in 1..numCrates) {
        tmpCrates.addLast(crates[src].removeLast())
    }
    for (i in 1..numCrates) {
        crates[dst].addLast(tmpCrates.removeLast())
    }
}
