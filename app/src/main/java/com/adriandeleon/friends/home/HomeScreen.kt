package com.adriandeleon.friends.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.adriandeleon.friends.navigation.Screen
import com.adriandeleon.friends.people.PeopleScreen
import com.adriandeleon.friends.postcomposer.CreateNewPostScreen
import com.adriandeleon.friends.timeline.TimelineScreen

@Composable
fun HomeScreen(userId: String) {
    val navigationController = rememberNavController()
    val homeNavigationScreens = listOf(Screen.Main.Timeline, Screen.Main.People)
    val currentDestination = currentDestination(navigationController = navigationController)
    val isMainDestination = homeNavigationScreens.any { it.route == currentDestination }

    Scaffold(bottomBar = {
        if (isMainDestination) {
            HomeScreenBottomNavigation(navigationController, homeNavigationScreens)
        }
    }) { paddingValues ->
        NavHost(
            navController = navigationController,
            startDestination = homeNavigationScreens.first().route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Screen.Main.Timeline.route) {
                TimelineScreen(userId = userId) {
                    navigationController.navigate(Screen.PostComposer.route)
                }
            }
            composable(route = Screen.PostComposer.route) {
                CreateNewPostScreen {
                    navigationController.navigateUp()
                }
            }
            composable(route = Screen.Main.People.route) {
                PeopleScreen()
            }
        }
    }
}

@Composable
private fun HomeScreenBottomNavigation(
    navigationController: NavHostController,
    homeNavigationScreens: List<Screen.Main>
) {
    val currentDestination = currentDestination(navigationController)
    BottomNavigation {
        homeNavigationScreens.forEach { screen ->
            val title = stringResource(id = screen.title)
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = title,
                    )
                },
                label = { Text(text = title) },
                selected = currentDestination == screen.route,
                onClick = {
                    navigationController.navigate(screen.route) {
                        popUpTo(navigationController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
private fun currentDestination(navigationController: NavHostController): String? {
    val navBackStackEntry by navigationController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}