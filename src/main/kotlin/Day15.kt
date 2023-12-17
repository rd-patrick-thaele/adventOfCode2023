
class Day15 {

    var initSequence = emptyList<Pair<Lens, LensOperation>>()
    val boxes = mutableMapOf<Int,LensBox>()

    companion object {
        const val OPERATOR_DASH = '-'

        fun hash(input: String): Int {
            var currentValue = 0

            for (char in input) {
                val asciiCode = char.code
                currentValue += asciiCode
                currentValue *= 17
                currentValue %= 256
            }

            return currentValue
        }

        fun sumOfHashes(initSequence: String): Int {
            return initSequence.split(",")
                .sumOf { hash(it) }
        }

        fun parseInitStep(initStep: String): Pair<Lens, LensOperation> {
            val lastSymbol = initStep.last()
            val operation = if (lastSymbol == OPERATOR_DASH) LensOperation.REMOVE_LENS else LensOperation.ADD_LENS

            return when (operation) {
                LensOperation.REMOVE_LENS -> Pair(Lens(initStep.substring(0..<initStep.lastIndex),0), operation)
                LensOperation.ADD_LENS -> Pair(Lens(initStep.substring(0..<initStep.lastIndex - 1), lastSymbol.digitToInt()), operation)
            }
        }

        fun focusingPower(initSequence: String): Int {
            val day = Day15()
            day.readInitSequence(initSequence)
            day.sortLensesIntoBoxes()

            return day.boxes.values.sumOf { it.getFocusingPower() }
        }
    }

    fun readInitSequence(initSequence: String) {
        this.initSequence = initSequence.split(",")
            .map { parseInitStep(it) }
            .toList()
    }

    fun sortLensesIntoBoxes() {
        for (initStep in initSequence) {
            val hash = hash(initStep.first.label)
            var box = boxes[hash]

            if (box == null) {
                box = LensBox(hash, mutableListOf())
                boxes[hash] = box
            }

            when (initStep.second) {
                LensOperation.REMOVE_LENS -> box.removeLens(initStep.first.label)
                LensOperation.ADD_LENS -> box.addOrReplaceLens(initStep.first)
            }
        }
    }
}

data class Lens(val label: String, val focalLength: Int)

data class LensBox(val boxNumber: Int, val lenses: MutableList<Lens>) {
    fun findLens(label: String): Int {
        for ((index, lens) in lenses.withIndex()) {
            if (lens.label == label) {
                return index
            }
        }

        return -1
    }

    fun removeLens(label: String) {
        val index = findLens(label)

        if (index == -1) return

        lenses.removeAt(index)
    }

    fun addOrReplaceLens(lensToAdd: Lens) {
        val index = findLens(lensToAdd.label)

        if (index == -1) {
            lenses.addLast(lensToAdd)
            return
        }

        lenses.removeAt(index)
        lenses.add(index, lensToAdd)
    }

    fun getFocusingPower(): Int {
        return lenses.mapIndexed { index, lens -> (index + 1) * lens.focalLength  }.sum() * (boxNumber + 1)
    }
}

enum class LensOperation {
    REMOVE_LENS, ADD_LENS
}