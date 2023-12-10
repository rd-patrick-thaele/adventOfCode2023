import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day10Test : FreeSpec({


    "samples" - {

        "findStartPosition" {
            //given
            val map = """
                .....
                .S-7.
                .|.|.
                .L-J.
                .....
            """.trimIndent().lineSequence().toList()
            val day = Day10(map)

            // when
            day.findStartPosition()

            // then
            day.getStartPosition() shouldBe PipePosition(1,1)
        }

        "findFirstPipeDirection" {
            //given
            val map = """
                .....
                .S-7.
                .|.|.
                .L-J.
                .....
            """.trimIndent().lineSequence().toList()
            val day = Day10(map)
            day.findStartPosition()

            // when
            val pipeDirection = day.findFirstPipeDirection()

            // then
            pipeDirection shouldBe PipeDirection.EAST
        }

        "stepsToOppositePosition" {
            //given
            val map = """
                .....
                .S-7.
                .|.|.
                .L-J.
                .....
            """.trimIndent().lineSequence().toList()
            val day = Day10(map)

            // when
            val steps = day.stepsToOppositePosition()

            // then
            steps shouldBe 4
        }

        "part 1" {
            //given
            val map = """
                ..F7.
                .FJ|.
                SJ.L7
                |F--J
                LJ...
            """.trimIndent().lineSequence().toList()
            val day = Day10(map)

            // when
            val steps = day.stepsToOppositePosition()

            // then
            steps shouldBe 8
        }
    }

    "solution" - {

        "part 1" {
            //given
            val map = getResourceFileAsStringSequence("day10/input.txt")
            val day = Day10(map)

            // when
            val steps = day.stepsToOppositePosition()

            // then
            steps shouldBe 6_714
        }

        "part 2" {
            // when
        }
    }
})