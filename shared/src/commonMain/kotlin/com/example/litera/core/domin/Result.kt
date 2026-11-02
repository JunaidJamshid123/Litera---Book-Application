package com.example.litera.core.domin
import com.example.litera.core.domin.Error
sealed interface Result<out D, out E : Error> {

    data class Success<out D>(
        val data: D
    ) : Result<D, Nothing>

    data class Error<out E : Error>(
        val error: E
    ) : Result<Nothing, E>
}
