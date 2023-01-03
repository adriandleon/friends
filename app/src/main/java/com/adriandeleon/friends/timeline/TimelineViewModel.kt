package com.adriandeleon.friends.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adriandeleon.friends.domain.post.InMemoryPostCatalog
import com.adriandeleon.friends.domain.user.Following
import com.adriandeleon.friends.timeline.state.TimelineState

class TimelineViewModel {

    private val mutableTimelineState = MutableLiveData<TimelineState>()
    val timelineState: LiveData<TimelineState> = mutableTimelineState

    fun timelineFor(userId: String) {
        val userIds = listOf(userId) + followedBy(userId)
        val postsForUser = InMemoryPostCatalog().postsFor(userIds)
        mutableTimelineState.value = TimelineState.Posts(postsForUser)
    }

    private fun followedBy(userId: String): List<String> {
        val followings = listOf(
            Following("saraId", "lucyId"),
            Following("annaId", "lucyId")
        )
        return followings
            .filter { it.userId == userId }
            .map { it.followedId }
    }
}
