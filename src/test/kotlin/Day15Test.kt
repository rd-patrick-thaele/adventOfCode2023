import io.kotest.core.spec.style.FreeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class Day15Test : FreeSpec({


    "samples" - {

        "calculateHash" - {
            withData( listOf(
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
            )){ (input, hash) ->
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
    }

    "solution" - {


        "part 1" {
            // given
            val initSequence = getResourceFileAsStringSequence("day15/input.txt").first()

            // when
            val sumOfHashes = Day15.sumOfHashes(initSequence)

            // then
            sumOfHashes shouldBe 521_434
        }

        "part 2" {
            // when
        }
    }
})