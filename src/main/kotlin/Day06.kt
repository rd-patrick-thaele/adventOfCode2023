class Day06 {
    fun multiplyNbOfWins(races: List<Race>): Long {
        return races.map { it.getNumberOfWaysToWin() }
            .reduce { acc, it -> acc * it }
    }
}

data class Race(val time: Long, val record: Long) {
    fun getNumberOfWaysToWin(): Long {
        var nbOfBrokenRecords = 0L

        for (i in 1 until time) {
            val distance = (time - i) * i

            if (distance > record) {
                nbOfBrokenRecords++
            }
        }

        return nbOfBrokenRecords
    }
}