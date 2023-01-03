package com.adriandeleon.friends.timeline

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.domain.post.Post
import com.adriandeleon.friends.domain.user.InMemoryUserCatalog
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
        val viewModel = TimelineViewModel(InMemoryUserCatalog())

        viewModel.timelineFor("tomId")

        assertEquals(TimelineState.Posts(emptyList()), viewModel.timelineState.value)
    }

    @Test
    fun `posts available`() {
        val tim = aUser().withId("timId").build()
        val timPosts = listOf(Post("postId", tim.id, "post text", 1L))
        val viewModel = TimelineViewModel(InMemoryUserCatalog())

        viewModel.timelineFor(tim.id)

        assertEquals(TimelineState.Posts(timPosts), viewModel.timelineState.value)
    }

    @Test
    fun `posts from friends`() {
        val anna = aUser().withId("annaId").build()
        val lucy = aUser().withId("lucyId").build()
        val lucyPosts = listOf(
            Post("post2", lucy.id, "post 2", 2L),
            Post("post1", lucy.id, "post 1", 1L),
        )
        val viewModel = TimelineViewModel(InMemoryUserCatalog())

        viewModel.timelineFor(anna.id)

        assertEquals(TimelineState.Posts(lucyPosts), viewModel.timelineState.value)
    }

    @Test
    fun `posts from friends along own`() {
        val lucy = aUser().withId("lucyId").build()
        val lucyPosts = listOf(
            Post("post2", lucy.id, "post 2", 2L),
            Post("post1", lucy.id, "post 1", 1L),
        )
        val sara = aUser().withId("saraId").build()
        val saraPosts = listOf(
            Post("post4", sara.id, "post 4", 4L),
            Post("post3", sara.id, "post 3", 3L),
        )
        val viewModel = TimelineViewModel(InMemoryUserCatalog())

        viewModel.timelineFor(sara.id)

        assertEquals(TimelineState.Posts(lucyPosts + saraPosts), viewModel.timelineState.value)
    }
}