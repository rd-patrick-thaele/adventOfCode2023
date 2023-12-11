import kotlin.math.absoluteValue

class Day11 {

    companion object {
        private const val GALAXY_SYMBOL = '#'
        private const val EMPTY_SPACE_SYMBOL = '.'
    }

    fun sumOfGalaxyDistances(universe: List<String>): Int {
        var sumOfDistances = 0
        val galaxies = findGalaxies(expandUniverse(universe)).toMutableList()

        while (galaxies.isNotEmpty()) {
            val galaxy = galaxies.removeFirst()!!

            for (other in galaxies) {
                sumOfDistances += galaxy.distance(other)
            }
        }

        return sumOfDistances
    }

    fun expandUniverse(universe: List<String>): List<String> {
        val expandedUniverseVertically = mutableListOf<String>()
        val universeWidth = universe.first().length
        val xIndexesForExpansion = (0 until universeWidth).toMutableList()

        for (line in universe) {
            expandedUniverseVertically.add(line)

            val foundGalaxies = mutableSetOf<Int>()
            for ((index, point) in line.withIndex()) {
                if (point == GALAXY_SYMBOL)
                    foundGalaxies.add(index)
            }

            if (foundGalaxies.isEmpty())
                expandedUniverseVertically.add(EMPTY_SPACE_SYMBOL.toString().repeat(universeWidth))

            xIndexesForExpansion.removeAll(foundGalaxies)
        }

        val expandedUniverseHorizontally = mutableListOf<String>()
        for (line in expandedUniverseVertically) {
            val updatedLine = line.toMutableList()

            for (emptySpaceIndex in xIndexesForExpansion.reversed()) {
                updatedLine.add(emptySpaceIndex, EMPTY_SPACE_SYMBOL)
            }

            expandedUniverseHorizontally.add(updatedLine.joinToString(""))
        }

        return expandedUniverseHorizontally
    }

    fun findGalaxies(universe: List<String>): List<GalaxyPosition> {
        val galaxies = mutableListOf<GalaxyPosition>()

        for ((y, line) in universe.withIndex()) {
            for ((x, symbol) in line.withIndex()) {
                if (symbol == GALAXY_SYMBOL) {
                    galaxies.add(GalaxyPosition(y, x))
                }
            }
        }

        return galaxies
    }
}

data class GalaxyPosition(val y: Int, val x:Int) {
    fun distance(other: GalaxyPosition): Int {
        return (y - other.y).absoluteValue + (x - other.x).absoluteValue
    }
}