class Day10(val map: List<String>) {

    companion object {
        const val STARTING_SYMBOL = 'S'
        const val NORTH_SOUTH_SYMBOL = '|'
        const val WEST_EAST_SYMBOL = '-'
        const val NORTH_EAST_SYMBOL = 'L'
        const val NORTH_WEST_SYMBOL = 'J'
        const val SOUTH_EAST_SYMBOL = 'F'
        const val SOUTH_WEST_SYMBOL = '7'
    }

    private var startingPosition = PipePosition(-1, -1)
    fun findStartPosition() {
        for ((y, line) in map.withIndex()) {
            for ((x, symbol) in line.withIndex()) {
                if (symbol == STARTING_SYMBOL) {
                    startingPosition = PipePosition(y, x)
                    return
                }
            }
        }
    }

    fun stepsToOppositePosition(): Int {
        findStartPosition()
        var pipeDirection = findFirstPipeDirection()
        var nextPosition = startingPosition
        var stepsTaken = 0

        while (true) {
            stepsTaken++
            nextPosition = when(pipeDirection) {
                PipeDirection.NORTH -> PipePosition(nextPosition.y - 1, nextPosition.x)
                PipeDirection.EAST -> PipePosition(nextPosition.y, nextPosition.x + 1)
                PipeDirection.SOUTH -> PipePosition(nextPosition.y + 1, nextPosition.x)
                PipeDirection.WEST -> PipePosition(nextPosition.y, nextPosition.x - 1)
            }

            val symbol = map[nextPosition.y][nextPosition.x]
            if (symbol == STARTING_SYMBOL) {
                return stepsTaken / 2
            }

            pipeDirection = getNextPipeDirection(symbol, pipeDirection)
        }
    }

    private fun getNextPipeDirection(symbol: Char, prevDirection: PipeDirection): PipeDirection {

        if (prevDirection == PipeDirection.EAST) {
            return when (symbol) {
                WEST_EAST_SYMBOL -> PipeDirection.EAST
                NORTH_WEST_SYMBOL -> PipeDirection.NORTH
                else -> PipeDirection.SOUTH
            }
        }

        if (prevDirection == PipeDirection.SOUTH) {
            return when (symbol) {
                NORTH_EAST_SYMBOL -> PipeDirection.EAST
                NORTH_SOUTH_SYMBOL -> PipeDirection.SOUTH
                else -> PipeDirection.WEST
            }
        }

        if (prevDirection == PipeDirection.WEST) {
            return when (symbol) {
                SOUTH_EAST_SYMBOL -> PipeDirection.SOUTH
                NORTH_EAST_SYMBOL -> PipeDirection.NORTH
                else -> PipeDirection.WEST
            }
        }

        return when (symbol) {
            SOUTH_WEST_SYMBOL -> PipeDirection.WEST
            NORTH_SOUTH_SYMBOL -> PipeDirection.NORTH
            else -> PipeDirection.EAST
        }
    }

    fun getStartPosition(): PipePosition {
        return startingPosition
    }

    fun findFirstPipeDirection(): PipeDirection {

        val eastNeighbor = map[startingPosition.y][startingPosition.x + 1]
        if (eastNeighbor in listOf(WEST_EAST_SYMBOL, NORTH_WEST_SYMBOL, SOUTH_WEST_SYMBOL)) {
            return PipeDirection.EAST
        }

        val southNeighbor = map[startingPosition.y + 1][startingPosition.x]
        if (southNeighbor in listOf(NORTH_SOUTH_SYMBOL, NORTH_EAST_SYMBOL, NORTH_WEST_SYMBOL)) {
            return PipeDirection.SOUTH
        }

        return PipeDirection.WEST
    }
}

data class PipePosition(val y: Int, val x: Int)

enum class PipeDirection {
    NORTH, EAST, SOUTH, WEST
}