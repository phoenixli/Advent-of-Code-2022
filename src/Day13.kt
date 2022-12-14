import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Int {
        var pairIdx = 0
        var idxSum = 0

        input.chunked(3).forEach{
            pairIdx++
            val isInOrder = isInOrder(toList(it[0]), toList(it[1]))
            if (isInOrder) {
                idxSum += pairIdx
            }
        }
        return idxSum
    }

    fun part2(input: List<String>): Int {
        val filteredList = input.filter{ it.isNotEmpty() }.toMutableList()
        filteredList.add("[[2]]")
        filteredList.add("[[6]]")
        val sortedList = filteredList.sortedWith{ a, b ->
            compare(toList(a), toList(b))
        }.asReversed()
        return (sortedList.indexOf("[[2]]") + 1) * (sortedList.indexOf("[[6]]") + 1)
    }

    // test if implementation meets criteria from the description, like:
//     val testInput = readInput("Day13_test")
//     println(part2(testInput))

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}

fun isInOrder(leftList: List<*>, rightList: List<*>): Boolean {
    return compare(leftList, rightList) > 0
}

fun compare(left: Any?, right: Any?): Int {
//    println("left: " + left)
//    println("right: " + right)
    if (left is Int && right is Int) {
        if (left > right) {
            return -1
        }
        if (left < right) {
            return 1
        }
        return 0
    }

    if (left is Int && right is List<*>) {
        return compare(listOf(left), right)
    }

    if (left is List<*> && right is Int) {
        return compare(left, listOf(right))
    }

    if (left is List<*> && right is List<*>) {
        val minSize = min(left.size, right.size)
        for (i in 0 until minSize) {
            val result = compare(left[i], right[i])
            if (result != 0) {
                return result
            }
        }
        return -left.size.compareTo(right.size)
    }

    return 0
}

fun toList(input: String): List<Any> {
    val grandList = mutableListOf<Any>()
    val parsedInput = input.drop(1).dropLast(1).split(",")
    val tmpStack: ArrayDeque<MutableList<Any>> = ArrayDeque()
    tmpStack.addLast(grandList)
    parsedInput.forEach{
        var stripped = it
        while (stripped.startsWith('[')) {
            val parentList = tmpStack.removeLast()
            val childList = mutableListOf<Any>()
            parentList.add(childList)
            tmpStack.addLast(parentList)
            tmpStack.addLast(childList)
            stripped = stripped.drop(1)
        }

        if (stripped.endsWith(']')) {
            val intString = stripped.substring(0, stripped.indexOf(']'))
            if (intString.isNotEmpty()) {
                val list = tmpStack.removeLast()
                list.add(intString.toInt())
                tmpStack.addLast(list)
            }
        } else {
            if (stripped.isNotEmpty()) {
                val list = tmpStack.removeLast()
                list.add(stripped.toInt())
                tmpStack.addLast(list)
            }
        }

        while (stripped.endsWith("]")) {
            tmpStack.removeLast()
            stripped = stripped.dropLast(1)
        }
    }

    return grandList
}