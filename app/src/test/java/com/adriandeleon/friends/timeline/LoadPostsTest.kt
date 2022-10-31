package com.adriandeleon.friends.timeline

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.timeline.state.TimelineState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutor::class)
class LoadPostsTest {

    @Test
    fun `no posts available`() {
        val viewModel = TimelineViewModel()

        viewModel.timelineFor("annaId")

        assertEquals(TimelineState.Posts(emptyList()), viewModel.timelineState.value)
    }
}