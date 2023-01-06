@file:OptIn(ExperimentalMaterialApi::class)

package com.nowiwr01p.meetings.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ModalBottomSheetValue.*
import androidx.compose.runtime.*
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
import com.nowiwr01p.core_ui.bottom_sheet.BottomSheet
import com.nowiwr01p.core_ui.bottom_sheet.ShowBottomSheetHelper
import com.nowiwr01p.core_ui.extensions.setSystemUiColor
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.MeetingsTheme
import com.nowiwr01p.core_ui.theme.mainBackgroundColor
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val navigator by inject<Navigator>()
    private val showBottomSheetHelper by inject<ShowBottomSheetHelper>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            val scope = rememberCoroutineScope()
            val bottomSheetState = rememberModalBottomSheetState(Hidden)
            showBottomSheetHelper.init(scope, bottomSheetState)

            LaunchedEffect(Unit) {
                snapshotFlow { bottomSheetState.currentValue }
                    .collect {
                        if (it == HalfExpanded) showBottomSheetHelper.closeBottomSheet()
                    }
            }

            val bottomSheetContent: @Composable () -> Unit = {
                showBottomSheetHelper.content.collectAsState(null).value.let { content ->
                    if (content != null) BottomSheet(content)
                }
                Spacer(modifier = Modifier.height(48.dp))
            }

            MainActivityScreen(
                navigator = navigator,
                navController = navController,
                bottomSheetState = bottomSheetState,
                bottomSheetContent = bottomSheetContent
            ) {
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
    bottomSheetState: ModalBottomSheetState,
    bottomSheetContent: @Composable () -> Unit,
    setGraphs: @Composable () -> Unit
) {
    navigator.setNavController(navController)

    val systemUiController = rememberSystemUiController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    MeetingsTheme {
        ModalBottomSheetLayout(
            sheetState = bottomSheetState,
            sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
            sheetContent = { bottomSheetContent() }
        ) {
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
                setSystemUiColor()
                Column(
                    modifier = Modifier.padding(it)
                ) {
                    setGraphs()
                }
            }
        }
    }
}