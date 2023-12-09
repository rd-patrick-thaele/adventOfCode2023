import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.shouldBe

class Day09Test : FreeSpec({


    "samples" - {

        "predictNextValue - abortion condition of recursive method" {
            //given
            val history = listOf(0, 0, 0, 0, 0, 0)

            // when
            val nextValue = Day09().predictNextValue(history)

            // then
            nextValue shouldBe 0
        }

        "predictNextValue - depth 2" {
            //given
            val history = listOf(0, 3, 6, 9, 12, 15)

            // when
            val nextValue = Day09().predictNextValue(history)

            // then
            nextValue shouldBe 18
        }

        "predictNextValue - depth 3" {
            //given
            val history = listOf(1, 3, 6, 10, 15, 21)

            // when
            val nextValue = Day09().predictNextValue(history)

            // then
            nextValue shouldBe 28
        }

        "predictNextValue - depth 4" {
            //given
            val history = listOf(10, 13, 16, 21, 30, 45)

            // when
            val nextValue = Day09().predictNextValue(history)

            // then
            nextValue shouldBe 68
        }

        "part 1" {
            // given
            val report = """
                0 3 6 9 12 15
                1 3 6 10 15 21
                10 13 16 21 30 45
            """.trimIndent().lineSequence().toList()

            // when
            val sum = Day09().sumOfPredictedNextValues(report)

            // then
            sum shouldBe 114
        }
    }

    "solution" - {


        "part 1" {
            // given
            val report = getResourceFileAsStringSequence("day09/input.txt")

            // when
            val sum = Day09().sumOfPredictedNextValues(report)

            // then
            sum shouldBe 1_993_300_041
        }

        "part 2" {
            // given
        }
    }
})