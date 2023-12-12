import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.FreeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.shouldBe

class Day12Test : FreeSpec({


    "samples" - {

        "parse hot springs record" {
            // given
            val record = "???.### 1,1,3"

            // when
            val parsed = HotSpringsRecord.parse(record)

            // then
            parsed.brokenRecord shouldBe "???.###"
            parsed.damagedGroupSizes shouldContainInOrder listOf(1, 1, 3)
        }

        "findUnknowns" - {

            withData(
                listOf(
                    Pair("???.###", listOf(0, 1, 2)),
                    Pair(".??..??...?##.", listOf(1, 2, 5, 6, 10)),
                    Pair("?#?#?#?#?#?#?#?", listOf(0, 2, 4, 6, 8, 10, 12, 14)),
                )
            ) { (brokenRecord, indexes) ->
                HotSpringsRecord(brokenRecord, emptyList()).findUnknowns() shouldBe indexes
            }
        }

        "generateArrangements" - {

            withData(
                listOf(
                    Pair(HotSpringsRecord.parse("???.### 1,1,3"), listOf("#.#.###")),
                    Pair(
                        HotSpringsRecord.parse(".??..??...?##. 1,1,3"),
                        listOf(".#...#....###.", "..#..#....###.", ".#....#...###.", "..#...#...###.")
                    ),
                    Pair(HotSpringsRecord.parse("?#?#?#?#?#?#?#? 1,3,1,6"), listOf(".#.###.#.######")),
                    Pair(HotSpringsRecord.parse("????.#...#... 4,1,1"), listOf("####.#...#...")),
                    Pair(
                        HotSpringsRecord.parse("????.######..#####. 1,6,5"),
                        listOf(
                            "#....######..#####.",
                            ".#...######..#####.",
                            "..#..######..#####.",
                            "...#.######..#####."
                        )
                    ),
                    Pair(
                        HotSpringsRecord.parse("?###???????? 3,2,1"),
                        listOf(
                            ".###.##.#...",
                            ".###.##..#..",
                            ".###.##...#.",
                            ".###.##....#",
                            ".###..##.#..",
                            ".###..##..#.",
                            ".###..##...#",
                            ".###...##.#.",
                            ".###...##..#",
                            ".###....##.#",
                        )
                    ),
                )
            ) { (record, arrangements) ->
                val generated = record.generateArrangements()
                generated shouldContainAll arrangements
                generated.size shouldBe arrangements.size
            }

        }

        "countUnknownsToFill" - {

            withData(
                listOf(
                    Pair(HotSpringsRecord.parse("???.### 1,1,3"), 2),
                    Pair(HotSpringsRecord.parse(".??..??...?##. 1,1,3"), 3),
                    Pair(HotSpringsRecord.parse("?#?#?#?#?#?#?#? 1,3,1,6"), 4),
                    Pair(HotSpringsRecord.parse("????.#...#... 4,1,1"), 4),
                    Pair(HotSpringsRecord.parse("????.######..#####. 1,6,5"), 1),
                    Pair(HotSpringsRecord.parse("?###???????? 3,2,1"), 3),
                )
            ) { (record, count) ->
                record.countDamagedUnknowns() shouldBe count
            }
        }

        "part 1" {
            // given
            val records = """
                ???.### 1,1,3
                .??..??...?##. 1,1,3
                ?#?#?#?#?#?#?#? 1,3,1,6
                ????.#...#... 4,1,1
                ????.######..#####. 1,6,5
                ?###???????? 3,2,1
            """.trimIndent().lineSequence().toList()

            // when
            val count = Day12().getTotalArrangements(records)

            // then
            count shouldBe 21
        }

        "unfold" - {
            withData(
                listOf(
                    Pair(".# 1", ".#?.#?.#?.#?.# 1,1,1,1,1"),
                    Pair("???.### 1,1,3", "???.###????.###????.###????.###????.### 1,1,3,1,1,3,1,1,3,1,1,3,1,1,3"),
                )
            ) { (input, output) ->
                Day12().unfold(input) shouldBe output
            }
        }


        "!part 2" {
            // given
            val records = """
                ???.### 1,1,3
                .??..??...?##. 1,1,3
                ?#?#?#?#?#?#?#? 1,3,1,6
                ????.#...#... 4,1,1
                ????.######..#####. 1,6,5
                ?###???????? 3,2,1
            """.trimIndent().lineSequence().toList()

            // when
            val count = Day12().getTotalArrangementsUnfolded(records)

            // then
            count shouldBe 525_152
        }

        "getPotentialGroups" {
            // given
            val record = "???.###"

            // when
            val groups = HotSpringsRecord(record, emptyList()).getPotentialGroups()

            // then
            groups.size shouldBe 2
            groups shouldContainInOrder listOf(SpringGroup(0, "???"), SpringGroup(4, "###"))
        }

        "getNbOfArrangements" - {

            withData(
                listOf(
                    Pair("???.### 1,1,3", 1),
                    Pair(".??..??...?##. 1,1,3", 4),
                    Pair("?#?#?#?#?#?#?#? 1,3,1,6", 1),
                    Pair("????.#...#... 4,1,1", 1),
                    Pair("????.######..#####. 1,6,5", 4),
                    Pair("?###???????? 3,2,1", 10),
                    Pair("??.####?.??#?#?.?.?? 4,6", 1),
                    Pair("?.???.????????? 1,1,1,2,1", 121),
                    Pair("??#?.#??.?? 2,1,1", 6),
                )
            ) { (rawRecord, nbOfArrangements) ->
                val record = HotSpringsRecord.parse(rawRecord)
                record.getNbOfArrangements() shouldBe nbOfArrangements
            }

        }

        "getNbOfArrangements - unfolded" - {

            withData(
                listOf(
                    Pair("???.### 1,1,3", 1),
                    //Pair(".??..??...?##. 1,1,3", 16_384),
                    Pair("?#?#?#?#?#?#?#? 1,3,1,6", 1),
                    Pair("????.#...#... 4,1,1", 16),
                    Pair("????.######..#####. 1,6,5", 2500),
                    //Pair("?###???????? 3,2,1", 506_250),
                )
            ) { (rawRecord, nbOfArrangements) ->
                val record = HotSpringsRecord.parse(Day12().unfold(rawRecord))
                record.getNbOfArrangements() shouldBe nbOfArrangements
            }

        }

        "splitDamagedGroupSizes" - {

            withData(
                listOf(
                    Triple(0, emptyList(), listOf(1, 2, 3)),
                    Triple(1, listOf(1), listOf(2, 3)),
                    Triple(2, listOf(1, 2), listOf(3)),
                    Triple(3, listOf(1, 2, 3), emptyList())
                )
            ) { (index, resultLeft, resultRight) ->
                // given
                val damagedGroupSizes = listOf(1, 2, 3)

                // when
                val (left, right) = HotSpringsRecord("", emptyList()).splitDamagedGroupSizes(index, damagedGroupSizes)

                // then
                left shouldBe resultLeft
                right shouldBe resultRight
            }

        }


    }

    "solution" - {

        // given
        val records = getResourceFileAsStringSequence("day12/input.txt")

        "part 1" {

            // when
            val count = Day12().getTotalArrangements(records)

            // then
            count shouldBe 6_981
        }

        "part 2" {
            // when
            val count = Day12().getTotalArrangementsUnfolded(records)

            // then
            count shouldBe 6_981
        }
    }
})