package com.nowiwr01p.meetings.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.BottomNavigation
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.MeetingsTheme
import com.nowiwr01p.core_ui.theme.mainBackgroundColor
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val navigator by inject<Navigator>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            MainActivityScreen(navigator, navController) {
                NavHost(
                    navController = navController,
                    startDestination = "splash_screen",
                    builder = {
                        navigator.setGraphs(this)
                    }
                )
            }
        }
    }
}

@Composable
fun MainActivityScreen(
    navigator: Navigator,
    navController: NavHostController,
    setGraphs: @Composable () -> Unit
) {
    navigator.setNavController(navController)

    val systemUiController = rememberSystemUiController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    MeetingsTheme {
        Scaffold(
            bottomBar = {
                if (navigator.currentScreen().showBottomNavigation) {
                    BottomNavigation(
                        contentPadding = rememberInsetsPaddingValues(
                            LocalWindowInsets.current.navigationBars
                        ),
                        backgroundColor = Color.White
                    ) {
                        navigator.getBottomNavigationItems().forEach { item ->
                            val selected = currentDestination?.hierarchy?.any {
                                it.route == item.route
                            } == true
                            BottomNavigationItem(
                                selected = selected,
                                onClick = { navigator.onBottomNavigationSelected(item) },
                                icon = {
                                    Icon(
                                        painter = painterResource(item.iconId),
                                        contentDescription = "Current Bottom Navigation Item",
                                        modifier = Modifier.padding(16.dp)
                                    )
                                },
                                selectedContentColor = Color(0xFFFC4C4C),
                                unselectedContentColor = MaterialTheme.colors.mainBackgroundColor
                            )
                        }
                    }
                } else {
                    Spacer(
                        Modifier
                            .navigationBarsPadding()
                            .imePadding()
                            .fillMaxWidth()
                    )
                }
            }
        ) {
            systemUiController.setStatusBarColor(
                color = MaterialTheme.colors.surface,
            )
            Column(
                modifier = Modifier.padding(it)
            ) {
                setGraphs()
            }
        }
    }
}