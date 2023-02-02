package com.adriandeleon.friends.domain.timeline

import com.adriandeleon.friends.domain.exceptions.BackendException
import com.adriandeleon.friends.domain.exceptions.ConnectionUnavailableException
import com.adriandeleon.friends.domain.post.PostCatalog
import com.adriandeleon.friends.domain.user.UserCatalog
import com.adriandeleon.friends.timeline.state.TimelineState

class TimelineRepository(
    private val userCatalog: UserCatalog,
    private val postCatalog: PostCatalog
) {

    fun getTimelineFor(userId: String): TimelineState {
        return try {
            val userIds = listOf(userId) + userCatalog.followedBy(userId)
            val postsForUser = postCatalog.postsFor(userIds)
            TimelineState.Posts(postsForUser)
        } catch (backendException: BackendException) {
            TimelineState.BackendError
        } catch (offlineException: ConnectionUnavailableException) {
            TimelineState.OfflineError
        }
    }
}