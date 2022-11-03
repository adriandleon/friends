package com.adriandeleon.friends.timeline

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.domain.post.Post
import com.adriandeleon.friends.infrastructure.builder.UserBuilder.Companion.aUser
import com.adriandeleon.friends.timeline.state.TimelineState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(InstantTaskExecutor::class)
class LoadPostsTest {

    @Test
    fun `no posts available`() {
        val viewModel = TimelineViewModel()

        viewModel.timelineFor("annaId")

        assertEquals(TimelineState.Posts(emptyList()), viewModel.timelineState.value)
    }

    @Test
    fun `posts available`() {
        val tim = aUser().withId("timId").build()
        val timPosts = listOf(Post("postId", tim.id, "post text", 1L))
        val viewModel = TimelineViewModel()

        viewModel.timelineFor(tim.id)

        assertEquals(TimelineState.Posts(timPosts), viewModel.timelineState.value)
    }
}