package com.adriandeleon.friends

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    private companion object {
        private const val SIGN_UP = "signUp"
        private const val TIMELINE = "timeline"
        private const val CREATE_NEW_POST = "createNewPost"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FriendsTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = SIGN_UP) {
                        composable(SIGN_UP) {
                            SignUpScreen { signedUserId ->
                                navController.navigate("home/$signedUserId") {
                                    popUpTo(SIGN_UP) { inclusive = true }
                                }
                            }
                        }
                        composable(route = "home/{userId}") { backStackEntry ->
                            HomeScreen(
                                userId = backStackEntry.arguments?.getString("userId") ?: ""
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun HomeScreen(userId: String) {
        val navigationController = rememberNavController()
        Scaffold(bottomBar = {
            HomeScreenBottomNavigation(navigationController)
        }) { paddingValues ->
            NavHost(
                navController = navigationController,
                startDestination = TIMELINE,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(route = TIMELINE) {
                    TimelineScreen(userId = userId) {
                        navigationController.navigate(CREATE_NEW_POST)
                    }
                }
                composable(CREATE_NEW_POST) {
                    CreateNewPostScreen {
                        navigationController.navigateUp()
                    }
                }
                composable("People") {
                    People()
                }
            }
        }
    }

    @Composable
    private fun HomeScreenBottomNavigation(navigationController: NavHostController) {
        val screens = listOf(TIMELINE, "People")
        val currentDestination = currentDestination(navigationController)
        BottomNavigation {
            screens.forEach { screen ->
                BottomNavigationItem(
                    icon = { Icons.Default.Add },
                    label = { Text(text = screen) },
                    selected = currentDestination == screen,
                    onClick = {
                        navigationController.navigate(screen) {
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
