package com.example.litera.app

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.litera.navigation.AppNavHost
import com.example.litera.navigation.BottomNavigationBar
import com.example.litera.navigation.isTopLevel

/** App shell: owns the NavController, shows the bottom bar only on tab screens. */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()

    // null on the very first frame -> show the bar to avoid a flicker
    val showBottomBar = backStackEntry?.destination?.isTopLevel() ?: true

    Scaffold(
        containerColor = Color(0xFFFAF9F6),
        bottomBar = {
            if (showBottomBar) BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                // Tell screens these insets are already handled, so they don't pad twice
                .consumeWindowInsets(innerPadding)
        )
    }
}
