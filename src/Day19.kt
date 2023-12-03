fun main() {
    fun part1(input: List<String>): Int {
        val blueprints = mutableListOf<RobotFactory>()
        input.forEach {
            val robotFactory = RobotFactory()
            robotFactory.addBlueprint(it)
            blueprints.add(robotFactory)
        }

        val results = mutableListOf<Result>()
        blueprints.forEach {
            val result = Result(it)
            result.robots.add(Robot.OreRobot)
            println("=========================")
            println("Robots: " + result.robots)
            println("Resources: " + result.availableResources)
            println("=========================")
            repeat(24) {
                println("Minute " + (it+1))
                result.manufacture()
            }
            results.add(result)
        }

        var sum = 0
        for (i in results.indices) {
            val numGeode = results[i].availableResources[ResourceType.Geode] ?: 0
            sum += numGeode * (i + 1)
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
     val testInput = readInput("Day19_test")
     println(part1(testInput))

//    val input = readInput("Day19")
//    println(part1(input))
//    println(part2(input))
}

enum class ResourceType {
    Ore, Clay, Obsidian, Geode
}

enum class Robot(val resource: ResourceType) {
    OreRobot(ResourceType.Ore),
    ClayRobot(ResourceType.Clay),
    ObsidianRobot(ResourceType.Obsidian),
    GeodeRobot(ResourceType.Geode)
}

class RobotFactory {
    private val blueprint = mutableMapOf<Robot, Map<ResourceType, Int>>()

    fun addBlueprint(input: String) {
        var cost = mutableMapOf<ResourceType, Int>()
        var substring = input.substringAfter("ore robot costs ")
        cost[ResourceType.Ore] = substring.substring(0, substring.indexOf(" ")).toInt()
        blueprint[Robot.OreRobot] = cost

        cost = mutableMapOf()
        substring = input.substringAfter("clay robot costs ")
        cost[ResourceType.Ore] = substring.substring(0, substring.indexOf(" ")).toInt()
        blueprint.put(Robot.ClayRobot, cost)

        cost = mutableMapOf()
        substring = input.substringAfter("obsidian robot costs ")
        cost[ResourceType.Ore] = substring.substring(0, substring.indexOf(" ")).toInt()
        substring = substring.substringAfter("and ")
        cost[ResourceType.Clay] = substring.substring(0, substring.indexOf(" ")).toInt()
        blueprint.put(Robot.ObsidianRobot, cost)

        cost = mutableMapOf()
        substring = input.substringAfter("geode robot costs ")
        cost[ResourceType.Ore] = substring.substring(0, substring.indexOf(" ")).toInt()
        substring = substring.substringAfter("and ")
        cost[ResourceType.Obsidian] = substring.substring(0, substring.indexOf(" ")).toInt()
        blueprint.put(Robot.GeodeRobot, cost)

        println("Blueprint: " + blueprint)
    }


    fun makeRobot(robotType: Robot, availableResources: MutableMap<ResourceType, Int>): Robot? {
        var readyToMake = true
        blueprint[robotType]!!.forEach { (resourceType, count) ->
            if ((availableResources[resourceType] ?: 0) < count) {
                readyToMake = false
            }
        }
        if (readyToMake) {
            blueprint[robotType]!!.forEach { (resourceType, count) ->
                availableResources[resourceType] = availableResources[resourceType]!! - count
            }
            return robotType
        }
        return null
    }
}

class Result(private val robotFactory: RobotFactory) {
    val robots = mutableListOf<Robot>()
    val availableResources = mutableMapOf<ResourceType, Int>()

    fun manufacture() {
        val newRobots = buildRobots()
        robots.forEach {
            val resCount = availableResources[it.resource] ?: 0
            availableResources[it.resource] = resCount + 1
        }
        robots.addAll(newRobots)
        println("Robots: " + robots)
        println("Resources: " + availableResources)
    }

    private fun buildRobots(): List<Robot> {
        val newRobots = mutableListOf<Robot>()
        Robot.values().forEach {
            robotFactory.makeRobot(it, availableResources)?.let { robot -> newRobots.add(robot) }
        }
        return newRobots
    }
}