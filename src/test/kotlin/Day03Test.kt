import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe

class Day03Test : FreeSpec({

    "samples" - {
        "part 1" - {
            "findEngineParts" {
                // given
                val engineSchematic = """
                467..114..
                ...*......
                ..35..633.
                ......#...
                617*......
                .....+.58.
                ..592.....
                ......755.
                ...$.*....
                .664.598..
            """.trimIndent().lineSequence().toList()

                // when
                val testSubject = Day03(engineSchematic)
                val engineParts = testSubject.findEngineParts()

                // then
                engineParts shouldContainAll listOf(EnginePart('*', 2, 4),
                    EnginePart('#', 4, 7),
                    EnginePart('*', 5, 4),
                    EnginePart('+', 6, 6),
                    EnginePart('$', 9, 4),
                    EnginePart('*', 9, 6))
            }

            "sumPartNumbers - straight/single digit" {
                // given
                val engineSchematic = """
                ..4..
                .2*6.
                ..5..
            """.trimIndent().lineSequence().toList()

                // when
                val testSubject = Day03(engineSchematic)
                val sumOfPartNumbers = testSubject.sumPartNumbers()

                // then
                sumOfPartNumbers shouldBe 17
            }

            "sumPartNumbers - diagonal/single digit" {
                // given
                val engineSchematic = """
                .1.2.
                ..*..
                .3.4.
            """.trimIndent().lineSequence().toList()

                // when
                val testSubject = Day03(engineSchematic)
                val sumOfPartNumbers = testSubject.sumPartNumbers()

                // then
                sumOfPartNumbers shouldBe 10
            }

            "sumPartNumbers - straight/multi digit" {
                // given
                val engineSchematic = """
                ..1111..
                .33*222.
                .444....
            """.trimIndent().lineSequence().toList()

                // when
                val testSubject = Day03(engineSchematic)
                val sumOfPartNumbers = testSubject.sumPartNumbers()

                // then
                sumOfPartNumbers shouldBe 1810
            }

            "sumPartNumbers - diagonal/multi digit" {
                // given
                val engineSchematic = """
                .11.222.
                ...*....
                ..3.44..
            """.trimIndent().lineSequence().toList()

                // when
                val testSubject = Day03(engineSchematic)
                val sumOfPartNumbers = testSubject.sumPartNumbers()

                // then
                sumOfPartNumbers shouldBe 280
            }

            "sumPartNumbers - part in the corner" {
                // given
                val engineSchematic = """
                *11.
                .444
                ..3.
                555&
            """.trimIndent().lineSequence().toList()

                // when
                val testSubject = Day03(engineSchematic)
                val sumOfPartNumbers = testSubject.sumPartNumbers()

                // then
                sumOfPartNumbers shouldBe 1_013
            }

            "part 1" {
                // given
                val engineSchematic = """
                467..114..
                ...*......
                ..35..633.
                ......#...
                617*......
                .....+.58.
                ..592.....
                ......755.
                ...$.*....
                .664.598..
            """.trimIndent().lineSequence().toList()

                // when
                val testSubject = Day03(engineSchematic)
                val sumOfPartNumbers = testSubject.sumPartNumbers()

                // then
                sumOfPartNumbers shouldBe 4_361
            }
        }

        "part 2" - {
            "findEngineParts with filter" {
                // given
                val engineSchematic = """
                467..114..
                ...*......
                ..35..633.
                ......#...
                617*......
                .....+.58.
                ..592.....
                ......755.
                ...$.*....
                .664.598..
            """.trimIndent().lineSequence().toList()

                // when
                val testSubject = Day03(engineSchematic)
                val engineParts = testSubject.findEngineParts('*')

                // then
                engineParts shouldContainAll listOf(EnginePart('*', 2, 4),
                    EnginePart('*', 5, 4),
                    EnginePart('*', 9, 6))
            }

            "part 2" {
                // given
                val engineSchematic = """
                467..114..
                ...*......
                ..35..633.
                ......#...
                617*......
                .....+.58.
                ..592.....
                ......755.
                ...$.*....
                .664.598..
            """.trimIndent().lineSequence().toList()

                // when
                val testSubject = Day03(engineSchematic)
                val sumOfGearRatios = testSubject.sumGearRatios()

                // then
                sumOfGearRatios shouldBe 467_835
            }
        }

    }

    "solution" - {
        // given
        val engineSchematic = getResourceFileAsStringSequence("day03/input.txt")

        "part 1" {
            // when
            val testSubject = Day03(engineSchematic)
            val sumOfPartNumbers = testSubject.sumPartNumbers()

            // then
            sumOfPartNumbers shouldBe 537_832
        }

        "part 2" {
            // when
            val testSubject = Day03(engineSchematic)
            val sumOfGearRatios = testSubject.sumGearRatios()

            // then
            sumOfGearRatios shouldBe 81_939_900
        }
    }
}) {
}