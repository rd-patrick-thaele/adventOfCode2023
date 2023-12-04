import kotlin.math.pow

class Day04 {
    fun getTotalPoints(cards: List<String>): Int {
        return cards.map { ScratchCard.parseDescription(it) }
            .sumOf { it.calculatePoints() }
    }
}

data class ScratchCard(val id: Int, val winningNumbers: List<Int>, val lotteryNumbers: List<Int>) {
    companion object {
        fun parseDescription(cardDescription: String): ScratchCard {
            val splitIdFromContent = cardDescription.split(":")
            val id = splitIdFromContent[0].split(" ").last()

            val splitWinningFromLotteryNumbers = splitIdFromContent[1].split("|")
            val winningNumbers = extractNumbersToList(splitWinningFromLotteryNumbers[0])
            val lotteryNumbers = extractNumbersToList(splitWinningFromLotteryNumbers[1])

            return ScratchCard(id.toInt(), winningNumbers, lotteryNumbers)
        }
        private fun extractNumbersToList(numbers: String): List<Int> {
            return numbers.split(" ")
                .filter { it.isNotBlank() }
                .map { it.toInt() }
                .toList()
        }

    }

    fun calculatePoints(): Int {
        val nbOfMetWins = winningNumbers.intersect(lotteryNumbers.toSet()).size

        return 2.0.pow(nbOfMetWins-1).toInt()
    }

}