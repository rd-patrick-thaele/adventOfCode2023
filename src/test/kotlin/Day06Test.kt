import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day06Test : FreeSpec({


    "samples" - {

        "getNbOfWaysToWin" {
            // given
            val race = Race(7, 9)

            // when
            val nbOfWaysToWin = race.getNumberOfWaysToWin()

            // then
            nbOfWaysToWin shouldBe  4
        }

        "part 1" {
            // given
            val races = listOf(Race(7, 9), Race(15, 40), Race(30, 200))

            // when
            val product = Day06().multiplyNbOfWins(races)

            // then
            product shouldBe 288
        }

        "part 2" {
            // given
            val races = listOf(Race(71530, 940200))

            // when
            val product = Day06().multiplyNbOfWins(races)

            // then
            product shouldBe 71503
        }
    }

    "solution" - {

        "part 1" {
            // given
            val races = listOf(Race(53, 313), Race(89, 1090), Race(76, 1214), Race(98, 1201))

            // when
            val product = Day06().multiplyNbOfWins(races)

            // then
            product shouldBe 5_133_600
        }

        "part 2" {
            // given
            val races = listOf(Race(53_897_698, 313_109_012_141_201L))

            // when
            val product = Day06().multiplyNbOfWins(races)

            // then
            product shouldBe 40_651_271
        }
    }
}) {
}