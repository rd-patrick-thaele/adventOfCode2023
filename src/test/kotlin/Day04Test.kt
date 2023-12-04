import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.shouldBe

class Day04Test : FreeSpec({

    "samples" - {
        "parse ScratchCard" {
            // given
            val cardDescription = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"

            // when
            val card = ScratchCard.parseDescription(cardDescription)

            // then
            card.id shouldBe 1
            card.winningNumbers shouldContainInOrder listOf(41, 48, 83, 86, 17)
            card.lotteryNumbers shouldContainInOrder listOf(83, 86, 6, 31, 17, 9, 48, 53)
        }

        "parse ScratchCard from puzzle input" {
            // given
            val cardDescription = "Card   5:  6 72 50 45 29 12 55 31 68 62 | 45 16 80 68 29 27 55  7 72 31 66 20 39  9 13 12 62 30 50  6 94 49 42 81  4"

            // when
            val card = ScratchCard.parseDescription(cardDescription)

            // then
            card.id shouldBe 5
            card.winningNumbers shouldContainInOrder listOf(6, 72, 50, 45, 29, 12, 55, 31, 68, 62)
            card.lotteryNumbers shouldContainInOrder listOf(45, 16, 80, 68, 29, 27, 55, 7, 72, 31, 66, 20, 39, 9, 13, 12, 62, 30, 50, 6, 94, 49, 42, 81, 4)
        }

        "calculatePoints" {
            // given
            val card = ScratchCard(42, listOf(41, 48, 83, 86, 17), listOf(83, 86, 6, 31, 17, 9, 48, 53))

            // when
            val points = card.calculatePoints()

            // then
            points shouldBe 8
        }

        "calculatePoints - no hit" {
            // given
            val card = ScratchCard(42, listOf(87, 83, 26, 28, 32), listOf(88, 30, 70, 12, 93, 22, 82, 36))

            // when
            val points = card.calculatePoints()

            // then
            points shouldBe 0
        }

        "part 1" {
            // given
            val cards = """
                Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
                Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
                Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
                Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
                Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
                Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
            """.trimIndent().lineSequence().toList()

            // when
            val points = Day04().getTotalPoints(cards)

            // then
            points shouldBe 13
        }
    }

    "solution" - {

        "part 1" {
            // given
            val cards = getResourceFileAsStringSequence("day04/input.txt")

            // when
            val points = Day04().getTotalPoints(cards)

            // then
            points shouldBe 32609
        }

        "part 2" {
            // when
        }
    }
}) {
}