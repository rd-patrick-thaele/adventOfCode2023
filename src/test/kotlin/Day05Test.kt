import io.kotest.core.spec.style.FreeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.shouldBe

class Day05Test : FreeSpec({


    "samples" - {

        "almanac mapping isInRange" - {
            // given
            val mapping = AlmanacMapping(98L, 50L, 2L)

            withData(
                listOf(
                    Pair(98L, true),
                    Pair(99L, true),
                    Pair(100L, false),
                    Pair(97L, false),
                )
            ) { (input, result) ->
                // expect
                mapping.isInRange(input) shouldBe result
            }
        }

        "almanac mapping getMapping" - {
            // given
            val mapping = AlmanacMapping(98L, 50L, 2L)

            withData(
                listOf(
                    Pair(98L, 50L),
                    Pair(99L, 51L),
                )
            ) { (input, result) ->
                // expect
                mapping.getMapping(input) shouldBe result
            }
        }

        "almanac category lookup - within ranges" {
            // given
            val almanacMappings = listOf(
                AlmanacMapping(98L, 50L, 2L),
                AlmanacMapping(50L, 52L, 48L)
            )
            val almanacCategory = AlmanacCategory("seed", "soil", almanacMappings)
            val seed = 53L

            // when
            val soil = almanacCategory.lookup(seed)

            // then
            soil shouldBe 55L
        }

        "almanac category lookup - not in ranges" {
            // given
            val almanacMappings = listOf(
                AlmanacMapping(98L, 50L, 2L),
                AlmanacMapping(50L, 52L, 48L)
            )
            val almanacCategory = AlmanacCategory("seed", "soil", almanacMappings)
            val seed = 10L

            // when
            val soil = almanacCategory.lookup(seed)

            // then
            soil shouldBe 10L
        }

        "readAlmanac" {
            // given
            val almanac = getResourceFileAsStringSequence("day05/sample.txt")

            // when
            val day05 = Day05.readAlmanac(almanac)

            // then
            day05.seeds shouldContainInOrder listOf(79L, 14L, 55L, 13L)
            day05.almanacCategories.size shouldBe 7

            day05.almanacCategories.values.toList()[2].sourceCategory shouldBe "fertilizer"
            day05.almanacCategories.values.toList()[2].destinationCategory shouldBe "water"

            day05.almanacCategories.values.toList()[5].mappings shouldContainInOrder listOf(AlmanacMapping(69, 0, 1),
                AlmanacMapping(0,1,69))
        }

        "getLowestLocationNumber" {
            // given
            val almanac = getResourceFileAsStringSequence("day05/sample.txt")
            val day05 = Day05.readAlmanac(almanac)

            // when
            val locationNumber = day05.getLowestLocationNumber()

            // then
            locationNumber shouldBe 35
        }
    }

    "solution" - {

        "part 1" {
            // given
            val almanac = getResourceFileAsStringSequence("day05/input.txt")
            val day05 = Day05.readAlmanac(almanac)

            // when
            val locationNumber = day05.getLowestLocationNumber()

            // then
            locationNumber shouldBe 309_796_150L
        }

        "part 2" {
            // when
        }
    }
}) {
}