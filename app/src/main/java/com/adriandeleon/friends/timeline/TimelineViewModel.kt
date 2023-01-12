package com.adriandeleon.friends.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adriandeleon.friends.domain.exceptions.BackendException
import com.adriandeleon.friends.domain.exceptions.ConnectionUnavailableException
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
        try {
            val userIds = listOf(userId) + userCatalog.followedBy(userId)
            val postsForUser = postCatalog.postsFor(userIds)
            mutableTimelineState.value = TimelineState.Posts(postsForUser)
        } catch (backendException: BackendException) {
            mutableTimelineState.value = TimelineState.BackendError
        } catch (offlineException: ConnectionUnavailableException) {
            mutableTimelineState.value = TimelineState.OfflineError
        }
    }
}
