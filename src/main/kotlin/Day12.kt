class Day12 {
    fun getTotalArrangements(records: List<String>): Int {
        return records.map { HotSpringsRecord.parse(it) }
            .map { it.generateArrangements() }
            .sumOf { it.size }
    }

    fun getTotalArrangementsUnfolded(records: List<String>): Int {
        return records.map { unfold(it) }
            .map { HotSpringsRecord.parse(it) }
            .map { it.generateArrangements() }
            .sumOf { it.size }
    }

    fun unfold(record: String): String {
        val (brokenRecord, groups) = record.split(" ")
        var unfoldedRecord = brokenRecord
        var unfoldedGroups = groups

        repeat(4){
            unfoldedRecord += HotSpringsRecord.SYMBOL_UNKNOWN + brokenRecord
            unfoldedGroups += ",$groups"
        }

        return "$unfoldedRecord $unfoldedGroups"
    }
}

data class HotSpringsRecord(val brokenRecord: String, val damagedGroupSizes: List<Int>) {
    companion object {

        const val SYMBOL_UNKNOWN = '?'
        private const val SYMBOL_DAMAGED = '#'
        private const val SYMBOL_OPERATIONAL = '.'
        fun parse(record: String): HotSpringsRecord {
            val (brokenRecord, groups) = record.split(" ")
            val damagedGroupSizes = groups.split(",").map { it.toInt() }

            return HotSpringsRecord(brokenRecord, damagedGroupSizes)
        }
    }

    fun generateArrangements(): List<String> {
        val unknowns = findUnknowns()
        val nbOfDamagedUnknowns = countDamagedUnknowns()

        val regex = generateValidationRegex()

        return permuteArrangements(nbOfDamagedUnknowns, unknowns.size)
            .map { replaceUnknowns(unknowns, it) }
            .filter { regex.matches(it) }
    }

    fun findUnknowns(): List<Int> {
        val unknowns = mutableListOf<Int>()

        for ((index, symbol) in brokenRecord.withIndex()) {
            if (symbol == SYMBOL_UNKNOWN) {
                unknowns.add(index)
            }
        }

        return unknowns.toList()
    }

    fun countDamagedUnknowns(): Int {
        val sumOfDamagedSprings = damagedGroupSizes.sum()
        val countOfKnownDamagedSprings = brokenRecord.toList().count { it == SYMBOL_DAMAGED }

        return sumOfDamagedSprings - countOfKnownDamagedSprings
    }

    private fun generateValidationRegex(): Regex {
        val regex =  "\\.*" + damagedGroupSizes.joinToString("\\.+") { "#{$it}" } + "\\.*"

        return regex.toRegex()
    }

    private fun permuteArrangements(nbOfDamagedUnknowns: Int, nbOfTotalUnknowns: Int): List<String> {
        if (nbOfDamagedUnknowns == 0)
            return listOf(SYMBOL_OPERATIONAL.toString().repeat(nbOfTotalUnknowns))

        if (nbOfDamagedUnknowns == nbOfTotalUnknowns)
            return listOf(SYMBOL_DAMAGED.toString().repeat(nbOfDamagedUnknowns))

        val prependOperational =  permuteArrangements(nbOfDamagedUnknowns, nbOfTotalUnknowns - 1)
            .map { SYMBOL_OPERATIONAL + it }
        val prependDamaged = permuteArrangements(nbOfDamagedUnknowns - 1, nbOfTotalUnknowns - 1)
            .map { SYMBOL_DAMAGED + it }

        return prependOperational + prependDamaged
    }

    private fun replaceUnknowns(unknowns: List<Int>, fillings: String): String {
        val symbols = brokenRecord.toMutableList()

        for ((i, index) in unknowns.withIndex()) {
            symbols[index] = fillings[i]
        }

        return symbols.joinToString("")
    }
}