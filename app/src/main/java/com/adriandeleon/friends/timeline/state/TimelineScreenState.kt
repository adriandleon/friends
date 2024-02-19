package com.adriandeleon.friends.timeline.state

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.adriandeleon.friends.domain.post.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimelineScreenState {
    var posts by mutableStateOf(emptyList<Post>())
    var loadedUserId by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var currentInfoMessage by mutableStateOf(0)

    fun updatePosts(newPosts: List<Post>) {
        isLoading = false
        this.posts = newPosts
    }

    fun shouldLoadPostsFor(userId: String): Boolean {
        return if (loadedUserId != userId) {
            loadedUserId = userId
            true
        } else {
            false
        }
    }

    fun showLoading() {
        isLoading = true
    }

    fun showInfoMessage(@StringRes infoMessage: Int) {
        isLoading = false
        if (currentInfoMessage != infoMessage) {
            currentInfoMessage = infoMessage
        }
    }
}