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
    private var startSymbolReplacement = '.'
    private val pipePositions = mutableSetOf<PipePosition>()

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
        pipePositions.add(startingPosition)
        var nextPosition = startingPosition
        var stepsTaken = 0

        while (true) {
            stepsTaken++

            nextPosition = when (pipeDirection) {
                PipeDirection.NORTH -> PipePosition(nextPosition.y - 1, nextPosition.x)
                PipeDirection.EAST -> PipePosition(nextPosition.y, nextPosition.x + 1)
                PipeDirection.SOUTH -> PipePosition(nextPosition.y + 1, nextPosition.x)
                PipeDirection.WEST -> PipePosition(nextPosition.y, nextPosition.x - 1)
            }
            pipePositions.add(nextPosition)

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

        var firstDirection:PipeDirection? = null

        val eastNeighbor = map[startingPosition.y][startingPosition.x + 1]
        if (eastNeighbor in listOf(WEST_EAST_SYMBOL, NORTH_WEST_SYMBOL, SOUTH_WEST_SYMBOL)) {
            firstDirection =  PipeDirection.EAST
        }

        val southNeighbor = map[startingPosition.y + 1][startingPosition.x]
        if (southNeighbor in listOf(NORTH_SOUTH_SYMBOL, NORTH_EAST_SYMBOL, NORTH_WEST_SYMBOL)) {
            if (firstDirection != null) {
                startSymbolReplacement = SOUTH_EAST_SYMBOL
                return firstDirection
            }
            firstDirection = PipeDirection.SOUTH
        }

        val westNeighbor = map[startingPosition.y][startingPosition.x - 1]
        if (westNeighbor in listOf(NORTH_EAST_SYMBOL, WEST_EAST_SYMBOL, SOUTH_EAST_SYMBOL)) {
            if (firstDirection == null) {
                startSymbolReplacement = NORTH_WEST_SYMBOL
                return PipeDirection.WEST
            }
            when(firstDirection) {
                PipeDirection.EAST -> startSymbolReplacement = WEST_EAST_SYMBOL
                PipeDirection.SOUTH -> startSymbolReplacement = SOUTH_WEST_SYMBOL
                else -> println("Won't reach this")
            }

            return firstDirection
        }

        when(firstDirection) {
            PipeDirection.EAST -> startSymbolReplacement = NORTH_EAST_SYMBOL
            PipeDirection.SOUTH -> startSymbolReplacement = NORTH_SOUTH_SYMBOL
            else -> println("Won't reach this")
        }

        return firstDirection!!
    }

    fun getEnclosedTiles(): Int {
        stepsToOppositePosition()
        var enclosedTiles = 0
        var edgeParity: Boolean
        val intersectionSymbols = mutableSetOf<Char>()

        val replacedString = map[startingPosition.y].replace('S', startSymbolReplacement)
        val updatedMap = map.toMutableList()
        updatedMap[startingPosition.y] = replacedString

        for ((y, line) in updatedMap.withIndex()) {
            edgeParity = true
            intersectionSymbols.clear()

            for ((x, symbol) in line.withIndex()) {

                if (pipePositions.contains(PipePosition(y, x))) {
                    if (symbol == WEST_EAST_SYMBOL) continue

                    //intersections: L-7, |, F-J
                    if (symbol in listOf(NORTH_EAST_SYMBOL, NORTH_WEST_SYMBOL, SOUTH_EAST_SYMBOL, SOUTH_WEST_SYMBOL)) {
                        intersectionSymbols.add(symbol)
                    }

                    if (symbol == NORTH_SOUTH_SYMBOL
                        || intersectionSymbols.containsAll(listOf(NORTH_EAST_SYMBOL, SOUTH_WEST_SYMBOL))
                        || intersectionSymbols.containsAll(listOf(SOUTH_EAST_SYMBOL, NORTH_WEST_SYMBOL))
                    ) {
                        edgeParity = !edgeParity
                        intersectionSymbols.clear()
                    }

                    if (intersectionSymbols.size > 1)
                        intersectionSymbols.clear()

                    continue
                }

                if (!edgeParity) {
                    enclosedTiles++
                }
            }
        }

        return enclosedTiles
    }
}

data class PipePosition(val y: Int, val x: Int)

enum class PipeDirection {
    NORTH, EAST, SOUTH, WEST
}