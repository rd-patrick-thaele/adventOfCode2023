
class Day05(val seeds: List<Long>, val seedRanges: List<LongRange>, val almanacCategories: Map<String, AlmanacCategory>) {

    companion object {

        private val categoryHeaderRegex = "(\\w+)-to-(\\w+) map:".toRegex()

        fun readAlmanac(serialAlmanac: List<String>): Day05 {
            val subLists = splitListByBlankLine(serialAlmanac)

            val serialSeeds = subLists.removeFirst().first()
            val seeds = readSeeds(serialSeeds)
            val seedRanges = readSeedRanges(serialSeeds)
            val almanacCategories = mutableMapOf<String, AlmanacCategory>()

            for (serialCategory in subLists) {
                val category = readCategory(serialCategory)
                almanacCategories.put(category.sourceCategory, category)
            }

            return Day05(seeds, seedRanges, almanacCategories.toMap())
        }

        private fun splitListByBlankLine(serialAlmanac: List<String>): List<List<String>> {
            val subLists = mutableListOf<List<String>>()
            var tempSubList = mutableListOf<String>()

            for (line in serialAlmanac) {
                if (line.isBlank()) {
                    subLists.add(tempSubList)
                    tempSubList = mutableListOf()
                } else {
                    tempSubList.add(line)
                }
            }

            subLists.add(tempSubList)

            return subLists
        }

        private fun readSeeds(seedLine: String): List<Long> {
            return seedLine.split("seeds: ")[1]
                .split(" ")
                .map { it.toLong() }
                .toList()
        }

        private fun readSeedRanges(serialSeeds: String): List<LongRange> {
            return readSeeds(serialSeeds).chunked(2)
                .map { it[0] until it[0] + it[1]  }
                .toList()
        }

        private fun readCategory(serialCategory: List<String>): AlmanacCategory {
            val (sourceCategory, destinationCategory) = categoryHeaderRegex.find(serialCategory.removeFirst())!!.destructured
            val mappings = mutableListOf<AlmanacMapping>()

            for (mapping in serialCategory) {
                val splitValues = mapping.split(" ")

                mappings.add(AlmanacMapping(splitValues[1].toLong(), splitValues[0].toLong(), splitValues[2].toLong()))
            }

            return AlmanacCategory(sourceCategory, destinationCategory, mappings.toList())
        }
    }

    fun getLowestLocationNumber(): Long {

        var lowestLocationNumber = Long.MAX_VALUE

        for (seed in seeds) {
            val locationNumber = getLocationNumberPerSeed(seed)
            if (locationNumber < lowestLocationNumber) {
                lowestLocationNumber = locationNumber
            }
        }

        return lowestLocationNumber
    }

    private fun getLocationNumberPerSeed(seed: Long): Long {
        var sourceCategory = "seed"
        var value = seed

        while (true) {
            val category = almanacCategories[sourceCategory]
            value = category!!.lookup(value)

            if (category.destinationCategory == "location") {
                return value
            }

            sourceCategory = category.destinationCategory
        }
    }

    fun getLowestLocationNumberForSeedRange(): Long {
        var lowestLocationNumber = Long.MAX_VALUE

        println("SEED RANGES")
        for (seedRange in seedRanges) {
            println("${seedRange.first}..${seedRange.last}")
        }

        for (seedRange in seedRanges) {
            println("Check")
            for (seed in seedRange) {
                val locationNumber = getLocationNumberPerSeed(seed)
                if (locationNumber < lowestLocationNumber) {
                    lowestLocationNumber = locationNumber
                }
            }
        }

        return lowestLocationNumber
    }
}

data class AlmanacCategory(val sourceCategory: String, val destinationCategory: String, val mappings: List<AlmanacMapping>) {
    fun lookup(sourceNumber: Long): Long {
        for (mapping in mappings) {
            if (mapping.isInRange(sourceNumber))
                return mapping.getMapping(sourceNumber)
        }

        return sourceNumber
    }
}

data class AlmanacMapping(val sourceRangeStart: Long, val destinationRangeStart: Long, val rangeLength: Long) {
    fun isInRange(input: Long): Boolean {
        val range = sourceRangeStart until sourceRangeStart+rangeLength

        return input in range
    }

    fun getMapping(input: Long): Long {
        val diff = input - sourceRangeStart

        return destinationRangeStart + diff
    }
}