package com.adriandeleon.friends.timeline

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.app.TestDispatchers
import com.adriandeleon.friends.domain.post.InMemoryPostCatalog
import com.adriandeleon.friends.domain.post.Post
import com.adriandeleon.friends.domain.timeline.TimelineRepository
import com.adriandeleon.friends.domain.user.Following
import com.adriandeleon.friends.domain.user.InMemoryUserCatalog
import com.adriandeleon.friends.infrastructure.builder.UserBuilder.Companion.aUser
import com.adriandeleon.friends.timeline.state.TimelineState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(InstantTaskExecutor::class)
class LoadTimelineTest {

    private val lucy = aUser().withId("lucyId").build()
    private val sara = aUser().withId("saraId").build()
    private val anna = aUser().withId("annaId").build()
    private val tim = aUser().withId("timId").build()

    private val timPosts = listOf(
        Post("postId", tim.id, "post text", 1L)
    )
    private val lucyPosts = listOf(
        Post("post2", lucy.id, "post 2", 2L),
        Post("post1", lucy.id, "post 1", 1L),
    )
    private val saraPosts = listOf(
        Post("post4", sara.id, "post 4", 4L),
        Post("post3", sara.id, "post 3", 3L),
    )

    private val availablePosts = timPosts + lucyPosts + saraPosts

    @Test
    fun `no posts available`() {
        val userCatalog = InMemoryUserCatalog()
        val postCatalog = InMemoryPostCatalog(availablePosts)
        val viewModel = TimelineViewModel(
            TimelineRepository(userCatalog, postCatalog),
            TestDispatchers()
        )

        viewModel.timelineFor("tomId")

        assertEquals(TimelineState.Posts(emptyList()), viewModel.timelineState.value)
    }

    @Test
    fun `posts available`() {
        val userCatalog = InMemoryUserCatalog()
        val postCatalog = InMemoryPostCatalog(availablePosts)
        val viewModel = TimelineViewModel(
            TimelineRepository(userCatalog, postCatalog),
            TestDispatchers()
        )

        viewModel.timelineFor(tim.id)

        assertEquals(TimelineState.Posts(timPosts), viewModel.timelineState.value)
    }

    @Test
    fun `posts from friends`() {
        val userCatalog = InMemoryUserCatalog(
            followings = listOf(
                Following(anna.id, lucy.id)
            )
        )
        val postCatalog = InMemoryPostCatalog(availablePosts)
        val viewModel = TimelineViewModel(
            TimelineRepository(userCatalog, postCatalog),
            TestDispatchers()
        )

        viewModel.timelineFor(anna.id)

        assertEquals(TimelineState.Posts(lucyPosts), viewModel.timelineState.value)
    }

    @Test
    fun `posts from friends along own`() {
        val userCatalog = InMemoryUserCatalog(
            followings = listOf(
                Following(sara.id, lucy.id)
            )
        )
        val postCatalog = InMemoryPostCatalog(availablePosts)
        val viewModel = TimelineViewModel(
            TimelineRepository(userCatalog, postCatalog),
            TestDispatchers()
        )

        viewModel.timelineFor(sara.id)

        assertEquals(TimelineState.Posts(lucyPosts + saraPosts), viewModel.timelineState.value)
    }
}