package me.reckter.aoc

class Day8 : Day {
    override val day: Int = 8

    val registers = mutableMapOf<String, Int>()
    var max = 0
    init {
        loadInput(8)
            .parseWithRegex("(.*?) (.*?) (.*?) if (.*?) (.*?) (.*?)")
            .mapIndexed() { index, (register, operation, value, comparatorRegister, comparison, comparisonValue) ->
                val valueOfComparissonRegister = registers.getOrDefault(comparatorRegister)
                if(getComparator(comparison)(valueOfComparissonRegister, Integer.parseInt(comparisonValue))) {
                    val oldValue = registers.getOrDefault(register)
                    val newValue = doAction(oldValue, operation, Integer.parseInt(value))
                    if(newValue > max) max = newValue
                    registers[register] = newValue
                    println("$operation $value $register from $oldValue to $newValue, because $comparatorRegister($valueOfComparissonRegister) $comparison $comparisonValue")
                } else {
                    println("skipping line $index: $register $operation $value if $comparatorRegister $comparison $comparisonValue, because $comparatorRegister is $valueOfComparissonRegister")
                }
            }
    }
    fun Map<String, Int>.getOrDefault(register: String): Int {
        return this.getOrDefault(register, 0)
    }
    override fun solvePart1() {
        registers
            .values
            .max()
            ?.print("solution 1:")
    }

    override fun solvePart2() {
        println("solution 2: $max")
    }
}

fun doAction(registerValue:Int, operation: String, value: Int): Int {
    return when(operation) {
        "inc" -> registerValue + value
        "dec" -> registerValue - value
        else -> error("unknown comparator $operation")
    }
}

fun getComparator(comparison: String): (Int, Int) -> Boolean =
    when (comparison) {
        ">" -> { a: Int, b: Int -> a > b }
        "<" -> { a: Int, b: Int -> a < b }
        ">=" -> { a: Int, b: Int -> a >= b }
        "<=" -> { a: Int, b: Int -> a <= b }
        "==" -> { a: Int, b: Int -> a == b }
        "!=" -> { a: Int, b: Int -> a != b }
        else -> error("unknown comparator $comparison")
    }


fun main(args: Array<String>) {
    val day = Day8()
    day.solvePart1()
    day.solvePart2()
}

