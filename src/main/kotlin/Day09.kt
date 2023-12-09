class Day09 {
    fun predictNextValue(history: List<Int>): OasisPrediction {
        if (history.none { it != 0 })
            return OasisPrediction(0, 0)

        val diffList = mutableListOf<Int>()
        for (index in 1 until history.size) {
            diffList.add(history[index] - history[index - 1])
        }

        val prediction = predictNextValue(diffList)
        return OasisPrediction(history.first() - prediction.pastValue, prediction.futureValue + history.last())
    }

    fun sumOfPredictedFutureValues(report: List<String>): Int {
        return report.map { it.split(" ").map { it.toInt() }.toList() }
            .sumOf { predictNextValue(it).futureValue }
    }

    fun sumOfPredictedPastValues(report: List<String>): Int {
        return report.map { it.split(" ").map { it.toInt() }.toList() }
            .sumOf { predictNextValue(it).pastValue }
    }
}

data class OasisPrediction(val pastValue: Int, val futureValue: Int)