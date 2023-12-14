class Day13 {
    fun sumNotes(input: List<String>): Int {
        return readMirrorPatterns(input)
            .sumOf { it.getMirrorSize() }
    }

    private fun readMirrorPatterns(input: List<String>): List<MirrorPattern> {
        val mirrorPatterns = mutableListOf<MirrorPattern>()
        var pattern = mutableListOf<String>()

        for (line in input) {
            if (line.isBlank()) {
                mirrorPatterns.add(MirrorPattern(pattern))
                pattern = mutableListOf()
                continue
            }

            pattern.add(line)
        }
        mirrorPatterns.add(MirrorPattern(pattern))

        return mirrorPatterns.toList()
    }
}

data class MirrorPattern(val pattern: List<String>) {
    fun getMirrorSize(): Int {
        val columns = getNumberOfColumns()
        val rows = getNumberOfRows() * 100
        //val size = if (columns > 0) columns
        //else getNumberOfRows() * 100
        val size = columns + rows

        println(size)

        return size
    }

    private fun getNumberOfColumns(): Int {
        val patternWidth = pattern.first().length
        val lastColumn = getColumn(patternWidth - 1)

        var indexes = getDuplicatedColumns(lastColumn)
        indexes = findMirroredVerticalIndexes(indexes)
        if (indexes.size < 2) {
            val firstColumn = getColumn(0)
            indexes = getDuplicatedColumns(firstColumn)
        }

        indexes = findMirroredVerticalIndexes(indexes)

        if (indexes.size == 2)
            return (indexes[1] - indexes[0] + 1) / 2 + indexes[0]

        return 0
    }

    private fun getColumn(index: Int): String {
        return pattern.map { it[index] }
            .joinToString("")
    }

    private fun getDuplicatedColumns(lastColumn: String): List<Int> {
        val mirrored = mutableListOf<Int>()

        for (index in pattern.first().indices) {
            if (getColumn(index) == lastColumn)
                mirrored.add(index)
        }

        return mirrored
    }

    private fun findMirroredVerticalIndexes(indexes: List<Int>): List<Int> {

        if (indexes.size < 2) {
            return emptyList()
        }

        for (leftIndex in 0 until indexes.lastIndex) {
            for (rightIndex in indexes.lastIndex downTo leftIndex + 1) {
                val left = indexes[leftIndex]
                val right = indexes[rightIndex]

                if (left != 0 && right != pattern.first().lastIndex)
                    continue

                val distance = right - left + 1

                if (distance % 2 == 1) continue

                var isMirror = true
                for (diff in 1..distance / 2) {
                    val leftColumn = getColumn(left + diff)
                    val rightColumn = getColumn(right - diff)

                    if (leftColumn != rightColumn) isMirror = false
                }

                if (isMirror)
                    return listOf(left, right)
            }
        }

        return emptyList()
    }

    private fun getNumberOfRows(): Int {
        val lastRow = pattern.last()
        var indexes = getDuplicatedRows(lastRow)
        indexes = findMirroredHorizontalIndexes(indexes)

        if (indexes.size < 2) {
            val firstRow = pattern.first()
            indexes = getDuplicatedRows(firstRow)
        }

        indexes = findMirroredHorizontalIndexes(indexes)

        return if (indexes.size == 2)
            (indexes[1] - indexes[0] + 1) / 2 + indexes[0]
        else
            0
    }

    private fun getDuplicatedRows(lastRow: String): List<Int> {
        val mirrored = mutableListOf<Int>()

        for ((index, row) in pattern.withIndex()) {
            if (row == lastRow)
                mirrored.add(index)
        }

        return mirrored
    }

    private fun findMirroredHorizontalIndexes(indexes: List<Int>): List<Int> {

        if (indexes.size < 2) {
            return emptyList()
        }

        for (leftIndex in 0 until indexes.lastIndex) {
            for (rightIndex in indexes.lastIndex downTo leftIndex + 1) {
                val left = indexes[leftIndex]
                val right = indexes[rightIndex]

                if (left != 0 && right != pattern.lastIndex)
                    continue

                val distance = right - left + 1

                if (distance % 2 == 1) continue

                var isMirror = true
                for (diff in 1..distance / 2) {
                    if (pattern[left + diff] != pattern[right - diff]) isMirror = false
                }

                if (isMirror)
                    return listOf(left, right)
            }
        }

        return emptyList()
    }
}