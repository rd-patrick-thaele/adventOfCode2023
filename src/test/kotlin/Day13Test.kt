import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day13Test : FreeSpec({


    "samples" - {

        "vertical reflection" {
            // given
            val mirrorPattern = """
                #.##..##.
                ..#.##.#.
                ##......#
                ##......#
                ..#.##.#.
                ..##..##.
                #.#.##.#.
            """.trimIndent().lineSequence().toList()

            // when
            val mirrorSize  = MirrorPattern(mirrorPattern).getMirrorSize()

            // then
            mirrorSize shouldBe 5
        }

        "horizontal reflection" {
            // given
            val mirrorPattern = """
                #...##..#
                #....#..#
                ..##..###
                #####.##.
                #####.##.
                ..##..###
                #....#..#
            """.trimIndent().lineSequence().toList()

            // when
            val mirrorSize  = MirrorPattern(mirrorPattern).getMirrorSize()

            // then
            mirrorSize shouldBe 400
        }

        "horizontal reflection - padding at the end" {
            // given
            val mirrorPattern = """
                #.##.#..###.##.
                ##..###.####...
                ##..###.####...
                #.##.#..###.##.
                .#..#..##.#.#..
                ..##..##.####.#
                ##...##...#..#.
                ..##.......##.#
                .#..#..#...##..
                ######........#
                ##..####.#..#..
                #.##.#.#.#.###.
                ######.#.###.#.
                #....#.###.####
                ##..##.####.#.#
            """.trimIndent().lineSequence().toList()

            // when
            val mirrorSize  = MirrorPattern(mirrorPattern).getMirrorSize()

            // then
            mirrorSize shouldBe 200
        }

        "vertical reflection - 3 starting columns" {
            // given
            val mirrorPattern = """
                ...#....#....
                #...####...##
                #....##....##
                .#..####..#..
                #..#....#..##
                ..###..###...
                #..##..##..##
                ..#.#..#.....
                #.#.#..#.#.##
                #####..######
                ##.##..##.###
                #####..######
                #.##.##.##.##
            """.trimIndent().lineSequence().toList()

            // when
            val mirrorSize  = MirrorPattern(mirrorPattern).getMirrorSize()

            // then
            mirrorSize shouldBe 12
        }

        "horizontal reflection - end patting" {
            // given
            val mirrorPattern = """
                ....#.#
                #....#.
                #....#.
                ....#.#
                ..#.##.
                .#.#...
                ####...
                ..#.##.
                #......
                #.#..#.
                ##.##..
                ##.##..
                #.##.#.
                #......
                ..#.##.
            """.trimIndent().lineSequence().toList()

            // when
            val mirrorSize  = MirrorPattern(mirrorPattern).getMirrorSize()

            // then
            mirrorSize shouldBe 200
        }

        "horizontal reflection - end patting" {
            // given
            val mirrorPattern = """
                ###.#...##.#.##
                ....#.###.##.##
                .....####.#.###
                ##.###...#...#.
                ###.#..#####.#.
                .....#.####....
                ..#.####..####.
                ###.####.###.#.
                ...####..###..#
                ###.#..#.#...#.
                ...#.##.#..####
                ..#..##.##....#
                ......#########
                ######...#..###
                #######..#..###
                ......#########
                ..#..##.##....#
            """.trimIndent().lineSequence().toList()

            // when
            val mirrorSize  = MirrorPattern(mirrorPattern).getMirrorSize()

            // then
            mirrorSize shouldBe 1
        }

        "horizontal reflection - end patting" {
            // given
            val mirrorPattern = """
                #....####..####..
                #..##.#.#........
                .##.###.#########
                .##.##.#.#.####.#
                .##.#...#...##..#
                .##.#...#...##..#
                .##.##.#.#.####.#
                .##.###.#########
                #..##.#.#........
            """.trimIndent().lineSequence().toList()

            // when
            val mirrorSize  = MirrorPattern(mirrorPattern).getMirrorSize()

            // then
            mirrorSize shouldBe 500
        }

        "part 1" {
            // given
            val input = """
                #.##..##.
                ..#.##.#.
                ##......#
                ##......#
                ..#.##.#.
                ..##..##.
                #.#.##.#.

                #...##..#
                #....#..#
                ..##..###
                #####.##.
                #####.##.
                ..##..###
                #....#..#
            """.trimIndent().lineSequence().toList()

            // when
            val sum = Day13().sumNotes(input)

            // then
            sum shouldBe 405
        }
    }

    "solution" - {

        "part 1" {
            // given
            val input = getResourceFileAsStringSequence("day13/input.txt")

            // when
            val sum = Day13().sumNotes(input)

            // then
            sum shouldBe 33_735
        }

        "part 2" {
            // when
        }
    }
})