import io.kotest.core.spec.style.FreeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe

class Day11Test : FreeSpec({


    "samples" - {

        //given
        val universe = """
                ...#......
                .......#..
                #.........
                ..........
                ......#...
                .#........
                .........#
                ..........
                .......#..
                #...#.....
            """.trimIndent().lineSequence().toList()

        val expandedUniverse = """
                ....#........
                .........#...
                #............
                .............
                .............
                ........#....
                .#...........
                ............#
                .............
                .............
                .........#...
                #....#.......
            """.trimIndent().lineSequence().toList()

        "expandUniverse" {

            // when
            val expanded = Day11().expandUniverse(universe)

            // then
            expanded shouldBe expandedUniverse
        }

        "findGalaxies" {
            // when
            val galaxies = Day11().findGalaxies(expandedUniverse)

            // then
            galaxies shouldContainAll listOf(
                GalaxyPosition(0, 4),
                GalaxyPosition(1, 9),
                GalaxyPosition(2, 0),
                GalaxyPosition(5, 8),
                GalaxyPosition(6, 1),
                GalaxyPosition(7, 12),
                GalaxyPosition(10, 9),
                GalaxyPosition(11, 0),
                GalaxyPosition(11, 5),
            )
        }

        "distance between galaxies" - {
            withData(
                listOf(
                    Triple(GalaxyPosition(6, 1), GalaxyPosition(11, 5), 9),
                    Triple(GalaxyPosition(0, 4), GalaxyPosition(10, 9), 15),
                    Triple(GalaxyPosition(2, 0), GalaxyPosition(7, 12), 17),
                    Triple(GalaxyPosition(11, 0), GalaxyPosition(11, 5), 5),
                    Triple(GalaxyPosition(11, 5), GalaxyPosition(11, 0), 5),
                    Triple(GalaxyPosition(7, 12), GalaxyPosition(10, 9), 6),
                )
            ) { (galaxy1, galaxy2, distance) ->
                galaxy1.distance(galaxy2) shouldBe distance
            }
        }

        "part 1" {
            // when
            val sum = Day11().sumOfGalaxyDistances(universe)

            // then
            sum shouldBe 374
        }
    }

    "solution" - {

        "part 1" {
            //given
            val universe = getResourceFileAsStringSequence("Day11/input.txt")

            // when
            val sum = Day11().sumOfGalaxyDistances(universe)

            // then
            sum shouldBe 9_723_824
        }

        "part 2" {
            // when
        }
    }
})