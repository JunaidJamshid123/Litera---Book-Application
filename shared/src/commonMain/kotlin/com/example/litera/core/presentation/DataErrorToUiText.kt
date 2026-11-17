package com.example.litera.core.presentation

import com.example.litera.core.domain.DataError

fun DataError.toUiText(): UiText {
    val message = when (this) {
        DataError.Network.NO_INTERNET -> "No internet connection. Check your network and try again."
        DataError.Network.REQUEST_TIMEOUT -> "The request timed out. Please try again."
        DataError.Network.TOO_MANY_REQUESTS -> "Too many requests. Please wait a moment."
        DataError.Network.NOT_FOUND -> "We couldn't find what you were looking for."
        DataError.Network.SERVER_ERROR -> "The server is having trouble. Please try again later."
        DataError.Network.SERIALIZATION -> "We received unexpected data from the server."
        DataError.Network.UNKNOWN -> "Something went wrong. Please try again."
        DataError.Local.DISK_FULL -> "Your device storage is full."
        DataError.Local.UNKNOWN -> "Something went wrong. Please try again."
    }
    return UiText.DynamicString(message)
}
