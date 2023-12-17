import io.kotest.core.spec.style.FreeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.shouldBe

class Day15Test : FreeSpec({


    "samples" - {

        "calculateHash" - {
            withData(
                listOf(
                    Pair("rn=1", 30),
                    Pair("cm-", 253),
                    Pair("qp=3", 97),
                    Pair("cm=2", 47),
                    Pair("qp-", 14),
                    Pair("pc=4", 180),
                    Pair("ot=9", 9),
                    Pair("ab=5", 197),
                    Pair("pc-", 48),
                    Pair("pc=6", 214),
                    Pair("ot=7", 231),
                )
            ) { (input, hash) ->
                Day15.hash(input) shouldBe hash
            }
        }

        "part 1" {
            // given
            val initSequence = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"

            // when
            val sumOfHashes = Day15.sumOfHashes(initSequence)

            // then
            sumOfHashes shouldBe 1320
        }

        "readInitSequence" {
            // given
            val initSequence = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"
            val day15 = Day15()

            // when
            day15.readInitSequence(initSequence)

            // then
            day15.initSequence shouldContainInOrder listOf(
                Pair(Lens("rn", 1), LensOperation.ADD_LENS),
                Pair(Lens("cm", 0), LensOperation.REMOVE_LENS),
                Pair(Lens("qp", 3), LensOperation.ADD_LENS),
                Pair(Lens("cm", 2), LensOperation.ADD_LENS),
                Pair(Lens("qp", 0), LensOperation.REMOVE_LENS),
                Pair(Lens("pc", 4), LensOperation.ADD_LENS),
                Pair(Lens("ot", 9), LensOperation.ADD_LENS),
                Pair(Lens("ab", 5), LensOperation.ADD_LENS),
                Pair(Lens("pc", 0), LensOperation.REMOVE_LENS),
                Pair(Lens("pc", 6), LensOperation.ADD_LENS),
                Pair(Lens("ot", 7), LensOperation.ADD_LENS),
            )
        }

        "parseInitStep" - {
            withData(
                listOf(
                    Pair("rn=1", Pair(Lens("rn", 1), LensOperation.ADD_LENS)),
                    Pair("cm-", Pair(Lens("cm", 0), LensOperation.REMOVE_LENS)),
                    Pair("longlabel=9", Pair(Lens("longlabel", 9), LensOperation.ADD_LENS)),
                )
            ) { (initStep, parsed) ->
                Day15.parseInitStep(initStep) shouldBe parsed
            }
        }

        "sortLensesIntoBoxes" {
            // given
            val initSequence = listOf(
                Pair(Lens("rn", 1), LensOperation.ADD_LENS),
                Pair(Lens("cm", 0), LensOperation.REMOVE_LENS),
                Pair(Lens("qp", 3), LensOperation.ADD_LENS),
                Pair(Lens("cm", 2), LensOperation.ADD_LENS),
                Pair(Lens("qp", 0), LensOperation.REMOVE_LENS),
                Pair(Lens("pc", 4), LensOperation.ADD_LENS),
                Pair(Lens("ot", 9), LensOperation.ADD_LENS),
                Pair(Lens("ab", 5), LensOperation.ADD_LENS),
                Pair(Lens("pc", 0), LensOperation.REMOVE_LENS),
                Pair(Lens("pc", 6), LensOperation.ADD_LENS),
                Pair(Lens("ot", 7), LensOperation.ADD_LENS),
            )
            val day15 = Day15()
            day15.initSequence = initSequence

            // when
            day15.sortLensesIntoBoxes()

            // then
            day15.boxes.size shouldBe 3
            day15.boxes.values shouldContainAll listOf(
                LensBox(0, mutableListOf(Lens("rn", 1), Lens("cm", 2))),
                LensBox(1, mutableListOf()),
                LensBox(3, mutableListOf(Lens("ot", 7), Lens("ab", 5), Lens("pc", 6)))
            )
        }

        "findLens in box - found" {
            // given
            val box = LensBox(1, mutableListOf(Lens("rn", 1), Lens("cm", 2)))

            // when
            val index = box.findLens("cm")

            // then
            index shouldBe 1
        }

        "findLens in box - not found" {
            // given
            val box = LensBox(1, mutableListOf(Lens("rn", 1), Lens("cm", 2)))

            // when
            val index = box.findLens("cms")

            // then
            index shouldBe -1
        }

        "removeLens from box - first" {
            // given
            val box = LensBox(3, mutableListOf(Lens("ot", 7), Lens("ab", 5), Lens("pc", 6)))

            // when
            box.removeLens("ot")

            // then
            box.lenses.size shouldBe 2
            box.lenses shouldContainInOrder listOf(Lens("ab", 5), Lens("pc", 6))
        }

        "removeLens from box - middle" {
            // given
            val box = LensBox(3, mutableListOf(Lens("ot", 7), Lens("ab", 5), Lens("pc", 6)))

            // when
            box.removeLens("ab")

            // then
            box.lenses.size shouldBe 2
            box.lenses shouldContainInOrder listOf(Lens("ot", 7), Lens("pc", 6))
        }

        "removeLens from box - end" {
            // given
            val box = LensBox(3, mutableListOf(Lens("ot", 7), Lens("ab", 5), Lens("pc", 6)))

            // when
            box.removeLens("pc")

            // then
            box.lenses.size shouldBe 2
            box.lenses shouldContainInOrder listOf(Lens("ot", 7), Lens("ab", 5))
        }

        "removeLens from box - not found" {
            // given
            val box = LensBox(3, mutableListOf(Lens("ot", 7), Lens("ab", 5), Lens("pc", 6)))

            // when
            box.removeLens("notexist")

            // then
            box.lenses.size shouldBe 3
            box.lenses shouldContainInOrder listOf(Lens("ot", 7), Lens("ab", 5), Lens("pc", 6))
        }

        "addOrReplaceLens in box - add to the end" {
            // given
            val box = LensBox(3, mutableListOf(Lens("ot", 7), Lens("ab", 5)))
            val lensToAdd = Lens("pc", 6)

            // when
            box.addOrReplaceLens(lensToAdd)

            // then
            box.lenses.size shouldBe 3
            box.lenses shouldContainInOrder listOf(Lens("ot", 7), Lens("ab", 5), lensToAdd)
        }

        "addOrReplaceLens in box - replace at beginning" {
            // given
            val box = LensBox(3, mutableListOf(Lens("ot", 7), Lens("ab", 5), Lens("pc", 6)))
            val lensToReplace = Lens("ot", 4)

            // when
            box.addOrReplaceLens(lensToReplace)

            // then
            box.lenses.size shouldBe 3
            box.lenses shouldContainInOrder listOf(lensToReplace, Lens("ab", 5), Lens("pc", 6))
        }

        "addOrReplaceLens in box - replace at middle" {
            // given
            val box = LensBox(3, mutableListOf(Lens("ot", 7), Lens("ab", 5), Lens("pc", 6)))
            val lensToReplace = Lens("ab", 9)

            // when
            box.addOrReplaceLens(lensToReplace)

            // then
            box.lenses.size shouldBe 3
            box.lenses shouldContainInOrder listOf(Lens("ot", 7), lensToReplace, Lens("pc", 6))
        }

        "addOrReplaceLens in box - replace at end" {
            // given
            val box = LensBox(3, mutableListOf(Lens("ot", 7), Lens("ab", 5), Lens("pc", 6)))
            val lensToReplace = Lens("pc", 1)

            // when
            box.addOrReplaceLens(lensToReplace)

            // then
            box.lenses.size shouldBe 3
            box.lenses shouldContainInOrder listOf(Lens("ot", 7), Lens("ab", 5), lensToReplace)
        }

        "getFocusingPower of box"{
            // given
            val box = LensBox(3, mutableListOf(Lens("ot", 7), Lens("ab", 5), Lens("pc", 6)))

            // when
            val focusingPower = box.getFocusingPower()

            // then
            focusingPower shouldBe 140
        }

        "part 2" {
            // given
            val initSequence = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"

            // when
            val focusingPower = Day15.focusingPower(initSequence)

            // then
            focusingPower shouldBe 145
        }
    }

    "solution" - {
        // given
        val initSequence = getResourceFileAsStringSequence("day15/input.txt").first()

        "part 1" {
            // when
            val sumOfHashes = Day15.sumOfHashes(initSequence)

            // then
            sumOfHashes shouldBe 521_434
        }

        "part 2" {
            // when
            val focusingPower = Day15.focusingPower(initSequence)

            // then
            focusingPower shouldBe 248_279
        }
    }
})