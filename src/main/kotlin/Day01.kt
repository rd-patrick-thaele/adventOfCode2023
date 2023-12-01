class Day01 {

    val calibrationValueMap = mapOf(Pair("0", 0), Pair("zero", 0),
        Pair("1", 1), Pair("one", 1),
        Pair("2", 2), Pair("two", 2),
        Pair("3", 3), Pair("three", 3),
        Pair("4", 4), Pair("four", 4),
        Pair("5", 5), Pair("five", 5),
        Pair("6", 6), Pair("six", 6),
        Pair("7", 7), Pair("seven", 7),
        Pair("8", 8), Pair("eight", 8),
        Pair("9", 9), Pair("nine", 9))

    fun sumOfCalibrationValues(calibrationDoc: List<String>): Int {
        return calibrationDoc.sumOf { getCalibrationValue(it) }
    }

    fun getCalibrationValue(calibrationString: String): Int {
        val numbersRange = (0..9).toList().map { it.toString() }
        val foundFirst = calibrationString.findAnyOf(numbersRange)
        val foundLast = calibrationString.findLastAnyOf(numbersRange)

        return foundFirst!!.second.toInt() * 10 + foundLast!!.second.toInt()
    }

    fun sumOfCalibrationValuesSpelled(calibrationDoc: List<String>): Int {
        return calibrationDoc.sumOf { getCalibrationValueSpelled(it) }
    }

    fun getCalibrationValueSpelled(calibrationString: String): Int {
        val numbersToBeFound = calibrationValueMap.keys

        val foundFirst = calibrationString.findAnyOf(numbersToBeFound)
        val foundLast = calibrationString.findLastAnyOf(numbersToBeFound)

        return calibrationValueMap.getValue(foundFirst!!.second) * 10 + calibrationValueMap.getValue(foundLast!!.second)
    }
}