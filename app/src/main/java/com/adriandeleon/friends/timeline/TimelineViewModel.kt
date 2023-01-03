package com.adriandeleon.friends.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adriandeleon.friends.domain.post.InMemoryPostCatalog
import com.adriandeleon.friends.domain.user.InMemoryUserCatalog
import com.adriandeleon.friends.timeline.state.TimelineState

class TimelineViewModel {

    private val mutableTimelineState = MutableLiveData<TimelineState>()
    val timelineState: LiveData<TimelineState> = mutableTimelineState

    fun timelineFor(userId: String) {
        val userIds = listOf(userId) + InMemoryUserCatalog().followedBy(userId)
        val postsForUser = InMemoryPostCatalog().postsFor(userIds)
        mutableTimelineState.value = TimelineState.Posts(postsForUser)
    }
}
