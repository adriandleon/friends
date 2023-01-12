package com.adriandeleon.friends.timeline

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.domain.exceptions.BackendException
import com.adriandeleon.friends.domain.post.Post
import com.adriandeleon.friends.domain.post.PostCatalog
import com.adriandeleon.friends.domain.user.InMemoryUserCatalog
import com.adriandeleon.friends.timeline.state.TimelineState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutor::class)
class FailTimelineLoadingTest {

    @Test
    fun `backend error`() {
        val userCatalog = InMemoryUserCatalog()
        val postCatalog = UnavailablePostCatalog()
        val viewModel = TimelineViewModel(userCatalog, postCatalog)

        viewModel.timelineFor(":irrelevant:")

        assertEquals(TimelineState.BackendError, viewModel.timelineState.value)
    }

    private class UnavailablePostCatalog : PostCatalog {
        override fun postsFor(userIds: List<String>): List<Post> {
            throw BackendException()
        }
    }
}