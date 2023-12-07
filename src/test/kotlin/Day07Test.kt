import io.kotest.core.spec.style.FreeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import kotlin.math.sign

class Day07Test : FreeSpec({


    "samples" - {

        "getType" - {

            withData(
                listOf(
                    Pair("77777", CamelCardsType.FIVE_OF_A_KIND),
                    Pair("22A22", CamelCardsType.FOUR_OF_A_KIND),
                    Pair("55KKK", CamelCardsType.FULL_HOUSE),
                    Pair("84868", CamelCardsType.THREE_OF_A_KIND),
                    Pair("J2TJT", CamelCardsType.TWO_PAIR),
                    Pair("97QKQ", CamelCardsType.ONE_PAIR),
                    Pair("AJ295", CamelCardsType.HIGH_CARD),
                )
            ) { (input, result) ->
                CamelCardsEntry(input, 0).getType() shouldBe result
            }
        }

        "compare Types" - {

            withData(
                listOf(
                    Triple(CamelCardsType.FIVE_OF_A_KIND, CamelCardsType.FIVE_OF_A_KIND, 0),
                    Triple(CamelCardsType.FIVE_OF_A_KIND, CamelCardsType.HIGH_CARD, 1),
                    Triple(CamelCardsType.HIGH_CARD, CamelCardsType.FIVE_OF_A_KIND, -1),
                    Triple(CamelCardsType.HIGH_CARD, CamelCardsType.ONE_PAIR, -1),
                    Triple(CamelCardsType.ONE_PAIR, CamelCardsType.TWO_PAIR, -1),
                    Triple(CamelCardsType.TWO_PAIR, CamelCardsType.THREE_OF_A_KIND, -1),
                    Triple(CamelCardsType.THREE_OF_A_KIND, CamelCardsType.FULL_HOUSE, -1),
                    Triple(CamelCardsType.FULL_HOUSE, CamelCardsType.FOUR_OF_A_KIND, -1),
                    Triple(CamelCardsType.FOUR_OF_A_KIND, CamelCardsType.FIVE_OF_A_KIND, -1),
                )
            ) { (compareA, compareB, result) ->
                compareA.compareTo(compareB).sign shouldBe result
            }
        }

        "compareByCard" - {
            withData(
                listOf(
                    Triple(CamelCardsEntry("32T3K", 765), CamelCardsEntry("32T3K", 765), 0),
                    Triple(CamelCardsEntry("21111", 765), CamelCardsEntry("11111", 684), 1),
                    Triple(CamelCardsEntry("12111", 765), CamelCardsEntry("11111", 684), 1),
                    Triple(CamelCardsEntry("11211", 765), CamelCardsEntry("11111", 684), 1),
                    Triple(CamelCardsEntry("11121", 765), CamelCardsEntry("11111", 684), 1),
                    Triple(CamelCardsEntry("11112", 765), CamelCardsEntry("11111", 684), 1),
                    Triple(CamelCardsEntry("11111", 765), CamelCardsEntry("11112", 684), -1),
                    Triple(CamelCardsEntry("11311", 765), CamelCardsEntry("11211", 684), 1),
                    Triple(CamelCardsEntry("11311", 765), CamelCardsEntry("11411", 684), -1),
                    Triple(CamelCardsEntry("11511", 765), CamelCardsEntry("11411", 684), 1),
                    Triple(CamelCardsEntry("11511", 765), CamelCardsEntry("11611", 684), -1),
                    Triple(CamelCardsEntry("11711", 765), CamelCardsEntry("11611", 684), 1),
                    Triple(CamelCardsEntry("11711", 765), CamelCardsEntry("11811", 684), -1),
                    Triple(CamelCardsEntry("11911", 765), CamelCardsEntry("11811", 684), 1),
                    Triple(CamelCardsEntry("11911", 765), CamelCardsEntry("11T11", 684), -1),
                    Triple(CamelCardsEntry("11J11", 765), CamelCardsEntry("11T11", 684), 1),
                    Triple(CamelCardsEntry("11J11", 765), CamelCardsEntry("11Q11", 684), -1),
                    Triple(CamelCardsEntry("11K11", 765), CamelCardsEntry("11Q11", 684), 1),
                    Triple(CamelCardsEntry("11K11", 765), CamelCardsEntry("11A11", 684), -1),
                )
            ) { (compareA, compareB, result) ->
                compareA.compareByCard(compareB).sign shouldBe result
            }
        }

        "compare hands" - {

            withData(
                listOf(
                    Triple(CamelCardsEntry("32T3K", 765), CamelCardsEntry("32T3K", 765), 0),
                    Triple(CamelCardsEntry("32T3K", 765), CamelCardsEntry("T55J5", 684), -1),
                    Triple(CamelCardsEntry("T55J5", 684), CamelCardsEntry("KK677", 28), 1),
                    Triple(CamelCardsEntry("KTJJT", 220), CamelCardsEntry("KK677", 28), -1),
                    Triple(CamelCardsEntry("QQQJA", 483), CamelCardsEntry("T55J5", 684), 1),
                )
            ) { (compareA, compareB, result) ->
                compareA.compareTo(compareB).sign shouldBe result
            }
        }

        "readCardEntry" {
            // given
            val line = "32T3K 765"

            // when
            val cardEntry = CamelCardsEntry.parse(line)

            // then
            cardEntry.hand shouldBe "32T3K"
            cardEntry.bid shouldBe 765
        }

        "part 1" {
            // given
            val playedHands = """
                32T3K 765
                T55J5 684
                KK677 28
                KTJJT 220
                QQQJA 483
            """.trimIndent().lineSequence().toList()

            // when
            val totalWinnings = Day07().getTotalWinnings(playedHands)

            // then
            totalWinnings shouldBe 6440
        }
    }

    "solution" - {

        "part 1" {
            // given
            val playedHands = getResourceFileAsStringSequence("day07/input.txt")

            // when
            val totalWinnings = Day07().getTotalWinnings(playedHands)

            // then
            totalWinnings shouldBe 251_927_063
        }

        "part 2" {
            // given
        }
    }
}) {
}