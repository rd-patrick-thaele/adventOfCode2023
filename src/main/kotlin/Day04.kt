import kotlin.math.pow

class Day04 {
    fun getTotalPoints(cards: List<String>): Int {
        return cards.map { ScratchCard.parseDescription(it) }
            .sumOf { it.calculatePoints() }
    }

    fun getPileSize(cards: List<String>): Int {
        val sortedPile = cards.map { CardStock(1, ScratchCard.parseDescription(it)) }
            .toList()

        for ((index, pilePerCard) in sortedPile.withIndex()) {
            val nbOfMatches = pilePerCard.card.getNbOfMatches()

            repeat(pilePerCard.count) {
                for (i in 1..nbOfMatches) {
                    sortedPile[index + i].count++
                }
            }
        }

        return sortedPile.sumOf { it.count }
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
        val nbOfMetWins = getNbOfMatches()

        return 2.0.pow(nbOfMetWins-1).toInt()
    }

    fun getNbOfMatches(): Int {
        return winningNumbers.intersect(lotteryNumbers.toSet()).size
    }

}

data class CardStock(var count: Int, val card: ScratchCard)