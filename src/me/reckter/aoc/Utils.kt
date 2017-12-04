package me.reckter.aoc

import java.io.File
import java.nio.file.Files

fun loadInput(problem: Int, part: Int = 0): List<String> {
    if (part != 0)
        return readLines("${problem}_$part.txt")
    return readLines("$problem.txt")
}

fun readLines(file: String): List<String> {
    return Files.readAllLines(File(file).toPath())
}

fun List<String>.toIntegers(): List<Int>
    = this.map { Integer.parseInt(it) }

fun List<String>.toDoubles(): List<Double>
    = this.map { it.toDouble() }

fun List<String>.parseWithRegex(regexString: String): List<MatchResult.Destructured> =
    this
        .mapNotNull(Regex(regexString)::matchEntire)
        .map { it.destructured }

fun List<String>.categorizeWithRegex(vararg regexes: String): List<List<MatchResult.Destructured>> =
    regexes
        .map {
            this.parseWithRegex(it)
        }

fun <E> List<String>.matchWithRegexAndParse(vararg matchers: Pair<Regex, (MatchResult.Destructured) -> E>): List<E> =
    this
        .map { line ->
            matchers
                .mapNotNull { (regex, parser) ->
                    val match = regex.matchEntire(line)
                    match?.destructured?.to(parser)
                }
                .map { (match, parser) -> parser(match) }
                .first()
        }

fun <E> List<String>.matchAndParse(vararg matchers: Pair<String, (MatchResult.Destructured) -> E>): List<E> =
    this.matchWithRegexAndParse(*matchers.map { Regex(it.first) to it.second }.toTypedArray())

fun <E> List<E>.pairWithIndex(indexer: (index: Int) -> Int): List<Pair<E, E>>
    = this.mapIndexed { index, elem -> elem to this[indexer(index) % this.size] }

fun <E> List<E>.pairWithIndexAndSize(indexer: (index: Int, size: Int) -> Int): List<Pair<E, E>>
    = this.mapIndexed { index, elem -> elem to this[indexer(index, this.size) % this.size] }

fun Any.print(name: String) = println(name + this.toString())

fun <E> List<E>.allCombinations(includeSelf: Boolean = false): List<Pair<E, E>> =
    this
        .flatMap { it ->
            this.mapNotNull { other ->
                if (it != other && !includeSelf)
                    it to other
                else null
            }
        }
