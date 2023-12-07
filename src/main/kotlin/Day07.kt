class Day07 {
    fun getTotalWinnings(playedHands: List<String>): Int {
        return playedHands.map { CamelCardsEntry.parse(it) }
            .sorted()
            .map { it.bid }
            .reduceIndexed { index, acc, bid -> acc + (index + 1) * bid }
    }

}

data class CamelCardsEntry(val hand: String, val bid: Int) : Comparable<CamelCardsEntry> {
    companion object {

        private val cardEntryRegex = "([2-9TJQKA]{5}) ([0-9]+)".toRegex()
        fun parse(line: String): CamelCardsEntry {
            val (hand, bid) = cardEntryRegex.find(line)!!.destructured

            return CamelCardsEntry(hand, bid.toInt())
        }
    }

    fun getType(): CamelCardsType {
        val histogram = hand.groupingBy { it }
            .eachCount()

        return when (histogram.size) {
            1 -> CamelCardsType.FIVE_OF_A_KIND
            2 -> if (histogram.containsValue(4)) CamelCardsType.FOUR_OF_A_KIND else CamelCardsType.FULL_HOUSE
            3 -> if (histogram.containsValue(3)) CamelCardsType.THREE_OF_A_KIND else CamelCardsType.TWO_PAIR
            4 -> CamelCardsType.ONE_PAIR

            else -> CamelCardsType.HIGH_CARD
        }
    }

    override fun compareTo(other: CamelCardsEntry): Int {
        val compareResult = getType().compareTo(other.getType())

        return if (compareResult == 0) compareByCard(other) else compareResult
    }

    fun compareByCard(other: CamelCardsEntry): Int {

        for (i in hand.indices) {
            val numValueCard = getNumericValue(hand[i])
            val numValueOtherCard = getNumericValue(other.hand[i])

            if (numValueCard == numValueOtherCard) continue

            return numValueCard - numValueOtherCard
        }

        return 0
    }

    private fun getNumericValue(card: Char): Int {


        val digit = card.digitToIntOrNull()

        if (digit != null) return digit

        return when (card) {
            'T' -> 10
            'J' -> 11
            'Q' -> 12
            'K' -> 13
            'A' -> 14
            else -> 0
        }
    }
}

enum class CamelCardsType {
    HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND, FULL_HOUSE, FOUR_OF_A_KIND, FIVE_OF_A_KIND,
}