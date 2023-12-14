import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day14Test : FreeSpec({


    "samples" - {

        "tiltReflectorDish" {
            // given
            val dish = """
                O....#....
                O.OO#....#
                .....##...
                OO.#O....O
                .O.....O#.
                O.#..O.#.#
                ..O..#O..O
                .......O..
                #....###..
                #OO..#....
            """.trimIndent().lineSequence().toList()

            // when
            val tilted  = Day14.tiltReflectorDish(dish)

            // then
            tilted shouldBe """
                OOOO.#.O..
                OO..#....#
                OO..O##..O
                O..#.OO...
                ........#.
                ..#....#.#
                ..O..#.O.O
                ..O.......
                #....###..
                #....#....
            """.trimIndent().lineSequence().toList()
        }

        "getTotalLoad" {
            // given
            val dish = """
                OOOO.#.O..
                OO..#....#
                OO..O##..O
                O..#.OO...
                ........#.
                ..#....#.#
                ..O..#.O.O
                ..O.......
                #....###..
                #....#....
            """.trimIndent().lineSequence().toList()

            // when
            val load = Day14.getTotalLoad(dish)

            // then
            load shouldBe 136
        }

        "rotateClockwise" {
            // given
            val dish = """
                O....#....
                O.OO#....#
                .....##...
                OO.#O....O
                .O.....O#.
                O.#..O.#.#
                ..O..#O..O
                .......O..
                #....###..
                #OO..#....
            """.trimIndent().lineSequence().toList()

            // when
            val rotatedDish = Day14.rotateClockwise(dish)

            // then
            rotatedDish shouldBe """
                ##..O.O.OO
                O....OO...
                O..O#...O.
                ......#.O.
                ......O.#.
                ##.#O..#.#
                .#.O...#..
                .#O.#O....
                .....#....
                ...O#.O.#.
            """.trimIndent().lineSequence().toList()
        }

        "spinCycle" {
            // given
            val dish = """
                O....#....
                O.OO#....#
                .....##...
                OO.#O....O
                .O.....O#.
                O.#..O.#.#
                ..O..#O..O
                .......O..
                #....###..
                #OO..#....
            """.trimIndent().lineSequence().toList()

            // when
            val cycle = Day14.spinCycle(dish)

            // then
            cycle shouldBe """
                .....#....
                ....#...O#
                ...OO##...
                .OO#......
                .....OOO#.
                .O#...O#.#
                ....O#....
                ......OOOO
                #...O###..
                #..OO#....
            """.trimIndent().lineSequence().toList()
        }

        "part 2" {
            var tiltedDish = """
                O....#....
                O.OO#....#
                .....##...
                OO.#O....O
                .O.....O#.
                O.#..O.#.#
                ..O..#O..O
                .......O..
                #....###..
                #OO..#....
            """.trimIndent().lineSequence().toList()

            // when
            repeat(1_000_000_000) {
                tiltedDish = Day14.spinCycle(tiltedDish)
            }
            val load = Day14.getTotalLoad(tiltedDish)

            // then
            load shouldBe 64
        }
    }

    "solution" - {

        // given
        val dish = getResourceFileAsStringSequence("day14/input.txt")

        "part 1" {
            // when
            val tiltedDish = Day14.tiltReflectorDish(dish)
            val load = Day14.getTotalLoad(tiltedDish)

            // then
            load shouldBe 110_090
        }

        "part 2" {
            // when
            var tiltedDish = dish
            repeat(1_000_000_000) {
                tiltedDish = Day14.spinCycle(tiltedDish)
            }
            val load = Day14.getTotalLoad(tiltedDish)

            // then
            load shouldBe 95_254
        }
    }
})