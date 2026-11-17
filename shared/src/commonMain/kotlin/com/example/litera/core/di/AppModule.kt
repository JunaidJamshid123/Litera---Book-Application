package com.example.litera.core.di

import com.example.litera.book.data.remote.BookApi
import com.example.litera.book.data.remote.BookApiImpl
import com.example.litera.book.data.repository.BookRepositoryImpl
import com.example.litera.book.domain.repository.BookRepository
import com.example.litera.book.domain.usecase.GetBookDetailsUseCase
import com.example.litera.book.domain.usecase.GetBooksUseCase
import com.example.litera.book.domain.usecase.SearchBooksUseCase
import com.example.litera.book.presentation.details.BookDetailsViewModel
import com.example.litera.book.presentation.search.SearchViewModel
import com.example.litera.core.data.database.DatabaseFactory
import com.example.litera.core.data.network.HttpClientFactory
import com.example.litera.database.LiteraDatabase
import com.example.litera.favorites.data.repository.FavoritesRepositoryImpl
import com.example.litera.favorites.domain.repository.FavoritesRepository
import com.example.litera.favorites.domain.usecase.AddFavoriteUseCase
import com.example.litera.favorites.domain.usecase.GetFavoritesUseCase
import com.example.litera.favorites.domain.usecase.RemoveFavoriteUseCase
import com.example.litera.favorites.presentation.FavoritesViewModel
import com.example.litera.readinglist.data.repository.ReadingListRepositoryImpl
import com.example.litera.readinglist.domain.repository.ReadingListRepository
import com.example.litera.readinglist.domain.usecase.GetReadingListUseCase
import com.example.litera.readinglist.domain.usecase.UpdateReadingStatusUseCase
import com.example.litera.readinglist.presentation.ReadingListViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

/** Platform-specific pieces: HttpClientEngine + DatabaseDriverFactory (androidMain / jvmMain / iosMain). */
expect val platformModule: Module

val sharedModule = module {

    // ---------- Network ----------
    single { HttpClientFactory.create(get()) }

    // ---------- Local database ----------
    single { DatabaseFactory.create(get()) }                    // one LiteraDatabase for the whole app
    single { get<LiteraDatabase>().favoriteBookQueries }        // generated from FavoriteBook.sq
    single { get<LiteraDatabase>().readingListBookQueries }     // generated from ReadingListBook.sq

    // ---------- Books (remote) ----------
    singleOf(::BookApiImpl).bind<BookApi>()
    singleOf(::BookRepositoryImpl).bind<BookRepository>()
    factoryOf(::SearchBooksUseCase)
    factoryOf(::GetBooksUseCase)
    factoryOf(::GetBookDetailsUseCase)

    // ---------- Favorites (local) ----------
    singleOf(::FavoritesRepositoryImpl).bind<FavoritesRepository>()
    factoryOf(::GetFavoritesUseCase)
    factoryOf(::AddFavoriteUseCase)
    factoryOf(::RemoveFavoriteUseCase)

    // ---------- Reading list (local) ----------
    singleOf(::ReadingListRepositoryImpl).bind<ReadingListRepository>()
    factoryOf(::GetReadingListUseCase)
    factoryOf(::UpdateReadingStatusUseCase)

    // ---------- ViewModels ----------
    viewModelOf(::SearchViewModel)
    viewModelOf(::FavoritesViewModel)
    viewModelOf(::ReadingListViewModel)
    // workId is passed from the screen with koinViewModel { parametersOf(workId) }
    viewModel { params ->
        BookDetailsViewModel(
            workId = params.get(),
            getBookDetails = get(),
            getReadingList = get(),
            updateReadingStatus = get()
        )
    }
}
