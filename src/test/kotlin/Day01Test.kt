import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day01Test : FreeSpec({

    "samples" - {
        "getCalibrationValue - start/beginning" {
            Day01().getCalibrationValue("1abc2") shouldBe 12
        }

        "getCalibrationValue - middle" {
            Day01().getCalibrationValue("pqr3stu8vwx") shouldBe 38
        }

        "getCalibrationValue - multiple digits" {
            Day01().getCalibrationValue("a1b2c3d4e5f") shouldBe 15
        }

        "getCalibrationValue - one digit" {
            Day01().getCalibrationValue("treb7uchet") shouldBe 77
        }

        "part 1" - {
            // given
            val calibrationDoc = """
                1abc2
                pqr3stu8vwx
                a1b2c3d4e5f
                treb7uchet
            """.trimIndent().lineSequence().toList()

            // when
            val sumOfCalibrationValues = Day01().sumOfCalibrationValues(calibrationDoc)

            // then
            sumOfCalibrationValues shouldBe 142
        }

        "getCalibrationValueSpelled - spelled at beginning and end" {
            Day01().getCalibrationValueSpelled("two1nine") shouldBe 29
        }

        "getCalibrationValueSpelled - spelled only" {
            Day01().getCalibrationValueSpelled("eightwothree") shouldBe 83
        }

        "getCalibrationValueSpelled - spelled with suffix and prefix" {
            Day01().getCalibrationValueSpelled("abcone2threexyz") shouldBe 13
        }

        "getCalibrationValueSpelled - spelled mixed" {
            Day01().getCalibrationValueSpelled("xtwone3four") shouldBe 24
        }

        "getCalibrationValueSpelled - digits first" {
            Day01().getCalibrationValueSpelled("4nineeightseven2") shouldBe 42
        }

        "getCalibrationValueSpelled - spelled and digit" {
            Day01().getCalibrationValueSpelled("zoneight234") shouldBe 14
        }

        "getCalibrationValueSpelled - ignore teens" {
            Day01().getCalibrationValueSpelled("7pqrstsixteen") shouldBe 76
        }

        "part 2" - {
            // given
            val calibrationDoc = """
                two1nine
                eightwothree
                abcone2threexyz
                xtwone3four
                4nineeightseven2
                zoneight234
                7pqrstsixteen
            """.trimIndent().lineSequence().toList()

            // when
            val sumOfCalibrationValues = Day01().sumOfCalibrationValuesSpelled(calibrationDoc)

            // then
            sumOfCalibrationValues shouldBe 281
        }
    }

    "solution" - {
        // given
        val calibrationDoc = getResourceFileAsStringSequence("day01/input.txt")

        "part 1" {
            // when
            val sumOfCalibrationValues = Day01().sumOfCalibrationValues(calibrationDoc)

            // then
            sumOfCalibrationValues shouldBe 55386
        }

        "part 2" {
            // when
            val sumOfCalibrationValues = Day01().sumOfCalibrationValuesSpelled(calibrationDoc)

            // then
            sumOfCalibrationValues shouldBe 54824
        }
    }
}) {
}