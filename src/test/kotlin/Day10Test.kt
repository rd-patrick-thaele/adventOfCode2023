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

        "part 2 - sample 1" {

            val map = """
                ...........
                .S-------7.
                .|F-----7|.
                .||.....||.
                .||.....||.
                .|L-7.F-J|.
                .|..|.|..|.
                .L--J.L--J.
                ...........
            """.trimIndent().lineSequence().toList()

            // when
            val enclosedTiles = Day10(map).getEnclosedTiles()

            // then
            enclosedTiles shouldBe 4

        }

        "part 2 - sample 2" {
            // given
            val map = """
                .F----7F7F7F7F-7....
                .|F--7||||||||FJ....
                .||.FJ||||||||L7....
                FJL7L7LJLJ||LJ.L-7..
                L--J.L7...LJS7F-7L7.
                ....F-J..F7FJ|L7L7L7
                ....L7.F7||L7|.L7L7|
                .....|FJLJ|FJ|F7|.LJ
                ....FJL-7.||.||||...
                ....L---J.LJ.LJLJ...
            """.trimIndent().lineSequence().toList()

            // when
            val enclosedTiles = Day10(map).getEnclosedTiles()

            // then
            enclosedTiles shouldBe 8

        }

        "part 2 - sample 3" {
            // given
            val map = """
                FF7FSF7F7F7F7F7F---7
                L|LJ||||||||||||F--J
                FL-7LJLJ||||||LJL-77
                F--JF--7||LJLJ7F7FJ-
                L---JF-JLJ.||-FJLJJ7
                |F|F-JF---7F7-L7L|7|
                |FFJF7L7F-JF7|JL---7
                7-L-JL7||F7|L7F-7F7|
                L.L7LFJ|||||FJL7||LJ
                L7JLJL-JLJLJL--JLJ.L
            """.trimIndent().lineSequence().toList()

            // when
            val enclosedTiles = Day10(map).getEnclosedTiles()

            // then
            enclosedTiles shouldBe 10
        }
    }

    "solution" - {

        // given
        val map = getResourceFileAsStringSequence("day10/input.txt")

        "part 1" {
            //given
            val day = Day10(map)

            // when
            val steps = day.stepsToOppositePosition()

            // then
            steps shouldBe 6_714
        }

        "part 2" {
            // when
            val enclosedTiles = Day10(map).getEnclosedTiles()

            // then
            enclosedTiles shouldBe 429
        }
    }
})