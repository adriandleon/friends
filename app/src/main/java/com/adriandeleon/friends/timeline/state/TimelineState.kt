package com.adriandeleon.friends.timeline.state

import com.adriandeleon.friends.domain.post.Post

sealed class TimelineState {
    data class Posts(val posts: List<Post>) : TimelineState()
}
