package com.example.litera.app

import kotlinx.serialization.Serializable

/**
 * Every destination in the app. Routes are @Serializable so navigation
 * arguments (like workId) are type-safe and survive process death.
 */
sealed interface Screen {

    // Bottom bar tabs
    @Serializable
    data object Search : Screen

    @Serializable
    data object Favorites : Screen

    @Serializable
    data object ReadingList : Screen

    // Detail screen (opened on top of a tab, no bottom bar)
    @Serializable
    data class BookDetails(val workId: String) : Screen
}
