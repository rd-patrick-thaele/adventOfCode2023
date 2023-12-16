class Day16(val layout: List<String>) {

    val energizedFields = mutableMapOf<BeamPosition, Set<BeamDirection>>()
    companion object {
        const val SYMBOL_EMPTY_SPACE = '.'
        const val SYMBOL_MIRROR_DOWNLEFT_UPRIGHT = '/'
        const val SYMBOL_MIRROR_UPLEFT_DOWNRIGHT = '\\'
        const val SYMBOL_VERTICAL_SPLITTER = '|'
        const val SYMBOL_HORIZONTAL_SPLITTER = '-'
    }

    fun getMaxEnergizedFields(): Int {
        var maxEnergizedFields = 0
        val xLastIndex = layout.first().lastIndex

        for (y in layout.indices) {
            val countLeftBorder = countEnergizedFields(Beam(BeamPosition(y, 0), BeamDirection.RIGHT))
            if (countLeftBorder > maxEnergizedFields)
                maxEnergizedFields = countLeftBorder

            val countRightBorder = countEnergizedFields(Beam(BeamPosition(y, xLastIndex), BeamDirection.LEFT))
            if (countRightBorder > maxEnergizedFields)
                maxEnergizedFields = countRightBorder
        }

        for (x in 0..xLastIndex) {
            val countUpperBorder = countEnergizedFields(Beam(BeamPosition(0, x), BeamDirection.DOWNWARD))
            if (countUpperBorder > maxEnergizedFields)
                maxEnergizedFields = countUpperBorder

            val countLowerBorder = countEnergizedFields(Beam(BeamPosition(layout.lastIndex, x), BeamDirection.UPWARD))
            if (countLowerBorder > maxEnergizedFields)
                maxEnergizedFields = countLowerBorder
        }

        return maxEnergizedFields
    }

    fun countEnergizedFields(startBeam: Beam = Beam(BeamPosition(0,0), BeamDirection.RIGHT)): Int {
        energizedFields.clear()

        sendBeam(startBeam)

        return energizedFields.count()
    }

    private fun sendBeam(beam: Beam) {
        val fieldDirections = energizedFields[beam.position]

        if (fieldDirections != null && fieldDirections.contains(beam.direction)) return
        else {
            if (fieldDirections == null) {
                energizedFields[beam.position] = setOf(beam.direction)
            } else {
                val newDirections = fieldDirections.toMutableSet()
                newDirections.add(beam.direction)
                energizedFields[beam.position] = newDirections.toSet()
            }
        }

        val nextBeams = forwardBeam(beam)
        nextBeams.forEach { sendBeam(it) }
    }

    fun forwardBeam(beam: Beam): List<Beam> {
        val nextBeams = mutableListOf<Beam>()


        val symbol = layout[beam.position.y][beam.position.x]
        if (symbol == SYMBOL_EMPTY_SPACE) {
            nextBeams.add(getBeamForEmptySpace(beam))
        }

        if (symbol == SYMBOL_MIRROR_DOWNLEFT_UPRIGHT) {
            nextBeams.add(getBeamForMirrorDownLeftToUpRight(beam))
        }

        if (symbol == SYMBOL_MIRROR_UPLEFT_DOWNRIGHT) {
            nextBeams.add(getBeamForMirrorUpLeftToDownRight(beam))
        }

        if (symbol == SYMBOL_VERTICAL_SPLITTER) {
            when (beam.direction) {
                BeamDirection.RIGHT, BeamDirection.LEFT -> {
                    nextBeams.add(getBeamForMirrorDownLeftToUpRight(beam))
                    nextBeams.add(getBeamForMirrorUpLeftToDownRight(beam))
                }

                BeamDirection.DOWNWARD, BeamDirection.UPWARD -> nextBeams.add(getBeamForEmptySpace(beam))
            }
        }

        if (symbol == SYMBOL_HORIZONTAL_SPLITTER) {
            when (beam.direction) {
                BeamDirection.RIGHT, BeamDirection.LEFT -> {
                    nextBeams.add(getBeamForEmptySpace(beam))
                }

                BeamDirection.DOWNWARD, BeamDirection.UPWARD -> {
                    nextBeams.add(getBeamForMirrorUpLeftToDownRight(beam))
                    nextBeams.add(getBeamForMirrorDownLeftToUpRight(beam))
                }
            }
        }

        return nextBeams.filter { withInBoundaries(it) }
    }

    private fun getBeamForEmptySpace(beam: Beam): Beam {
        val y = beam.position.y
        val x = beam.position.x

        return when (beam.direction) {
            BeamDirection.RIGHT -> Beam(BeamPosition(y, x + 1), beam.direction)
            BeamDirection.LEFT -> Beam(BeamPosition(y, x - 1), beam.direction)
            BeamDirection.DOWNWARD -> Beam(BeamPosition(y + 1, x), beam.direction)
            BeamDirection.UPWARD -> Beam(BeamPosition(y - 1, x), beam.direction)
        }
    }

    private fun getBeamForMirrorDownLeftToUpRight(beam: Beam): Beam {
        val y = beam.position.y
        val x = beam.position.x

        return when (beam.direction) {
            BeamDirection.RIGHT -> Beam(BeamPosition(y - 1, x), BeamDirection.UPWARD)
            BeamDirection.LEFT -> Beam(BeamPosition(y + 1, x), BeamDirection.DOWNWARD)
            BeamDirection.DOWNWARD -> Beam(BeamPosition(y, x - 1), BeamDirection.LEFT)
            BeamDirection.UPWARD -> Beam(BeamPosition(y, x + 1), BeamDirection.RIGHT)
        }
    }

    private fun getBeamForMirrorUpLeftToDownRight(beam: Beam): Beam {
        val y = beam.position.y
        val x = beam.position.x

        return when (beam.direction) {
            BeamDirection.RIGHT -> Beam(BeamPosition(y + 1, x), BeamDirection.DOWNWARD)
            BeamDirection.LEFT -> Beam(BeamPosition(y - 1, x), BeamDirection.UPWARD)
            BeamDirection.DOWNWARD -> Beam(BeamPosition(y, x + 1), BeamDirection.RIGHT)
            BeamDirection.UPWARD -> Beam(BeamPosition(y, x - 1), BeamDirection.LEFT)
        }
    }

    private fun withInBoundaries(nextBeam: Beam): Boolean {
        val x = nextBeam.position.x
        val y = nextBeam.position.y

        return x in layout.first().indices && y in layout.indices
    }

}

data class Beam(val position: BeamPosition, val direction: BeamDirection)

data class BeamPosition(val y: Int, val x: Int)

enum class BeamDirection {
    RIGHT, LEFT, DOWNWARD, UPWARD
}