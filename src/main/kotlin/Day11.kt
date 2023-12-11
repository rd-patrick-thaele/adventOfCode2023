import kotlin.math.absoluteValue

class Day11 {

    companion object {
        private const val GALAXY_SYMBOL = '#'
        private const val EMPTY_SPACE_SYMBOL = '.'
    }

    fun sumOfGalaxyDistances(universe: List<String>, expansionFactor:Int = 2): Long {
        var sumOfDistances = 0L
        val galaxies = findGalaxies(universe).toMutableList()
        val (yIndexes, xIndexes) = getExpansionIndexes(universe)
        println("Y Indexes: $yIndexes")
        println("X Indexes: $xIndexes")

        while (galaxies.isNotEmpty()) {
            val galaxy = galaxies.removeFirst()!!

            for (other in galaxies) {
                var distance = galaxy.distance(other)

                val rangeY = if (other.y > galaxy.y) galaxy.y..other.y else other.y..galaxy.y
                val countYExpanses = yIndexes.intersect(rangeY).size
                distance += (expansionFactor - 1) * countYExpanses

                val rangeX = if(other.x > galaxy.x) galaxy.x..other.x else other.x..galaxy.x
                val countXExpanses = xIndexes.intersect(rangeX).size
                distance += (expansionFactor - 1) * countXExpanses

                sumOfDistances += distance
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

    fun getExpansionIndexes(universe: List<String>): Pair<List<Int>, List<Int>> {
        val yIndexes = mutableListOf<Int>()
        val xIndexes = (0 until universe.first().length).toMutableList()

        for ((yIndex, line) in universe.withIndex()) {

            val foundGalaxies = mutableSetOf<Int>()
            for ((xIndex, point) in line.withIndex()) {
                if (point == GALAXY_SYMBOL)
                    foundGalaxies.add(xIndex)
            }

            if (foundGalaxies.isEmpty())
                yIndexes.add(yIndex)

            xIndexes.removeAll(foundGalaxies)
        }

        return Pair(yIndexes, xIndexes)
    }
}

data class GalaxyPosition(val y: Int, val x:Int) {
    fun distance(other: GalaxyPosition): Int {
        return (y - other.y).absoluteValue + (x - other.x).absoluteValue
    }
}