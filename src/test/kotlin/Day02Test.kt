import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.shouldBe

class Day02Test : FreeSpec({

    "samples" - {
        "readCubeGame - one cube set" {
            // given
            val gameRecord = "Game 1: 3 green, 4 blue, 1 red"

            // when
            val game = CubeGame.readGameRecord(gameRecord)

            // then
            game.id shouldBe 1
            game.cubeSets.size shouldBe 1
            game.cubeSets shouldContain CubeSet(4, 1, 3)
        }

        "readCubeGame - different number of colors per cube set" {
            // given
            val gameRecord = "Game 3: 8 green, 6 blue, 20 red; 5 blue; 5 green, 1 red"

            // when
            val game = CubeGame.readGameRecord(gameRecord)

            // then
            game.id shouldBe 3
            game.cubeSets.size shouldBe 3

            game.cubeSets shouldContainInOrder listOf(CubeSet(6,20,8),
                CubeSet(5, 0,0),
                CubeSet(0, 1, 5))
        }

        "getMaxReds" {
            // given
            val gameRecord = "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red"

            // when
            val game = CubeGame.readGameRecord(gameRecord)

            // then
            game.getMaxReds() shouldBe 20
        }

        "getMaxBlues" {
            // given
            val gameRecord = "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red"

            // when
            val game = CubeGame.readGameRecord(gameRecord)

            // then
            game.getMaxBlues() shouldBe 6
        }

        "getMaxGreens" {
            // given
            val gameRecord = "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red"

            // when
            val game = CubeGame.readGameRecord(gameRecord)

            // then
            game.getMaxGreens() shouldBe 13
        }


        "part 1" - {
            // given
            val gameRecords = """
                Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
                Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
                Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
                Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
                Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
            """.trimIndent().lineSequence().toList()

            // when
            val sumOfImpossibleIds = Day02().getSumOfPossibleIds(gameRecords)

            // then
            sumOfImpossibleIds shouldBe 8
        }


    }

    "solution" - {

        "part 1" {
            // given
            val gameRecords = getResourceFileAsStringSequence("day02/input.txt")

            // when
            val sumOfImpossibleIds = Day02().getSumOfPossibleIds(gameRecords)

            // then
            sumOfImpossibleIds shouldBe 2162
        }

        "part 2" {

        }
    }
}) {
}