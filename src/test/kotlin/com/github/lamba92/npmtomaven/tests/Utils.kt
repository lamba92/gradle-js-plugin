package com.github.lamba92.npmtomaven.tests

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.util.stream.Stream
import kotlin.streams.asStream

fun <T> provideArguments(action: SequenceScope<T>.(ExtensionContext) -> Unit) = ArgumentsProvider {
    sequence { action(it) }.map { Arguments.of(it) }.asStream()
}

fun <T> provideArguments(arguments: List<T>) =
    provideArguments(arguments.asSequence())

fun <T> provideArguments(arguments: Stream<T>) =
    ArgumentsProvider { arguments.map { Arguments.of(it) } }

fun <T> provideArguments(arguments: Sequence<T>) =
    ArgumentsProvider { arguments.map { Arguments.of(it) }.asStream() }

fun <T> provideArguments(vararg arguments: T) =
    provideArguments(arguments.asSequence())

data class NpmCoordinates(val name: String, val version: String) {
    override fun toString() = "$name:$version"
}

object TestPackages : ArgumentsProvider by provideArguments(
//    NpmCoordinates("karma", "6.3.9"),
    NpmCoordinates("lodash", "4.17.21"),
//    NpmCoordinates("chalk", "5.0.0"),
//    NpmCoordinates("request", "2.88.2"),
//    NpmCoordinates("commander", "8.3.0"),
//    NpmCoordinates("react", "17.0.2"),
    NpmCoordinates("@spatial/turf", "1.0.5")
)
