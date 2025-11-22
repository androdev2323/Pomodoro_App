package com.example.pomodoro.Navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.example.pomodoro.presentation.AppBlock.Presentation.AppBlockScreen
import com.example.pomodoro.presentation.AppBlock.Presentation.AppBlockScreenRoute
import com.example.pomodoro.presentation.HomeScreen.Task_Screen
import com.example.pomodoro.presentation.StopWatch.StopwatchScreen

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController, startDestination = HomeScreenRoutes.Home) {
        composable<HomeScreenRoutes.Home> {
            Task_Screen(navController = navController)
        }
        composable<HomeScreenRoutes.Stopwatch>(
            deepLinks = listOf(
                navDeepLink<HomeScreenRoutes.Stopwatch>(basePath = "pomodoroapp://stopwatch")
            )
        ) {

            StopwatchScreen()
        }
    }
}

@Composable
fun mainNavhost(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    Scaffold(
        bottomBar = {
            NavigationBar {
                PomodoroRoutes.mainroutes.forEach() { route ->
                    val isselected =
                        navBackStackEntry?.destination?.route == route.routeClassname

                    NavigationBarItem(
                        selected = isselected,
                        icon = {
                            Icon(
                                imageVector = if (isselected) route.selectedImage else route.unselectedImage,
                                contentDescription = "icon"
                            )
                        },
                        onClick = {
                            navController.navigate(route.routes){
                                popUpTo(navController.graph.findStartDestination().id){
                                    saveState=true
                                }
                                launchSingleTop=true
                                restoreState = true
                            }
                        },
                        label = {
                            Text(route.title)
                        }

                    )
                }

            }
        }
    ) {padding->
        NavHost(
            navController = navController,
            startDestination = PomodoroRoutes.home,
           modifier = Modifier
                .padding(
                    start = padding.calculateStartPadding(LocalLayoutDirection.current),
                    end = padding.calculateEndPadding(LocalLayoutDirection.current),
                    bottom = padding.calculateBottomPadding()
                )
                .background(MaterialTheme.colorScheme.background),
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) },
            popEnterTransition = { fadeIn(animationSpec = tween(300)) },
            popExitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            composable<PomodoroRoutes.home> {
                AppNavHost(navController = rememberNavController())
            }
            composable<PomodoroRoutes.appblock> {
                AppBlockScreenRoute()
            }
            composable<PomodoroRoutes.about> {
                Text(PomodoroRoutes.about.toString())
            }
        }
    }

}