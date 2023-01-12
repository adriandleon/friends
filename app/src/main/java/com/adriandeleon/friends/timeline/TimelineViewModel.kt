package com.adriandeleon.friends.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adriandeleon.friends.domain.post.PostCatalog
import com.adriandeleon.friends.domain.user.UserCatalog
import com.adriandeleon.friends.timeline.state.TimelineState

class TimelineViewModel(
    private val userCatalog: UserCatalog,
    private val postCatalog: PostCatalog
) {

    private val mutableTimelineState = MutableLiveData<TimelineState>()
    val timelineState: LiveData<TimelineState> = mutableTimelineState

    fun timelineFor(userId: String) {
        val userIds = listOf(userId) + userCatalog.followedBy(userId)
        val postsForUser = postCatalog.postsFor(userIds)
        mutableTimelineState.value = TimelineState.Posts(postsForUser)
    }
}
