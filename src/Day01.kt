fun main() {
    fun part1(input: List<String>): Int {
        var maxCalories = 0
        var currCalories = 0

        input.forEach {
            val foodCal = it.toIntOrNull()
            if (foodCal != null) {
                currCalories += foodCal
            } else {
                maxCalories = maxOf(currCalories, maxCalories)
                currCalories = 0
            }
        }

        return maxCalories
    }

    fun part2(input: List<String>): Int {
        val allCalories = mutableListOf<Int>()
        var currCalories = 0

        input.forEach {
            val foodCal = it.toIntOrNull()
            if (foodCal != null) {
                currCalories += foodCal
            } else {
                allCalories.add(currCalories)
                currCalories = 0
            }
        }

        return allCalories.sortedDescending().take(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

