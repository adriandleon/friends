package com.adriandeleon.friends

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.adriandeleon.friends.postcomposer.CreateNewPostScreen
import com.adriandeleon.friends.signup.SignUpScreen
import com.adriandeleon.friends.timeline.TimelineScreen
import com.adriandeleon.friends.ui.theme.FriendsTheme

class MainActivity : ComponentActivity() {

    sealed class Screen(val route: String) {
        data object SignUp : Screen("signUp")

        data object Home : Screen("home/{userId}") {
            fun createRoute(userId: String) = "home/$userId"
        }

        sealed class Main(
            route: String,
            @StringRes val title: Int,
            @DrawableRes val icon: Int
        ) : Screen(route) {
            data object Timeline : Main("timeline", R.string.timeline, R.drawable.ic_timeline)

            data object People : Main("people", R.string.people, R.drawable.ic_people)
        }

        data object PostComposer : Screen("createNewPost")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FriendsTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.SignUp.route) {
                        composable(Screen.SignUp.route) {
                            SignUpScreen { signedUserId ->
                                navController.navigate(Screen.Home.createRoute(signedUserId)) {
                                    popUpTo(Screen.SignUp.route) { inclusive = true }
                                }
                            }
                        }
                        composable(route = Screen.Home.route) { backStackEntry ->
                            HomeScreen(userId = backStackEntry.arguments?.getString("userId") ?: "")
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun HomeScreen(userId: String) {
        val navigationController = rememberNavController()
        val homeNavigationScreens = listOf(Screen.Main.Timeline, Screen.Main.People)

        Scaffold(bottomBar = {
            HomeScreenBottomNavigation(navigationController, homeNavigationScreens)
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
                    People()
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

    @Composable
    fun People() {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = stringResource(id = R.string.people))
        }
    }
}
