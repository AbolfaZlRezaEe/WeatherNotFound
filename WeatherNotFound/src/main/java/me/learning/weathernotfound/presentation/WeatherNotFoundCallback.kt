package me.learning.weathernotfound.presentation

interface WeatherNotFoundCallback<T, E> {

    fun onSuccess(response: T)

    fun onError(error: E)
}