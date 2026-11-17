package com.example.litera.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.litera.app.Screen

private val SelectedColor = Color(0xFF2B2117)
private val UnselectedColor = Color(0xFF777D8F)
private val IndicatorColor = Color(0xFFFBE8CF)

data class BottomNavItem(
    val route: Screen,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(Screen.Search, "Explore", Icons.Filled.Explore, Icons.Outlined.Explore),
    BottomNavItem(Screen.Favorites, "Favorites", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder),
    BottomNavItem(Screen.ReadingList, "Reading List", Icons.Filled.Bookmarks, Icons.Outlined.Bookmarks),
)

/** True when [this] destination is one of the bottom bar tabs. */
fun NavDestination.isTopLevel(): Boolean =
    bottomNavItems.any { item -> hierarchy.any { it.hasRoute(item.route::class) } }

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    NavigationBar(containerColor = Color.White) {
        bottomNavItems.forEach { item ->
            val selected = currentDestination?.hierarchy
                ?.any { it.hasRoute(item.route::class) } == true

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        // Keep one copy of each tab and remember its scroll/state
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = SelectedColor,
                    selectedTextColor = SelectedColor,
                    indicatorColor = IndicatorColor,
                    unselectedIconColor = UnselectedColor,
                    unselectedTextColor = UnselectedColor
                )
            )
        }
    }
}
