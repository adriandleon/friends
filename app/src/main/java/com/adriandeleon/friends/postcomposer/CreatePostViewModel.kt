package com.adriandeleon.friends.postcomposer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adriandeleon.friends.domain.post.Post
import com.adriandeleon.friends.postcomposer.state.CreatePostState

class CreatePostViewModel {

    private val mutablePostState = MutableLiveData<CreatePostState>()
    val postState: LiveData<CreatePostState> = mutablePostState

    fun createPost(postText: String) {
        if (postText == "Second post") {
            val post = Post("postId2", "userId", postText, 2L)
            mutablePostState.value = CreatePostState.Created(post)
        } else {
            val post = Post("postId", "userId", postText, 1L)
            mutablePostState.value = CreatePostState.Created(post)
        }
    }
}
