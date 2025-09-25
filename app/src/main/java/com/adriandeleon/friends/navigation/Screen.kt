package com.adriandeleon.friends.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.adriandeleon.friends.R

sealed class Screen(val route: String) {
    data object SignUp : Screen("signUp")

    data object Home : Screen("home/{userId}") {
        const val userId = "userId"
        fun createRoute(userId: String) = "home/$userId"
    }

    sealed class Main(
        route: String,
        @field:StringRes val title: Int,
        @field:DrawableRes val icon: Int
    ) : Screen(route) {
        data object Timeline : Main("timeline", R.string.timeline, R.drawable.ic_timeline)

        data object Friends : Main("friends", R.string.friends, R.drawable.ic_friends)
    }

    data object PostComposer : Screen("createNewPost")
}