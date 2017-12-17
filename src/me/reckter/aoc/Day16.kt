package me.reckter.aoc

class Day16 : Day {
    override val day: Int = 16

    var moves: List<Operation>

    init {
        moves = loadInput(16)
            .first()
            .split(",")
            .matchAndParse<Operation>(
                "s(.*)" to { (numbrString) ->
                    val number = numbrString.toInt();
                    RotateOperation(number)
                },
                "x(.*?)/(.*?)" to { (firstPos, secondPos) ->
                    val firstNumber = firstPos.toInt();
                    val secondNumber = secondPos.toInt();
                    SwitchOperation(firstNumber, secondNumber)
                },
                "p(.*?)/(.*?)" to { (firstProgramm, secondProgramm) ->
                    PartnerOperation(firstProgramm, secondProgramm)
                }
            )
    }

    override fun solvePart1() {

        moves
            .fold("abcdefghijklmnop") { string, operation ->
                operation.apply(string)
            }
            .print("solution 1: ")
    }

    fun <A> ((A) -> A).chain(times: Int): (A) -> A = { a ->
        (1..times).fold(a) { a, i ->
            this(a)
        }
    }

    fun generateShortcutSwitchFunction(func: (String) -> String): (String) -> String {
        val start = "abcdefghijklmnop"
        val switchResult = func(start)

        val switchMap = start
            .mapIndexed { index, char ->
                index to switchResult.indexOf(char)
            }
            .toMap()

        return { input ->
            input
                .mapIndexed { index, char ->
                    char to (switchMap[index] ?: -1)
                }
                .sortedBy { it.second }
                .joinToString("") { it.first.toString() }
        }
    }

    fun fastGenerate(
        loops: Int,
        func: (String) -> String,
        generator: ((String) -> String) -> (String) -> String
    ): (String) -> String {
        return (1..Math.log(loops.toDouble()).toInt()).fold(func) { func, _ ->
            generator(func.chain(10))
        }
    }

    fun generateEasySwitchFunction(): (String) -> String {
        val switches = moves
            .filter { it !is PartnerOperation }

        return generateShortcutSwitchFunction { start ->
            switches.fold(start) { string, operation ->
                operation.apply(string)
            }
        }
    }

    fun generateShortcutPartnerFunction(func: (String) -> String): (String) -> String {
        val start = "abcdefghijklmnop"
        val switchResult = func(start)

        val switchMap = start
            .mapIndexed { index, char ->
                char to switchResult[index]
            }
            .toMap()

        return { input ->
            input
                .mapIndexed { index, char ->
                    switchMap[char] ?: '-'
                }
                .joinToString("") { it.toString() }
        }
    }

    fun generateEasyPartnerFunction(): (String) -> String {
        val switches = moves
            .filter { it is PartnerOperation }

        return generateShortcutPartnerFunction { start ->
            switches.fold(start) { string, operation ->
                operation.apply(string)
            }
        }
    }

    override fun solvePart2() {

        val switches = fastGenerate(1000000000, generateEasySwitchFunction(), ::generateShortcutSwitchFunction)

        val partners = fastGenerate(1000000000, generateEasyPartnerFunction(), ::generateShortcutPartnerFunction)

        val switched = switches("abcdefghijklmnop")
        val partnered = partners(switched)
        partnered
            .print("solution 2: ")
    }
}

abstract class Operation {
    abstract fun apply(input: String): String
}

class RotateOperation(val number: Int) : Operation() {
    override fun apply(input: String): String {
        return input.substring(input.length - number) + input.substring(0, input.length - number)
    }
}

class PartnerOperation(val firstProgramm: String, val secondProgramm: String) : Operation() {
    override fun apply(input: String): String {
        return input.replace(firstProgramm, "-").replace(secondProgramm, firstProgramm).replace("-", secondProgramm)
    }
}

class SwitchOperation(val firstNumber: Int, val secondNumber: Int) : Operation() {
    override fun apply(input: String): String {
        val list = input.toMutableList()
        val tmp = list[firstNumber]
        list[firstNumber] = list[secondNumber]
        list[secondNumber] = tmp
        return list.joinToString("")
    }
}

fun main(args: Array<String>) {
    val day = Day16()
    day.solvePart1()
    day.solvePart2()
}

