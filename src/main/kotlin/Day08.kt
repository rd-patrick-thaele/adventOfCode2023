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

    fun countTotalStepsV2(input: List<String>): Int {
        var stepCount = 0

        val directions = input.removeFirst()
        input.removeFirst()

        val desertNetwork = parseDesertNetwork(input)
        var currentNodes = getAllNodesEndingWithA(desertNetwork)

        while (true) {
            for (step in directions) {
                stepCount++
                val nextNodes = mutableListOf<String>()
                var allNodesFoundDestination = true

                for (currentNode in currentNodes) {

                    val nextNode = when (step) {
                        MOVE_LEFT -> desertNetwork[currentNode]!!.left
                        MOVE_RIGHT -> desertNetwork[currentNode]!!.right
                        else -> ""
                    }
                    nextNodes.add(nextNode)

                    if (!nextNode.endsWith('Z')) allNodesFoundDestination = false
                }

                if (allNodesFoundDestination) return stepCount
                currentNodes = nextNodes
            }
        }
    }
}

data class DesertNode(val left: String, val right: String)