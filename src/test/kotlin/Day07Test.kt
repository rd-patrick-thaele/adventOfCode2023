import io.kotest.core.spec.style.FreeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import kotlin.math.sign

class Day07Test : FreeSpec({


    "samples" - {

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

        "part 1" - {
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

            "compareByCard" - {
                withData(
                    listOf(
                        Triple(CamelCardsEntry("32T3K", 765), CamelCardsEntry("32T3K", 765), 0),
                        Triple(CamelCardsEntry("32222", 765), CamelCardsEntry("22222", 684), 1),
                        Triple(CamelCardsEntry("23222", 765), CamelCardsEntry("22222", 684), 1),
                        Triple(CamelCardsEntry("22322", 765), CamelCardsEntry("22222", 684), 1),
                        Triple(CamelCardsEntry("22232", 765), CamelCardsEntry("22222", 684), 1),
                        Triple(CamelCardsEntry("22223", 765), CamelCardsEntry("22222", 684), 1),
                        Triple(CamelCardsEntry("22222", 765), CamelCardsEntry("22223", 684), -1),
                        Triple(CamelCardsEntry("22322", 765), CamelCardsEntry("22422", 684), -1),
                        Triple(CamelCardsEntry("22522", 765), CamelCardsEntry("22422", 684), 1),
                        Triple(CamelCardsEntry("22522", 765), CamelCardsEntry("22622", 684), -1),
                        Triple(CamelCardsEntry("22722", 765), CamelCardsEntry("22622", 684), 1),
                        Triple(CamelCardsEntry("22722", 765), CamelCardsEntry("22822", 684), -1),
                        Triple(CamelCardsEntry("22922", 765), CamelCardsEntry("22822", 684), 1),
                        Triple(CamelCardsEntry("22922", 765), CamelCardsEntry("22T22", 684), -1),
                        Triple(CamelCardsEntry("22J22", 765), CamelCardsEntry("22T22", 684), 1),
                        Triple(CamelCardsEntry("22J22", 765), CamelCardsEntry("22Q22", 684), -1),
                        Triple(CamelCardsEntry("22K22", 765), CamelCardsEntry("22Q22", 684), 1),
                        Triple(CamelCardsEntry("22K22", 765), CamelCardsEntry("22A22", 684), -1),
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

        "part 2" - {
            "getType" - {

                withData(
                    listOf(
                        Pair("77777", CamelCardsType.FIVE_OF_A_KIND),
                        Pair("22A22", CamelCardsType.FOUR_OF_A_KIND),
                        Pair("55KKK", CamelCardsType.FULL_HOUSE),
                        Pair("84868", CamelCardsType.THREE_OF_A_KIND),
                        Pair("32T3T", CamelCardsType.TWO_PAIR),
                        Pair("97QKQ", CamelCardsType.ONE_PAIR),
                        Pair("AQ295", CamelCardsType.HIGH_CARD),
                        Pair("AJ295", CamelCardsType.ONE_PAIR),
                        Pair("J7QKQ", CamelCardsType.THREE_OF_A_KIND),
                        Pair("3JT3T", CamelCardsType.FULL_HOUSE),
                        Pair("J2TJT", CamelCardsType.FOUR_OF_A_KIND),
                        Pair("8J868", CamelCardsType.FOUR_OF_A_KIND),
                        Pair("J4J6J", CamelCardsType.FOUR_OF_A_KIND),
                        Pair("JJKKK", CamelCardsType.FIVE_OF_A_KIND),
                        Pair("22J22", CamelCardsType.FIVE_OF_A_KIND),
                    )
                ) { (input, result) ->
                    CamelCardsJokerEntry(input, 0).getType() shouldBe result
                }
            }

            "compareByCard" - {
                withData(
                    listOf(
                        Triple(CamelCardsJokerEntry("32T3K", 765), CamelCardsJokerEntry("32T3K", 765), 0),
                        Triple(CamelCardsJokerEntry("32222", 765), CamelCardsJokerEntry("22222", 684), 1),
                        Triple(CamelCardsJokerEntry("23222", 765), CamelCardsJokerEntry("22222", 684), 1),
                        Triple(CamelCardsJokerEntry("22322", 765), CamelCardsJokerEntry("22222", 684), 1),
                        Triple(CamelCardsJokerEntry("22232", 765), CamelCardsJokerEntry("22222", 684), 1),
                        Triple(CamelCardsJokerEntry("22223", 765), CamelCardsJokerEntry("22222", 684), 1),
                        Triple(CamelCardsJokerEntry("22222", 765), CamelCardsJokerEntry("22223", 684), -1),
                        Triple(CamelCardsJokerEntry("22J22", 765), CamelCardsJokerEntry("22222", 684), -1),
                        Triple(CamelCardsJokerEntry("22322", 765), CamelCardsJokerEntry("22422", 684), -1),
                        Triple(CamelCardsJokerEntry("22522", 765), CamelCardsJokerEntry("22422", 684), 1),
                        Triple(CamelCardsJokerEntry("22522", 765), CamelCardsJokerEntry("22622", 684), -1),
                        Triple(CamelCardsJokerEntry("22722", 765), CamelCardsJokerEntry("22622", 684), 1),
                        Triple(CamelCardsJokerEntry("22722", 765), CamelCardsJokerEntry("22822", 684), -1),
                        Triple(CamelCardsJokerEntry("22922", 765), CamelCardsJokerEntry("22822", 684), 1),
                        Triple(CamelCardsJokerEntry("22922", 765), CamelCardsJokerEntry("22T22", 684), -1),
                        Triple(CamelCardsJokerEntry("22Q22", 765), CamelCardsJokerEntry("22T22", 684), 1),
                        Triple(CamelCardsJokerEntry("22Q22", 765), CamelCardsJokerEntry("22K22", 684), -1),
                        Triple(CamelCardsJokerEntry("22A22", 765), CamelCardsJokerEntry("22K22", 684), 1),
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
                        Triple(CamelCardsEntry("KT88T", 220), CamelCardsEntry("KK677", 28), -1),
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
                val cardEntry = CamelCardsJokerEntry.parse(line)

                // then
                cardEntry.hand shouldBe "32T3K"
                cardEntry.bid shouldBe 765
            }

            "part 2" {
                // given
                val playedHands = """
                32T3K 765
                T55J5 684
                KK677 28
                KTJJT 220
                QQQJA 483
            """.trimIndent().lineSequence().toList()

                // when
                val totalWinnings = Day07().getTotalWinningsWithJoker(playedHands)

                // then
                totalWinnings shouldBe 5905
            }
        }
    }

    "solution" - {
        // given
        val playedHands = getResourceFileAsStringSequence("day07/input.txt")

        "part 1" {
            // when
            val totalWinnings = Day07().getTotalWinnings(playedHands)

            // then
            totalWinnings shouldBe 251_927_063
        }

        "part 2" {
            // when
            val totalWinnings = Day07().getTotalWinningsWithJoker(playedHands)

            // then
            totalWinnings shouldBe 255_632_664
        }
    }
}) {
}