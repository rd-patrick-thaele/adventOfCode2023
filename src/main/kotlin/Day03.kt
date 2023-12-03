class Day03 (private val engineSchematic: List<String>){

    private val _engineSchematic = run {
        val tempList = mutableListOf<String>()
        tempList.add(".".repeat(engineSchematic.first().length + 2))
        for (line in engineSchematic) {
            tempList.add(".$line.")
        }
        tempList.add(".".repeat(engineSchematic.first().length + 2))
        tempList.toList()
    }

    companion object {
        const val NO_PART_LIST = "0123456789."
    }

    fun sumPartNumbers(): Int {
        return findEngineParts().sumOf { getPartNumber(it) }
    }

    fun findEngineParts(filter: Char?=null): List<EnginePart> {
        val engineParts = mutableListOf<EnginePart>()

        for ((y, row) in _engineSchematic.withIndex()) {
            for ((x, symbol) in row.withIndex()) {
                if (NO_PART_LIST.contains(symbol))
                    continue

                if (filter == null || filter==symbol)
                engineParts.add(EnginePart(symbol, y, x))
            }
        }

        return engineParts.toList()
    }

    private fun getPartNumber(enginePart: EnginePart): Int {
        var partNumber = 0
        val consideredSymbols = mutableListOf<Pair<Int, Int>>()

        for (dy in -1..1) {
            for (dx in -1..1) {

                val coordinate = Pair(enginePart.y + dy, enginePart.x + dx)
                val symbol = _engineSchematic[coordinate.first][coordinate.second]
                if(isNumeric(symbol) && !consideredSymbols.contains(coordinate)) {

                    var partNumberFragment = symbol.toString()
                    consideredSymbols.add(coordinate)

                    // search left
                    var ddx = -1
                    while (true) {
                        val searchLeft = Pair(coordinate.first, coordinate.second + ddx)
                        val leftSymbol = _engineSchematic[searchLeft.first][searchLeft.second]
                        if (!isNumeric(leftSymbol)) {
                            break
                        }

                        partNumberFragment = leftSymbol + partNumberFragment
                        consideredSymbols.add(searchLeft)

                        ddx--
                    }

                    // search right
                    ddx = 1
                    while (true) {
                        val searchRight = Pair(coordinate.first, coordinate.second + ddx)
                        val rightSymbol = _engineSchematic[searchRight.first][searchRight.second]
                        if (!isNumeric(rightSymbol)) {
                            break
                        }

                        partNumberFragment += rightSymbol
                        consideredSymbols.add(searchRight)

                        ddx++
                    }

                    partNumber += partNumberFragment.toInt()
                }
            }
        }

        return partNumber
    }

    private fun isNumeric(symbol: Char): Boolean {
        return NO_PART_LIST.contains(symbol) && symbol != '.'
    }

    fun sumGearRatios(): Int {
        return findEngineParts('*').sumOf { getGearRatio(it) }
    }

    private fun getGearRatio(enginePart: EnginePart): Int {
        var gearRatio = 1
        var countNumbers = 0
        val consideredSymbols = mutableListOf<Pair<Int, Int>>()

        for (dy in -1..1) {
            for (dx in -1..1) {

                val coordinate = Pair(enginePart.y + dy, enginePart.x + dx)
                val symbol = _engineSchematic[coordinate.first][coordinate.second]
                if(isNumeric(symbol) && !consideredSymbols.contains(coordinate)) {

                    var partNumberFragment = symbol.toString()
                    consideredSymbols.add(coordinate)

                    // search left
                    var ddx = -1
                    while (true) {
                        val searchLeft = Pair(coordinate.first, coordinate.second + ddx)
                        val leftSymbol = _engineSchematic[searchLeft.first][searchLeft.second]
                        if (!isNumeric(leftSymbol)) {
                            break
                        }

                        partNumberFragment = leftSymbol + partNumberFragment
                        consideredSymbols.add(searchLeft)

                        ddx--
                    }

                    // search right
                    ddx = 1
                    while (true) {
                        val searchRight = Pair(coordinate.first, coordinate.second + ddx)
                        val rightSymbol = _engineSchematic[searchRight.first][searchRight.second]
                        if (!isNumeric(rightSymbol)) {
                            break
                        }

                        partNumberFragment += rightSymbol
                        consideredSymbols.add(searchRight)

                        ddx++
                    }

                    gearRatio *= partNumberFragment.toInt()
                    countNumbers++
                }
            }
        }

        return if (countNumbers == 2)
            gearRatio
        else 0
    }

}

data class EnginePart(val symbol: Char, val y: Int, val x: Int)