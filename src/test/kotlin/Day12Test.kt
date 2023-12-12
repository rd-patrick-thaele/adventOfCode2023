import io.kotest.core.spec.style.FreeSpec
import io.kotest.datatest.withData
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
    }

    "solution" - {

        "part 1" {
            // given
            val records = getResourceFileAsStringSequence("day12/input.txt")

            // when
            val count = Day12().getTotalArrangements(records)

            // then
            count shouldBe 6_981
        }

        "part 2" {

        }
    }
})