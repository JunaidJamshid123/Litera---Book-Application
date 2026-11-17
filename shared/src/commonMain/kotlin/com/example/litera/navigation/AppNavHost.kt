package com.example.litera.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.litera.app.Screen
import com.example.litera.book.presentation.details.BookDetailsScreen
import com.example.litera.book.presentation.search.SearchScreen
import com.example.litera.favorites.presentation.FavoritesScreen
import com.example.litera.readinglist.presentation.ReadingListScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Search,
        modifier = modifier
    ) {
        composable<Screen.Search> {
            SearchScreen(
                onBookClick = { book ->
                    navController.navigate(Screen.BookDetails(workId = book.id))
                }
            )
        }

        composable<Screen.Favorites> {
            FavoritesScreen(
                onBookClick = { book ->
                    navController.navigate(Screen.BookDetails(workId = book.id))
                }
            )
        }

        composable<Screen.ReadingList> {
            ReadingListScreen(
                onBookClick = { bookId ->
                    navController.navigate(Screen.BookDetails(workId = bookId))
                }
            )
        }

        composable<Screen.BookDetails> { backStackEntry ->
            val route = backStackEntry.toRoute<Screen.BookDetails>()
            BookDetailsScreen(
                workId = route.workId,
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}
