fun main() {
    fun part1(input: List<String>): Int {
        val rows: MutableList<MutableList<Int>> = mutableListOf()
        input.forEach{
            val row = mutableListOf<Int>()
            for(c in it.toCharArray()) {
                row.add(c.toString().toInt())
            }
            rows.add(row)
        }

        val cols = List(rows[0].size) { mutableListOf<Int>() }
        for (row in rows) {
            for (i in row.indices) {
                cols[i].add(row[i])
            }
        }

        var count = 0
        for (i in rows.indices) {
            if (i == 0 || i == (rows.size - 1)) {
                count += rows.size
                continue
            }

            val row = rows[i]
            for (j in row.indices) {
                if (j == 0 || j == (row.size - 1)) {
                    count++
                    continue
                }

                val tree = row[j]
                val leftMax = row.subList(0, j).maxOrNull() ?: 0
                val rightMax = row.subList(j+1, row.size).maxOrNull() ?: 0
                if (tree > leftMax || tree > rightMax) {
                    count++
                } else {
                    // Check column max
                    val col = cols[j]
                    val topMax = col.subList(0, i).maxOrNull() ?: 0
                    val bottomMax = col.subList(i+1, col.size).maxOrNull() ?: 0
                    if (tree > topMax || tree > bottomMax) {
                        count++
                    }
                }
            }
        }

        return count
    }

    fun part2(input: List<String>): Int {
        val rows: MutableList<MutableList<Int>> = mutableListOf()
        input.forEach{
            val row = mutableListOf<Int>()
            for(c in it.toCharArray()) {
                row.add(c.toString().toInt())
            }
            rows.add(row)
        }

        val cols = List(rows[0].size) { mutableListOf<Int>() }
        for (row in rows) {
            for (i in row.indices) {
                cols[i].add(row[i])
            }
        }

        var maxScore = 0
        var currScore = 0
        for (i in rows.indices) {
            currScore = 0
            if (i == 0 || i == (rows.size - 1)) {
                continue
            }

            val row = rows[i]
            for (j in row.indices) {
                if (j == 0 || j == (row.size - 1)) {
                    continue
                }

                val tree = row[j]
                val left = row.subList(0, j).asReversed()
                val leftScore = getScoreForTree(tree, left)
                val right = row.subList(j+1, row.size)
                val rightScore = getScoreForTree(tree, right)
                val col = cols[j]
                val top = col.subList(0, i).asReversed()
                val topScore = getScoreForTree(tree, top)
                val bottom = col.subList(i+1, col.size)
                val bottomScore = getScoreForTree(tree, bottom)
                currScore = leftScore * rightScore * topScore * bottomScore
                maxScore = maxOf(maxScore, currScore)
            }

        }

        return maxScore
    }

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

fun getScoreForTree(tree: Int, list: List<Int>): Int {
    var score = 0
    for (it in list) {
        if (it >= tree) {
            score += 1
            return score
        }
        if (it < tree) {
            score += 1
        }
    }
    return score
}
