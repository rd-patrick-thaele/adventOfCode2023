class Day09 {
    fun predictNextValue(history: List<Int>): Int {
        if (history.none { it != 0 })
            return 0

        val diffList = mutableListOf<Int>()
        for (index in 1 until history.size) {
            diffList.add(history[index] - history[index - 1])
        }

        return predictNextValue(diffList) + history.last()
    }

    fun sumOfPredictedNextValues(report: List<String>): Int {
        return report.map { it.split(" ").map { it.toInt() }.toList() }
            .sumOf { predictNextValue(it) }
    }
}