package me.ostafin.basicdaggerproject.util

import com.jakewharton.rxrelay3.Relay
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

fun Relay<Unit>.emitElement() = this.accept(Unit)

fun <T> Observable<T>.pairwise(): Observable<Pair<T, T>> =
    buffer(2, 1).map { (oldValue: T, newValue: T) ->
        Pair(oldValue, newValue)
    }

fun <T> Observable<T>.pairwiseWithIdenticalFirstPair(): Observable<Pair<T, T>> =
    this.take(1)
        .concatWith(this)
        .buffer(2, 1).map { (oldValue: T, newValue: T) ->
            Pair(oldValue, newValue)
        }

fun <A, B> Observable<Pair<A, B>>.mapToSecondOfPair(): Observable<B> =
    map { (_, second) -> second }

fun <A, B> Observable<Pair<A, B>>.mapToFirstOfPair(): Observable<A> =
    map { (first, _) -> first }

fun <A> Observable<A>.mapToUnit(): Observable<Unit> =
    map { Unit }

fun <T> Observable<T>.throttleMultipleClicks(): Observable<T> =
    this.throttleFirst(1, TimeUnit.SECONDS)