fun main() {
    fun part1(input: List<String>): Int {
        return countProcessedChars(input.first(), 4)
    }

    fun part2(input: List<String>): Int {
        return countProcessedChars(input.first(), 14)
    }

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}

fun countProcessedChars(input: String, numUnique: Int): Int {
    var count = 0
    val charArray = input.toCharArray()

    run breaking@{
        for (i in 0..charArray.size - numUnique) {
            count++
            if (isUnique(charArray.sliceArray(i..i + numUnique - 1))) {
                // Found the unique sequence counting the next 3 char.
                count += numUnique - 1
                return@breaking
            }
        }
    }

    return count;
}

fun isUnique(charArray: CharArray): Boolean {
    val charMap = mutableMapOf<Char, Int>()
    charArray.forEach {
        val charCount = charMap[it] ?: 0
        if (charCount != 0) return false
        charMap[it] = charCount + 1
    }
    return true
}