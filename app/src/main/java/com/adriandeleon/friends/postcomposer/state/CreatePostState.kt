package com.adriandeleon.friends.postcomposer.state

import com.adriandeleon.friends.domain.post.Post

sealed class CreatePostState {
    object Loading : CreatePostState()
    data class Created(val post: Post) : CreatePostState()
    object BackendError : CreatePostState()
    object Offline : CreatePostState()
}
