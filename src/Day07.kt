fun main() {
    fun part1(input: List<String>): Int {
        val root = parseInput(input)
        return root.find { it.size <= 100000 }.sumOf { it.size }
    }

    fun part2(input: List<String>): Int {
        val root = parseInput(input)
        val unused = 70000000 - root.size
        val needToFree = 30000000 - unused
        return root.find { it.size >= needToFree }.minOf { it.size }
    }

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

class Directory(val name: String) {
    private val children = mutableListOf<Directory>()
    private var sizeOfFiles = 0

    val size: Int
        get() = sizeOfFiles + children.sumOf { it.size }

    fun addFile(size: Int) {
        sizeOfFiles += size
    }

    fun addDirectory(dir: Directory) {
        children.add(dir)
    }

    fun find(predicae: (Directory) -> Boolean): List<Directory> {
        return children.filter(predicae) + children.flatMap { it.find(predicae) }
    }
}

fun parseInput(input: List<String>): Directory {
    val directories = ArrayDeque<Directory>()
    directories.add(Directory("/"))
    input.forEach { cmd ->
        when {
            cmd == "$ cd /" -> directories.removeIf { it.name != "/" }
            cmd == "$ cd .." -> directories.removeFirst()
            cmd == "$ ls" -> {}
            cmd.startsWith("dir") -> {}
            cmd.startsWith("$ cd ") -> {
                val name = cmd.substringAfterLast(' ')
                val dir = Directory(name)
                directories.first().addDirectory(dir)
                directories.addFirst(dir)
            }
            else -> {
                val size = cmd.substringBefore(' ').toInt()
                directories.first().addFile(size)
            }
        }
    }
    return directories.last()
}