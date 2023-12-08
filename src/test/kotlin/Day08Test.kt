import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day08Test : FreeSpec({


    "samples" - {

        "parseDesertMap" {
            //given
            val desert = """
                AAA = (BBB, CCC)
                BBB = (DDD, EEE)
                CCC = (ZZZ, GGG)
                DDD = (DDD, DDD)
                EEE = (EEE, EEE)
                GGG = (GGG, GGG)
                ZZZ = (ZZZ, ZZZ)
            """.trimIndent().lineSequence().toList()

            // when
            val desertNetwork = Day08().parseDesertNetwork(desert)

            // then
            desertNetwork.size shouldBe 7
            desertNetwork["AAA"] shouldBe DesertNode("BBB", "CCC")
            desertNetwork["BBB"] shouldBe DesertNode("DDD", "EEE")
            desertNetwork["ZZZ"] shouldBe DesertNode("ZZZ", "ZZZ")
        }

        "part 1 - no repeat" {
            // given
            val input = """
                RL

                AAA = (BBB, CCC)
                BBB = (DDD, EEE)
                CCC = (ZZZ, GGG)
                DDD = (DDD, DDD)
                EEE = (EEE, EEE)
                GGG = (GGG, GGG)
                ZZZ = (ZZZ, ZZZ)
            """.trimIndent().lineSequence().toList()

            // when
            val totalSteps = Day08().countTotalSteps(input)

            // then
            totalSteps shouldBe 2
        }

        "part 1 - repeat directions" {
            // given
            val input = """
                LLR

                AAA = (BBB, BBB)
                BBB = (AAA, ZZZ)
                ZZZ = (ZZZ, ZZZ)
            """.trimIndent().lineSequence().toList()

            // when
            val totalSteps = Day08().countTotalSteps(input)

            // then
            totalSteps shouldBe 6
        }
    }

    "solution" - {

        "part 1" {
            // given
            val input = getResourceFileAsStringSequence("day08/input.txt")

            // when
            val totalSteps = Day08().countTotalSteps(input)

            // then
            totalSteps shouldBe 23_147
        }

        "part 2" {
            // when
        }
    }
})