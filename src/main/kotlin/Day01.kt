class Day01 {

    val regex = "([0-9]).+([0-9]?)".toRegex()

    fun sumOfCalibrationValues(calibrationDoc: List<String>): Int {
        return calibrationDoc.sumOf { getCalibrationValue(it) }
    }

    fun getCalibrationValue(calibrationString: String): Int {
        val numbersRange = (0..9).toList().map { it.toString() }
        val foundFirst = calibrationString.findAnyOf(numbersRange)
        val foundLast = calibrationString.findLastAnyOf(numbersRange)

        return foundFirst!!.second.toInt() * 10 + foundLast!!.second.toInt()
    }
}