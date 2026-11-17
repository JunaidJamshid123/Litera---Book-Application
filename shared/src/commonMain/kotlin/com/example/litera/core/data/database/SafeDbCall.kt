package com.example.litera.core.data.database

import com.example.litera.core.domain.DataError
import com.example.litera.core.domain.EmptyResult
import com.example.litera.core.domain.Result
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

/**
 * Runs a database write on the IO dispatcher and turns any failure into a
 * typed [DataError.Local] — the local-database twin of the network safeCall.
 */
suspend fun safeDbCall(block: () -> Unit): EmptyResult<DataError.Local> =
    withContext(Dispatchers.IO) {
        try {
            block()
            Result.Success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            val isDiskFull = e.message?.contains("full", ignoreCase = true) == true
            Result.Error(if (isDiskFull) DataError.Local.DISK_FULL else DataError.Local.UNKNOWN)
        }
    }
