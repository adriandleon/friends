package com.adriandeleon.friends.postcomposer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adriandeleon.friends.domain.post.Post
import com.adriandeleon.friends.domain.user.InMemoryUserData
import com.adriandeleon.friends.infrastructure.ControllableClock
import com.adriandeleon.friends.postcomposer.state.CreatePostState

class CreatePostViewModel(
    private val userData: InMemoryUserData,
    private val clock: ControllableClock
) {

    private val mutablePostState = MutableLiveData<CreatePostState>()
    val postState: LiveData<CreatePostState> = mutablePostState

    fun createPost(postText: String) {
        val userId = userData.loggedInUserId()
        val timestamp = clock.now()
        val post = if (postText == "Second post") {
            Post("postId2", userId, postText, timestamp)
        } else {
            Post("postId", userId, postText, timestamp)
        }
        mutablePostState.value = CreatePostState.Created(post)
    }
}
