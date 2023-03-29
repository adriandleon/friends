package com.adriandeleon.friends.postcomposer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adriandeleon.friends.domain.post.Post
import com.adriandeleon.friends.domain.user.InMemoryUserData
import com.adriandeleon.friends.postcomposer.state.CreatePostState

class CreatePostViewModel(
    private val userData: InMemoryUserData
) {

    private val mutablePostState = MutableLiveData<CreatePostState>()
    val postState: LiveData<CreatePostState> = mutablePostState

    fun createPost(postText: String) {
        val userId = userData.loggedInUserId()
        val timestamp = if (postText == "Second post") {
            ControllableClock(2L).now()
        } else {
            ControllableClock(1L).now()
        }
        val post = if (postText == "Second post") {
            Post("postId2", userId, postText, timestamp)
        } else {
            Post("postId", userId, postText, timestamp)
        }
        mutablePostState.value = CreatePostState.Created(post)
    }

    class ControllableClock(
        private val timestamp: Long
    ) {

        fun now(): Long {
            return timestamp
        }
    }
}
