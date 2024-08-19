package com.adriandeleon.friends.timeline

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.app.TestDispatchers
import com.adriandeleon.friends.domain.post.InMemoryPostCatalog
import com.adriandeleon.friends.domain.timeline.TimelineRepository
import com.adriandeleon.friends.domain.user.InMemoryUserCatalog
import com.adriandeleon.friends.timeline.state.TimelineState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutor::class)
class RenderingTimelineStatesTest {

    private val timelineRepository = TimelineRepository(
        InMemoryUserCatalog(),
        InMemoryPostCatalog()
    )
    private val viewModel = TimelineViewModel(timelineRepository, TestDispatchers())

    @Test
    fun `timeline states delivered to an observer in particular order`() {
        val renderedState = mutableListOf<TimelineState?>()
        viewModel.timelineState.observeForever { renderedState.add(it) }

        viewModel.timelineFor(":irrelevant:")

        assertEquals(listOf(TimelineState.Loading, TimelineState.Posts(emptyList())), renderedState)
    }
}