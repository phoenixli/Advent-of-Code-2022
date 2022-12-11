fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0

        input.forEach{
            val wrongItem = findWrongItem(it)!!
            if (wrongItem.isLowerCase()) {
                sum += wrongItem - 'a' + 1
            } else if (wrongItem.isUpperCase()) {
                sum += wrongItem - 'A' + 27
            }
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        input.chunked(3).forEach{
            val badge = findBadge(it)!!
            if (badge.isLowerCase()) {
                sum += badge - 'a' + 1
            } else if (badge.isUpperCase()) {
                sum += badge - 'A' + 27
            }
        }
        return sum
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

fun findWrongItem(rucksack: String): Char? {
    val firstCompartment = rucksack.substring(0, rucksack.length / 2)
    val secondCompartment = rucksack.substring(rucksack.length / 2)

    val itemMap = mutableMapOf<Char, Int>()
    for (item in firstCompartment.iterator()) {
        val currQuantity = itemMap[item] ?: 0
        itemMap[item] = currQuantity + 1
    }

    for (item in secondCompartment.iterator()) {
        if (itemMap.containsKey(item)) {
            return item
        }
    }

    return null
}

fun findCommonItems(first: String, second: String): List<Char> {
    val common = mutableListOf<Char>()

    val itemMap = mutableMapOf<Char, Int>()
    for (item in first.iterator()) {
        val currQuantity = itemMap[item] ?: 0
        itemMap[item] = currQuantity + 1
    }

    for (item in second.iterator()) {
        if (itemMap.containsKey(item)) {
            common.add(item)
        }
    }

    return common
}

fun findBadge(elves: List<String>): Char? {
    val first = elves[0]
    val second = elves[1]
    var common = findCommonItems(first, second)

    val third = elves[2]
    common = findCommonItems(String(common.toCharArray()), third)

    return common.firstOrNull()
}
