package com.adriandeleon.friends.postcomposer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adriandeleon.friends.domain.post.PostRepository
import com.adriandeleon.friends.postcomposer.state.CreatePostState

class CreatePostViewModel(private val postRepository: PostRepository) {

    private val mutablePostState = MutableLiveData<CreatePostState>()
    val postState: LiveData<CreatePostState> = mutablePostState

    fun createPost(postText: String) {
        val result = postRepository.createNewPost(postText)
        mutablePostState.value = result
    }
}
