
class Day08 {

    companion object {
        const val TARGET_NODE = "ZZZ"
        const val MOVE_LEFT = 'L'
        const val MOVE_RIGHT = 'R'
    }

    // NNN = (TNT, XDJ)
    private val desertNodeRegex = "([A-Z0-9]{3}) = \\(([A-Z0-9]{3}), ([A-Z0-9]{3})\\)".toRegex()

    fun countTotalSteps(input: List<String>): Int {
        var stepCount = 0
        var currentNode = "AAA"

        val directions = input.removeFirst()
        input.removeFirst()

        val desertNetwork = parseDesertNetwork(input)

        while (true) {
            for (step in directions) {
                stepCount++
                when (step) {
                    MOVE_LEFT -> currentNode = desertNetwork[currentNode]!!.left
                    MOVE_RIGHT -> currentNode = desertNetwork[currentNode]!!.right
                }

                if (currentNode == TARGET_NODE) return stepCount
            }
        }
    }

    fun parseDesertNetwork(desert: List<String>): Map<String, DesertNode> {
        val network = mutableMapOf<String, DesertNode>()

        for (node in desert) {
            val (key, left, right) = desertNodeRegex.find(node)!!.destructured

            network[key] = DesertNode(left, right)
        }

        return network.toMap()
    }

    fun getAllNodesEndingWithA(desertNetwork: Map<String, DesertNode>): List<String> {
        return desertNetwork.keys
            .filter { it.endsWith('A') }
            .toList()
    }

    fun countTotalStepsV2(input: List<String>): Long {
        var stepCount = 0L
        var allInSync = false

        val directions = input.removeFirst()
        input.removeFirst()

        val desertNetwork = parseDesertNetwork(input)
        val patterns = getAllNodesEndingWithA(desertNetwork)
            .map { getPattern(it, directions, desertNetwork) }
            .toList()

        while (!allInSync) {
            allInSync = true
            for (pattern in patterns) {
                while (pattern.stepsTaken() < stepCount) {

                    val stepsTaken = pattern.moveOn()

                    if (stepsTaken > stepCount) {
                        stepCount = stepsTaken
                        allInSync = false
                    }
                }
            }
        }

        return stepCount
    }

    fun getPattern(startNode: String, directions: String, desertNetwork: Map<String, DesertNode>): CompletionPattern {

        println("Get Pattern for startNode $startNode")

        var currentNode = startNode
        var stepsFirstFinish = 0
        var stepsToFinish = 0
        var firstFinishDone = false

        val loopSteps = mutableListOf<Int>()
        val directionIndexOnFinish = mutableSetOf<Int>()

        while (true) {

            for ((index, step) in directions.withIndex()) {
                stepsToFinish++

                val nextNode = when (step) {
                    MOVE_LEFT -> desertNetwork[currentNode]!!.left
                    MOVE_RIGHT -> desertNetwork[currentNode]!!.right
                    else -> { println("ERROR: $step"); "" }
                }

                currentNode = nextNode

                if (currentNode.endsWith('Z')) {
                    if (firstFinishDone) {
                        loopSteps.add(stepsToFinish)

                        if (directionIndexOnFinish.contains(index)) {
                            val pattern = CompletionPattern(stepsFirstFinish, loopSteps)
                            println(pattern)
                            return pattern
                        }

                        directionIndexOnFinish.add(index)
                    } else {
                        firstFinishDone = true
                        stepsFirstFinish = stepsToFinish
                    }

                    stepsToFinish = 0
                }
            }
        }

    }
}

data class DesertNode(val left: String, val right: String)

data class CompletionPattern(val stepsFirstFinish: Int, val loop: List<Int>) {

    private var stepsTaken = -1L
    private var initialStepsDone = false
    private var loopIndex = 0
    fun moveOn(): Long {
        if (!initialStepsDone) {
            stepsTaken = stepsFirstFinish.toLong()
            initialStepsDone = true
        } else {
            stepsTaken += loop[loopIndex]

            if (loopIndex + 1 < loop.size) loopIndex++
            else loopIndex = 0
        }

        return stepsTaken
    }

    fun stepsTaken(): Long {
        return stepsTaken
    }
}