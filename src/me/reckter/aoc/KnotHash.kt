package me.reckter.aoc

import java.nio.charset.Charset

class KnotHash {

    companion object {

        fun oneHashRound(
            lengths: List<Int>,
            state: List<Int> = (0..255).toList(),
            skip: Int = 0,
            index: Int = 0
        ): HashRound {
            var internalState = state
            var internalSkip = skip
            var internalIndex = index
            lengths
                .filter { it <= state.size }
                .forEach { length ->
                    internalState = internalState.reverseSection(internalIndex, length)
                    internalIndex = (internalIndex + length + internalSkip) % state.size
                    internalSkip++
                }
            return HashRound(
                state = internalState,
                index = internalIndex,
                skip = internalSkip
            )
        }


        fun hash(input: List<Int>): String {
            val end = (1..64)
                .fold(
                    HashRound(
                        state = (0..255).toList(),
                        index = 0,
                        skip = 0
                    )
                ) { round, _ ->
                    oneHashRound(
                        lengths = input,
                        state = round.state,
                        skip = round.skip,
                        index = round.index
                    )
                }

            return end.state
                .buildConsecutiveGrouosOf(16)
                .map {
                    it.reduce { a, b ->
                        a xor b
                    }
                }
                .joinToString("") { it.toString(16).padStart(2, '0') }
        }

        fun hash(input: String): String
            = hash(
            input
                .toByteArray(Charset.forName("ASCII"))
                .map { it.toInt() } + listOf(17, 31, 73, 47, 23)
        )

    }
}


private fun <E> List<E>.reverseSection(index: Int, length: Int): List<E> {
    val switched = (this + this)
        .subList(index, index + length)
        .asReversed()

    val ret = if (index + length < this.size)
        this.subList(0, index) + switched + this.subList(index + length, this.size)
    else {
        val overshoot = (index + length) - this.size
        switched.takeLast(overshoot) + this.subList(overshoot, index) + switched.take(switched.size - overshoot)
    }
    return ret
}
