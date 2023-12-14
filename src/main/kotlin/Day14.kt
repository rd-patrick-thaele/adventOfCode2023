class Day14 {

    companion object {
        private const val SYMBOL_ROUND_ROCK = 'O'
        private const val SYMBOL_CUBE_ROCK = '#'
        private const val SYMBOL_EMPTY_SPACE = '.'
    }

    fun tiltReflectorDish(reflectorDish: List<String>): List<String> {
        val tiltedDish = mutableListOf<MutableList<Char>>()
        repeat(reflectorDish.size) { tiltedDish.add(mutableListOf())}

        for (columnIndex in reflectorDish.first().indices) {
            var countRoundRocks = 0
            var totalCountToCubeRocks = 0
            var lastRowIndex = 0

            for (rowIndex in reflectorDish.indices) {
                totalCountToCubeRocks++
                val symbol = reflectorDish[rowIndex][columnIndex]

                if (symbol == SYMBOL_ROUND_ROCK) {
                    countRoundRocks++
                }

                if (symbol == SYMBOL_CUBE_ROCK) {
                    repeat(countRoundRocks) {
                        tiltedDish[lastRowIndex].add(SYMBOL_ROUND_ROCK)
                        lastRowIndex++
                    }

                    repeat(totalCountToCubeRocks - countRoundRocks - 1) {
                        tiltedDish[lastRowIndex].add(SYMBOL_EMPTY_SPACE)
                        lastRowIndex++
                    }

                    tiltedDish[lastRowIndex].add(SYMBOL_CUBE_ROCK)
                    lastRowIndex++

                    countRoundRocks = 0
                    totalCountToCubeRocks = 0
                }
            }

            repeat(countRoundRocks) {
                tiltedDish[lastRowIndex].add(SYMBOL_ROUND_ROCK)
                lastRowIndex++
            }

            repeat(totalCountToCubeRocks - countRoundRocks) {
                tiltedDish[lastRowIndex].add(SYMBOL_EMPTY_SPACE)
                lastRowIndex++
            }
        }

        return tiltedDish.map { it.joinToString("") }
    }

    fun getTotalLoad(reflectorDish: List<String>): Int {
        return reflectorDish.mapIndexed { index, line -> line.count { it==SYMBOL_ROUND_ROCK } * (reflectorDish.size - index) }
            .sum()
    }
}