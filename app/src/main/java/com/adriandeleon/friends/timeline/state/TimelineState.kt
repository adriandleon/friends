package com.adriandeleon.friends.timeline.state

import com.adriandeleon.friends.domain.post.Post

sealed class TimelineState {
    object Loading : TimelineState()
    data class Posts(val posts: List<Post>) : TimelineState()
    object BackendError : TimelineState()
    object OfflineError : TimelineState()
}
