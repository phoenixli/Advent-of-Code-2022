fun main() {
    fun part1(input: List<String>): Long {
        val monkeys = mutableListOf<Monkey>()
        input.chunked(7).forEach {
            monkeys.add(Monkey.of(it))
        }

        repeat(20) {
            monkeys.forEach {
                it.inspect(monkeys)
            }
        }

        return monkeys.map { monkey -> monkey.numInspection }
            .sortedDescending()
            .take(2)
            .reduce { acc, it -> acc * it }
    }

    fun part2(input: List<String>): Long {
        val monkeys = mutableListOf<Monkey>()
        input.chunked(7).forEach {
            monkeys.add(Monkey.of(it))
        }

        val mod = monkeys.map { monkey -> monkey.divisibleBy }.reduce{ acc, it -> acc * it }

        repeat(10000) {
            monkeys.forEach {
                it.newInspection(mod, monkeys)
            }
        }

        return monkeys.map { monkey -> monkey.numInspection }
            .sortedDescending()
            .take(2)
            .reduce { acc, it -> acc * it }
    }

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}

class Monkey(
    val id: Int,
    val items: MutableList<Long>,
    val operation: (Long) -> Long,
    val divisibleBy: Long,
    val trueMonkeyId: Int,
    val falseMonkeyId: Int
    ) {
    var numInspection = 0L

    fun inspect(monkeys: List<Monkey>) {
        for (item in items) {
            numInspection++
            val worry = operation(item) / 3
            if (worry % divisibleBy == 0L) {
                monkeys[trueMonkeyId].items.add(worry)
            } else {
                monkeys[falseMonkeyId].items.add(worry)
            }
        }
        items.clear()
    }

    fun newInspection(mod: Long, monkeys: List<Monkey>) {
        for (item in items) {
            numInspection++
            val worry = operation(item) % mod
            if (worry % divisibleBy == 0L) {
                monkeys[trueMonkeyId].items.add(worry)
            } else {
                monkeys[falseMonkeyId].items.add(worry)
            }
        }
        items.clear()
    }

    companion object {
        fun of(input: List<String>): Monkey {
            val id = input[0].substringAfter(' ').dropLast(1).toInt()
            val items = mutableListOf<Long>()
            input[1].replace(" ", "").substringAfter(":").split(',').forEach {
                items.add(it.toLong())
            }
            val operationIdx = input[2].lastIndexOf(' ') - 1
            val operationStr = input[2].substring(operationIdx, operationIdx + 1)
            val secondOperand = input[2].substringAfterLast(' ')
            val operation: (Long) -> Long = when {
                secondOperand == "old" -> ({ it * it })
                else -> {
                    val value = secondOperand.toLong()
                    if (operationStr == "+") {
                        ({ it + value })
                    } else {
                        ({ it * value })
                    }
                }
            }
            val divisibleBy = input[3].substringAfterLast(' ').toLong()
            val trueMonkeyId = input[4].substringAfterLast(' ').toInt()
            val falseMonkeyId = input[5].substringAfterLast(' ').toInt()
            return Monkey(id, items, operation, divisibleBy, trueMonkeyId, falseMonkeyId)
        }
    }
}