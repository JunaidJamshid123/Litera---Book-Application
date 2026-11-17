package com.example.litera.core.domain

sealed interface Result<out D, out E : AppError> {

    data class Success<out D>(
        val data: D
    ) : Result<D, Nothing>

    data class Error<out E : AppError>(
        val error: E
    ) : Result<Nothing, E>
}

inline fun <T, E : AppError, R> Result<T, E>.map(transform: (T) -> R): Result<R, E> =
    when (this) {
        is Result.Success -> Result.Success(transform(data))
        is Result.Error -> this
    }

inline fun <T, E : AppError> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    if (this is Result.Success) action(data)
    return this
}

inline fun <T, E : AppError> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    if (this is Result.Error) action(error)
    return this
}

fun <T, E : AppError> Result<T, E>.asEmptyResult(): EmptyResult<E> = map { }
