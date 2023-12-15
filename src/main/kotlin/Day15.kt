class Day15 {
    companion object {
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
    }
}