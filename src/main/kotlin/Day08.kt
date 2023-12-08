class Day08 {

    companion object {
        const val TARGET_NODE = "ZZZ"
        const val MOVE_LEFT = 'L'
        const val MOVE_RIGHT = 'R'
    }

    // NNN = (TNT, XDJ)
    private val desertNodeRegex = "([A-Z]{3}) = \\(([A-Z]{3}), ([A-Z]{3})\\)".toRegex()

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
}

data class DesertNode(val left: String, val right: String)