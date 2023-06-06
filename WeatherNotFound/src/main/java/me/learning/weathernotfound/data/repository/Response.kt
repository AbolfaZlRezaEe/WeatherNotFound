package me.learning.weathernotfound.data.repository

internal sealed class Response<out V, out E>

internal data class Success<out V>(val value: V) : Response<V, Nothing>()

internal data class Failure<out E>(val error: E) : Response<Nothing, E>()

internal fun <V, E> Response<V, E>.isSuccessful(): Boolean {
    return this is Success
}

internal typealias IsSuccessful<E> = Failure<E>

internal inline fun <V, E> Response<V, E>.ifSuccessful(success: (V) -> Unit): IsSuccessful<E>? {
    return when (this) {
        is Success -> {
            success.invoke(this.value)
            null
        }
        is Failure -> {
            this
        }
    }
}

internal inline fun <V, E> Response<V, E>.ifNotSuccessful(failure: (E) -> Unit) {
    if (this is Failure) {
        failure.invoke(this.error)
    }
}