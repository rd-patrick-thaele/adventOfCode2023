import io.kotest.core.spec.style.FreeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.shouldBe

class Day16Test : FreeSpec({


    "samples" - {

        // given
        val layout = """
                .|...\....
                |.-.\.....
                .....|-...
                ........|.
                ..........
                .........\
                ..../.\\..
                .-.-/..|..
                .|....-|.\
                ..//.|....
            """.trimIndent().lineSequence().toList()

        "forwardBeam - hit horizontal splitter -" - {
            withData(
                listOf(
                    Pair(
                        Beam(BeamPosition(1, 2), BeamDirection.RIGHT),
                        listOf(Beam(BeamPosition(1, 3), BeamDirection.RIGHT))
                    ),
                    Pair(
                        Beam(BeamPosition(1, 2), BeamDirection.LEFT),
                        listOf(Beam(BeamPosition(1, 1), BeamDirection.LEFT))
                    ),
                    Pair(
                        Beam(BeamPosition(1, 2), BeamDirection.DOWNWARD),
                        listOf(
                            Beam(BeamPosition(1, 1), BeamDirection.LEFT),
                            Beam(BeamPosition(1, 3), BeamDirection.RIGHT)
                        )
                    ),
                    Pair(
                        Beam(BeamPosition(1, 2), BeamDirection.UPWARD),
                        listOf(
                            Beam(BeamPosition(1, 1), BeamDirection.LEFT),
                            Beam(BeamPosition(1, 3), BeamDirection.RIGHT)
                        )
                    ),
                )
            ) { (beam, nextBeams) ->
                Day16(layout).forwardBeam(beam) shouldContainAll nextBeams
            }
        }

        "forwardBeam - hit vertical splitter |" - {
            withData(
                listOf(
                    Pair(
                        Beam(BeamPosition(3, 8), BeamDirection.RIGHT),
                        listOf(
                            Beam(BeamPosition(4, 8), BeamDirection.DOWNWARD),
                            Beam(BeamPosition(2, 8), BeamDirection.UPWARD)
                        )
                    ),
                    Pair(
                        Beam(BeamPosition(3, 8), BeamDirection.LEFT),
                        listOf(
                            Beam(BeamPosition(4, 8), BeamDirection.DOWNWARD),
                            Beam(BeamPosition(2, 8), BeamDirection.UPWARD)
                        )
                    ),
                    Pair(
                        Beam(BeamPosition(3, 8), BeamDirection.DOWNWARD),
                        listOf(Beam(BeamPosition(4, 8), BeamDirection.DOWNWARD))
                    ),
                    Pair(
                        Beam(BeamPosition(3, 8), BeamDirection.UPWARD),
                        listOf(Beam(BeamPosition(2, 8), BeamDirection.UPWARD))
                    ),
                )
            ) { (beam, nextBeams) ->
                Day16(layout).forwardBeam(beam) shouldContainAll nextBeams
            }
        }

        "forwardBeam - hit mirror \\" - {
            withData(
                listOf(
                    Pair(
                        Beam(BeamPosition(1, 4), BeamDirection.RIGHT),
                        listOf(Beam(BeamPosition(2, 4), BeamDirection.DOWNWARD))
                    ),
                    Pair(
                        Beam(BeamPosition(1, 4), BeamDirection.LEFT),
                        listOf(Beam(BeamPosition(0, 4), BeamDirection.UPWARD))
                    ),
                    Pair(
                        Beam(BeamPosition(1, 4), BeamDirection.DOWNWARD),
                        listOf(Beam(BeamPosition(1, 5), BeamDirection.RIGHT))
                    ),
                    Pair(
                        Beam(BeamPosition(1, 4), BeamDirection.UPWARD),
                        listOf(Beam(BeamPosition(1, 3), BeamDirection.LEFT))
                    ),
                )
            ) { (beam, nextBeams) ->
                Day16(layout).forwardBeam(beam) shouldBe nextBeams
            }
        }

        "forwardBeam - hit mirror /" - {
            withData(
                listOf(
                    Pair(
                        Beam(BeamPosition(6, 4), BeamDirection.RIGHT),
                        listOf(Beam(BeamPosition(5, 4), BeamDirection.UPWARD))
                    ),
                    Pair(
                        Beam(BeamPosition(6, 4), BeamDirection.LEFT),
                        listOf(Beam(BeamPosition(7, 4), BeamDirection.DOWNWARD))
                    ),
                    Pair(
                        Beam(BeamPosition(6, 4), BeamDirection.DOWNWARD),
                        listOf(Beam(BeamPosition(6, 3), BeamDirection.LEFT))
                    ),
                    Pair(
                        Beam(BeamPosition(6, 4), BeamDirection.UPWARD),
                        listOf(Beam(BeamPosition(6, 5), BeamDirection.RIGHT))
                    ),
                )
            ) { (beam, nextBeams) ->
                Day16(layout).forwardBeam(beam) shouldBe nextBeams
            }
        }

        "forwardBeam - hit empty space" - {
            withData(
                listOf(
                    Pair(
                        Beam(BeamPosition(2, 1), BeamDirection.RIGHT),
                        listOf(Beam(BeamPosition(2, 2), BeamDirection.RIGHT))
                    ),
                    Pair(
                        Beam(BeamPosition(2, 1), BeamDirection.LEFT),
                        listOf(Beam(BeamPosition(2, 0), BeamDirection.LEFT))
                    ),
                    Pair(
                        Beam(BeamPosition(2, 1), BeamDirection.DOWNWARD),
                        listOf(Beam(BeamPosition(3, 1), BeamDirection.DOWNWARD))
                    ),
                    Pair(
                        Beam(BeamPosition(2, 1), BeamDirection.UPWARD),
                        listOf(Beam(BeamPosition(1, 1), BeamDirection.UPWARD))
                    ),
                )
            ) { (beam, nextBeams) ->
                Day16(layout).forwardBeam(beam) shouldBe nextBeams
            }
        }

        "forwardBeam - hit border of layout" - {
            withData(
                listOf(
                    Beam(BeamPosition(4, 9), BeamDirection.RIGHT),
                    Beam(BeamPosition(5, 0), BeamDirection.LEFT),
                    Beam(BeamPosition(9, 1), BeamDirection.DOWNWARD),
                    Beam(BeamPosition(0, 3), BeamDirection.UPWARD),
                )
            ) { beam ->
                Day16(layout).forwardBeam(beam) shouldBe emptyList()
            }
        }

        "part 1" {

            // when
            val countEnergizedFields = Day16(layout).countEnergizedFields()

            // then
            countEnergizedFields shouldBe 46
        }

        "part 2"{
            // when
            val maxEnergizedFields = Day16(layout).getMaxEnergizedFields()

            // then
            maxEnergizedFields shouldBe 51
        }
    }

    "solution" - {

        // given
        val layout = getResourceFileAsStringSequence("day16/input.txt")

        "part 1" {
            // when
            val countEnergizedFields = Day16(layout).countEnergizedFields()

            // then
            countEnergizedFields shouldBe 7_562
        }

        "part 2" {
            // when
            val maxEnergizedFields = Day16(layout).getMaxEnergizedFields()

            // then
            maxEnergizedFields shouldBe 7_793
        }
    }
})