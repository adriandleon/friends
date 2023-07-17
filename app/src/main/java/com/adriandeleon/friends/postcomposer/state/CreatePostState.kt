package com.adriandeleon.friends.postcomposer.state

import com.adriandeleon.friends.domain.post.Post

sealed class CreatePostState {
    data class Created(val post: Post) : CreatePostState()
    object BackendError : CreatePostState()
}
