import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day09Test : FreeSpec({


    "samples" - {

        "predictNextValue - abortion condition of recursive method" {
            //given
            val history = listOf(0, 0, 0, 0, 0, 0)

            // when
            val prediction = Day09().predictNextValue(history)

            // then
            prediction.futureValue shouldBe 0
            prediction.pastValue shouldBe 0
        }

        "predictNextValue - depth 2" {
            //given
            val history = listOf(0, 3, 6, 9, 12, 15)

            // when
            val prediction = Day09().predictNextValue(history)

            // then
            prediction.futureValue shouldBe 18
            prediction.pastValue shouldBe -3
        }

        "predictNextValue - depth 3" {
            //given
            val history = listOf(1, 3, 6, 10, 15, 21)

            // when
            val prediction = Day09().predictNextValue(history)

            // then
            prediction.futureValue shouldBe 28
            prediction.pastValue shouldBe 0
        }

        "predictNextValue - depth 4" {
            //given
            val history = listOf(10, 13, 16, 21, 30, 45)

            // when
            val prediction = Day09().predictNextValue(history)

            // then
            prediction.futureValue shouldBe 68
            prediction.pastValue shouldBe 5
        }

        "part 1" {
            // given
            val report = """
                0 3 6 9 12 15
                1 3 6 10 15 21
                10 13 16 21 30 45
            """.trimIndent().lineSequence().toList()

            // when
            val sum = Day09().sumOfPredictedFutureValues(report)

            // then
            sum shouldBe 114
        }

        "part 2" {
            // given
            val report = """
                0 3 6 9 12 15
                1 3 6 10 15 21
                10 13 16 21 30 45
            """.trimIndent().lineSequence().toList()

            // when
            val sum = Day09().sumOfPredictedPastValues(report)

            // then
            sum shouldBe 2
        }
    }

    "solution" - {
        // given
        val report = getResourceFileAsStringSequence("day09/input.txt")

        "part 1" {
            // when
            val sum = Day09().sumOfPredictedFutureValues(report)

            // then
            sum shouldBe 1_993_300_041
        }

        "part 2" {
            // when
            val sum = Day09().sumOfPredictedPastValues(report)

            // then
            sum shouldBe 1_038
        }
    }
})