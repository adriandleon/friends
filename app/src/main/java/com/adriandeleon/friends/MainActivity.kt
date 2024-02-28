package com.adriandeleon.friends

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
                                navController.navigate("$TIMELINE/$signedUserId") {
                                    popUpTo(SIGN_UP) { inclusive = true }
                                }
                            }
                        }
                        composable(route = "$TIMELINE/{userId}") { backStackEntry ->
                            TimelineScreen(
                                userId = backStackEntry.arguments?.getString("userId") ?: ""
                            ) {
                                navController.navigate(CREATE_NEW_POST)
                            }
                        }
                        composable(CREATE_NEW_POST) {
                            CreateNewPostScreen {
                                navController.navigateUp()
                            }
                        }
                    }
                }
            }
        }
    }
}
