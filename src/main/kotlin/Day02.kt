class Day02 {
    fun getSumOfPossibleIds(gameRecords: List<String>): Int {
        val maxCubeSet = CubeSet(14, 12, 13)

        return gameRecords.map { CubeGame.readGameRecord(it) }
            .filter { maxCubeSet.blues >= it.getMaxBlues() }
            .filter { maxCubeSet.reds >= it.getMaxReds() }
            .filter { maxCubeSet.greens >= it.getMaxGreens() }
            .map { it.id }
            .sum()
    }

    fun getSumOfPowersOfMinimumSets(gameRecords: List<String>): Int {
        return gameRecords.map { CubeGame.readGameRecord(it) }
            .map { it.getMinPossibleSet() }
            .map { it.power() }
            .sum()
    }

}

data class CubeGame(val id: Int, val cubeSets: List<CubeSet>) {
    fun getMaxReds(): Int {
        return cubeSets.map { it.reds }.max()
    }

    fun getMaxBlues(): Int {
        return cubeSets.map { it.blues }.max()
    }

    fun getMaxGreens(): Int {
        return cubeSets.map { it.greens }.max()
    }

    fun getMinPossibleSet(): CubeSet {
        return CubeSet(getMaxBlues(), getMaxReds(), getMaxGreens())
    }

    companion object {
        private val gameIdRegex = "Game ([0-9]+)".toRegex()
        private val nbOfCubesRegex = "([0-9]+) (red|blue|green)".toRegex()
        fun readGameRecord(gameRecord: String): CubeGame{
            val splitGameIdFromRecord = gameRecord.split(":")
            val (gameId) = gameIdRegex.find(splitGameIdFromRecord[0])!!.destructured

            val cubeSets = readCubeSets(splitGameIdFromRecord[1])

            return CubeGame(gameId.toInt(), cubeSets)
        }

        private fun readCubeSets(record: String): List<CubeSet> {
            val splitCubeSets = record.split(";")
            val cubeSets = mutableListOf<CubeSet>()

            for (cubeSetRecord in splitCubeSets) {
                cubeSets.add(readSingleCubeSet(cubeSetRecord))
            }

            return cubeSets.toList()
        }

        private fun readSingleCubeSet(cubeSetRecord: String): CubeSet {
            var blues = 0
            var reds = 0
            var greens = 0

            val cubeSet = cubeSetRecord.split(",")
            for (cubes in cubeSet) {
                val (nbOfCubes, color) = nbOfCubesRegex.find(cubes)!!.destructured

                when (color) {
                    "blue" -> blues = nbOfCubes.toInt()
                    "red" -> reds = nbOfCubes.toInt()
                    "green" -> greens = nbOfCubes.toInt()
                }
            }

            return CubeSet(blues, reds, greens)
        }
    }

}

data class CubeSet(val blues: Int, val reds: Int, val greens: Int) {
    fun power(): Int {
        return blues * reds * greens
    }
}