package com.example.litera.book.presentation.search

/** Category chips on the search screen. [subject] is the Open Library subject filter. */
enum class BookCategory(val label: String, val subject: String?) {
    ALL("All Books", null),
    FICTION("Fiction", "fiction"),
    NON_FICTION("Non-Fiction", "nonfiction"),
    SELF_HELP("Self-Help", "self-help"),
    SCIENCE("Science", "science")
}
