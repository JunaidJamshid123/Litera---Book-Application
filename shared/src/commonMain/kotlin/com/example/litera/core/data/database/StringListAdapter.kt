package com.example.litera.core.data.database

import app.cash.sqldelight.ColumnAdapter
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

/**
 * SQLite has no list type. This stores a List<String> in a TEXT column as JSON,
 * e.g. ["James Clear","Another Author"], and turns it back into a list when read.
 */
object StringListAdapter : ColumnAdapter<List<String>, String> {

    private val serializer = ListSerializer(String.serializer())

    override fun decode(databaseValue: String): List<String> =
        if (databaseValue.isBlank()) emptyList()
        else Json.decodeFromString(serializer, databaseValue)

    override fun encode(value: List<String>): String =
        Json.encodeToString(serializer, value)
}
