package com.adriandeleon.friends.timeline

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.app.TestDispatchers
import com.adriandeleon.friends.domain.post.OfflinePostCatalog
import com.adriandeleon.friends.domain.post.UnavailablePostCatalog
import com.adriandeleon.friends.domain.timeline.TimelineRepository
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
        val viewModel = TimelineViewModel(
            TimelineRepository(userCatalog, postCatalog),
            TestDispatchers()
        )

        viewModel.timelineFor(":irrelevant:")

        assertEquals(TimelineState.BackendError, viewModel.timelineState.value)
    }

    @Test
    fun `offline error`() {
        val userCatalog = InMemoryUserCatalog()
        val postCatalog = OfflinePostCatalog()
        val viewModel = TimelineViewModel(
            TimelineRepository(userCatalog, postCatalog),
            TestDispatchers()
        )

        viewModel.timelineFor(":irrelevant:")

        assertEquals(TimelineState.OfflineError, viewModel.timelineState.value)
    }
}