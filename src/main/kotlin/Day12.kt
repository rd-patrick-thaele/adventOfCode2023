class Day12 {
    fun getTotalArrangements(records: List<String>): Int {
        return records.map { HotSpringsRecord.parse(it) }
            .sumOf { it.getNbOfArrangements() }
    }

    fun getTotalArrangementsUnfolded(records: List<String>): Int {
        return records.map { unfold(it) }
            .map { HotSpringsRecord.parse(it) }
            .sumOf { it.getNbOfArrangements() }
    }

    fun unfold(record: String): String {
        val (brokenRecord, groups) = record.split(" ")
        var unfoldedRecord = brokenRecord
        var unfoldedGroups = groups

        repeat(4) {
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

        val arrangements = permuteArrangements(nbOfDamagedUnknowns, unknowns.size)
            .map { replaceUnknowns(unknowns, it) }
            .filter { regex.matches(it) }

        println(arrangements.size)

        return arrangements
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
        val regex = "\\.*" + damagedGroupSizes.joinToString("\\.+") { "#{$it}" } + "\\.*"

        return regex.toRegex()
    }

    private fun permuteArrangements(nbOfDamagedUnknowns: Int, nbOfTotalUnknowns: Int): List<String> {
        if (nbOfDamagedUnknowns == 0)
            return listOf(SYMBOL_OPERATIONAL.toString().repeat(nbOfTotalUnknowns))

        if (nbOfDamagedUnknowns == nbOfTotalUnknowns)
            return listOf(SYMBOL_DAMAGED.toString().repeat(nbOfDamagedUnknowns))

        val prependOperational = permuteArrangements(nbOfDamagedUnknowns, nbOfTotalUnknowns - 1)
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

    fun getPotentialGroups(): List<SpringGroup> {
        val groups = mutableListOf<SpringGroup>()
        var isMandatory = false
        var tempGroup = ""

        for (symbol in brokenRecord) {

            if (symbol == SYMBOL_OPERATIONAL && tempGroup.isNotEmpty()) {
                groups.add(SpringGroup(tempGroup, isMandatory))
                tempGroup = ""
                isMandatory = false
                continue
            }

            if (symbol == SYMBOL_DAMAGED) {
                isMandatory = true
                tempGroup += symbol
                continue
            }

            if (symbol == SYMBOL_UNKNOWN) {
                tempGroup += symbol
            }
        }

        if (tempGroup.isNotEmpty()) {
            groups.add(SpringGroup(tempGroup, isMandatory))
        }

        return groups
    }

    fun getNbOfArrangements(): Int {
        val potentialGroups = getPotentialGroups()

        val count = countPermutations(potentialGroups, damagedGroupSizes)
        println(count)
        return count
    }

    private fun countPermutations(potentialGroups: List<SpringGroup>, damagedGroupSizes: List<Int>): Int {

        if (!canPermutationBePerformed(potentialGroups, damagedGroupSizes))
            return 0

        if (potentialGroups.size == 1)
            return countPermutationsPerGroup(potentialGroups.first(), damagedGroupSizes)

        var count = 0
        val potentialGroup = potentialGroups.first()
        val reducedGroups = potentialGroups.subList(1, potentialGroups.size)

        for (index in 0..damagedGroupSizes.size) {
            val (left, right) = splitDamagedGroupSizes(index, damagedGroupSizes)

            if (left.isEmpty() && !potentialGroup.mandatory) {
                count += countPermutations(reducedGroups, right)
                continue
            }

            if (right.isEmpty() && !containsMandatoryGroups(reducedGroups)) {
                count += countPermutationsPerGroup(potentialGroup, left)
                continue
            }

            if (!canPermutationBePerformed(reducedGroups, right)) continue

            val groupPermutations = countPermutationsPerGroup(potentialGroup, left)
            if (groupPermutations == 0 && potentialGroup.mandatory) continue

            val rest = countPermutations(reducedGroups, right)
            count += groupPermutations * rest
        }

        return count
    }

    private fun containsMandatoryGroups(groups: List<SpringGroup>): Boolean {
        return groups.any { it.mandatory }
    }

    private fun canPermutationBePerformed(potentialGroups: List<SpringGroup>, damagedGroupSizes: List<Int>): Boolean {

        val availableGroupSpace = potentialGroups.sumOf { it.elements.length }
        val minNeededGroupSpace = damagedGroupSizes.sum()

        if (potentialGroups.isEmpty() || damagedGroupSizes.isEmpty() || availableGroupSpace < minNeededGroupSpace)
            return false

        return true
    }

    fun countPermutationsPerGroup(potentialGroup: SpringGroup, damagedGroupSizes: List<Int>): Int {
        val availableGroupSpace = potentialGroup.elements.length
        val minNeededGroupSpace = damagedGroupSizes.sum() + damagedGroupSizes.size - 1
        if (availableGroupSpace < minNeededGroupSpace || damagedGroupSizes.isEmpty())
            return 0

        var count = 0

        val operationals = damagedGroupSizes.map { 1 }.toMutableList()
        operationals[0] = 0

        var indexToUpdate = operationals.lastIndex
        while (true) {
            if (runPermutation(operationals, potentialGroup, damagedGroupSizes))
                count++

            // update operationals
            operationals[indexToUpdate]++

            while (operationals.sum() + damagedGroupSizes.sum() > availableGroupSpace) {
                if (indexToUpdate == 0)
                    return count

                operationals[indexToUpdate] = 1
                indexToUpdate--
                operationals[indexToUpdate]++
            }

            indexToUpdate = operationals.lastIndex
        }
    }

    private fun runPermutation(
        operationals: MutableList<Int>, potentialGroup: SpringGroup, damagedGroupSizes: List<Int>
    ): Boolean {

        var groupSizeIndex = -1
        var remainingGroupSizeValue = -1
        var operationalsIndex = 0
        var operationalsValue = operationals[operationalsIndex]

        for (symbol in potentialGroup.elements) {

            if (remainingGroupSizeValue == -1) {
                if (groupSizeIndex < damagedGroupSizes.lastIndex && operationalsValue <= 0) {
                    groupSizeIndex++
                    remainingGroupSizeValue = damagedGroupSizes[groupSizeIndex]
                } else {
                    if (symbol == SYMBOL_DAMAGED)
                        return false

                    operationalsValue--
                    //print(".")
                }
            }

            if (remainingGroupSizeValue == 0) {
                if (symbol == SYMBOL_DAMAGED)
                    return false

                remainingGroupSizeValue--

                if (operationalsIndex < operationals.lastIndex)
                    operationalsIndex++

                operationalsValue = operationals[operationalsIndex] - 1
                //print(".")
            }

            if (remainingGroupSizeValue > 0) {
                //print("#")
                remainingGroupSizeValue--
            }
        }
        //println()

        return true
    }

    fun splitDamagedGroupSizes(index: Int, damagedGroupSizes: List<Int>): Pair<List<Int>, List<Int>> {
        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()

        for ((i, value) in damagedGroupSizes.withIndex()) {
            if (index > i) left.add(value)
            else right.add(value)
        }

        return Pair(left, right)
    }
}

data class SpringGroup(val elements: String, val mandatory: Boolean)