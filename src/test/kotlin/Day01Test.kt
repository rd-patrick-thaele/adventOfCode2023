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
    }

    "solution" - {
        "part 1" {
            // given
            val calibrationDoc = getResourceFileAsStringSequence("day01/input.txt")

            // when
            val sumOfCalibrationValues = Day01().sumOfCalibrationValues(calibrationDoc)

            // then
            sumOfCalibrationValues shouldBe 55386
        }
    }
}) {
}