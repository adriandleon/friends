package com.adriandeleon.friends.postcomposer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adriandeleon.friends.app.CoroutineDispatchers
import com.adriandeleon.friends.domain.post.PostRepository
import com.adriandeleon.friends.postcomposer.state.CreatePostState
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreatePostViewModel(
    private val postRepository: PostRepository,
    private val dispatchers: CoroutineDispatchers
) : ViewModel() {

    private val mutablePostState = MutableLiveData<CreatePostState>()
    val postState: LiveData<CreatePostState> = mutablePostState

    fun createPost(postText: String) {
        viewModelScope.launch {
            mutablePostState.value = CreatePostState.Loading
            mutablePostState.value = withContext(dispatchers.background) {
                postRepository.createNewPost(postText)
            }
        }
    }
}
