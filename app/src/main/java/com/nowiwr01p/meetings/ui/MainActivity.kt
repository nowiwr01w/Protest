@file:OptIn(ExperimentalMaterialApi::class)

package com.nowiwr01p.meetings.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ModalBottomSheetValue.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nowiwr01p.core_ui.extensions.setSystemUiColor
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.MeetingsTheme
import com.nowiwr01p.core_ui.theme.mainBackgroundColor
import com.nowiwr01p.core_ui.ui.bottom_sheet.BottomSheet
import com.nowiwr01p.core_ui.ui.bottom_sheet.ShowBottomSheetHelper
import com.nowiwr01p.core_ui.ui.open_ilnks.OpenLinkObserver
import com.nowiwr01p.core_ui.ui.open_ilnks.OpenLinksHelper
import com.nowiwr01p.core_ui.ui.open_ilnks.openLink
import com.nowiwr01p.core_ui.ui.snack_bar.ShowSnackBarHelper
import com.nowiwr01p.core_ui.ui.snack_bar.ErrorSnackBar
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val navigator by inject<Navigator>()
    private val showSnackBarHelper by inject<ShowSnackBarHelper>()
    private val showBottomSheetHelper by inject<ShowBottomSheetHelper>()
    private val openLinksHelper by inject<OpenLinksHelper>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            val scope = rememberCoroutineScope()
            val scaffoldState = rememberScaffoldState()
            val bottomSheetState = rememberModalBottomSheetState(
                initialValue = Hidden,
                skipHalfExpanded = true
            )

            showSnackBarHelper.init(scope, scaffoldState)
            showBottomSheetHelper.init(scope, bottomSheetState)

            LaunchedEffect(Unit) {
                snapshotFlow { bottomSheetState.currentValue }
                    .collect {
                        if (it == Hidden) showBottomSheetHelper.closeBottomSheet()
                    }
            }

            val bottomSheetContent: @Composable () -> Unit = {
                showBottomSheetHelper.content.collectAsState(null).value.let { params ->
                    if (params != null) BottomSheet(params)
                }
                Spacer(modifier = Modifier.height(1.dp))
            }

            val errorShackBarContent: @Composable () -> Unit = {
                showSnackBarHelper.text.collectAsState("").value.let { text ->
                    AnimatedVisibility(
                        visible = text.isNotEmpty(),
                        enter = fadeIn() + slideInVertically(animationSpec = tween(350)),
                        exit = fadeOut() + slideOutVertically(animationSpec = tween(350))
                    ) {
                        ErrorSnackBar(text)
                    }
                }
            }

            val context = LocalContext.current
            OpenLinkObserver(openLinksHelper.openLink) {
                openLink(it, context)
            }

            MainActivityScreen(
                navigator = navigator,
                navController = navController,
                scaffoldState = scaffoldState,
                bottomSheetState = bottomSheetState,
                bottomSheetContent = bottomSheetContent,
                errorShackBarContent = errorShackBarContent
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
    scaffoldState: ScaffoldState,
    bottomSheetState: ModalBottomSheetState,
    bottomSheetContent: @Composable () -> Unit,
    errorShackBarContent: @Composable () -> Unit,
    setGraphs: @Composable () -> Unit
) {
    navigator.setNavController(navController)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    MeetingsTheme {
        ModalBottomSheetLayout(
            sheetState = bottomSheetState,
            sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
            sheetContent = { bottomSheetContent() }
        ) {
            Scaffold(
                scaffoldState = scaffoldState,
                bottomBar = {
                    if (navigator.currentScreen().showBottomNavigation) {
                        BottomNavigation(
                            backgroundColor = Color.White
                        ) {
                            navigator.getBottomNavigationItems().forEach { item ->
                                val selected = currentDestination?.hierarchy?.any {
                                    it.route == item.route
                                } == true
                                BottomNavigationItem(
                                    selected = selected,
                                    selectedContentColor = Color(0xFFFC4C4C),
                                    unselectedContentColor = MaterialTheme.colors.mainBackgroundColor,
                                    onClick = {
                                        navigator.onBottomNavigationSelected(item)
                                    },
                                    icon = {
                                        Icon(
                                            painter = painterResource(item.iconId),
                                            contentDescription = "Current Bottom Navigation Item",
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                )
                            }
                        }
                    } else {
                        Spacer(
                            modifier = Modifier
                                .navigationBarsPadding()
                                .imePadding()
                                .fillMaxWidth()
                        )
                    }
                }
            ) {
                setSystemUiColor(color = Color.White)
                Box(
                    modifier = Modifier.padding(it)
                ) {
                    setGraphs()
                    errorShackBarContent()
                }
            }
        }
    }
}