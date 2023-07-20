package me.learning.weathernotfound.presentation

/**
 * Represents the final data callback for outside of the library.
 *
 * @param T is the data type of Response
 * @param E is the data type of Error
 */
interface WeatherNotFoundCallback<T, E> {

    fun onSuccess(response: T)

    fun onError(error: E)
}