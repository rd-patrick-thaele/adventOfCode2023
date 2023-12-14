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
            val tilted  = Day14().tiltReflectorDish(dish)

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
            val load = Day14().getTotalLoad(dish)

            // then
            load shouldBe 136
        }
    }

    "solution" - {

        "part 1" {
            // given
            val dish = getResourceFileAsStringSequence("day14/input.txt")

            // when
            val tiltedDish = Day14().tiltReflectorDish(dish)
            val load = Day14().getTotalLoad(tiltedDish)

            // then
            load shouldBe 110_090
        }

        "part 2" {
            // when
        }
    }
})