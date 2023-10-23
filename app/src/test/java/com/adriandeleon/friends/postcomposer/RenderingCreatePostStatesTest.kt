package com.adriandeleon.friends.postcomposer

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.app.TestDispatchers
import com.adriandeleon.friends.domain.post.InMemoryPostCatalog
import com.adriandeleon.friends.domain.post.Post
import com.adriandeleon.friends.domain.post.PostRepository
import com.adriandeleon.friends.domain.user.InMemoryUserData
import com.adriandeleon.friends.infrastructure.ControllableClock
import com.adriandeleon.friends.infrastructure.ControllableIdGenerator
import com.adriandeleon.friends.postcomposer.state.CreatePostState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutor::class)
class RenderingCreatePostStatesTest {

    private val idGenerator = ControllableIdGenerator("postId")
    private val clock = ControllableClock(1L)
    private val postCatalog = InMemoryPostCatalog(idGenerator = idGenerator, clock = clock)
    private val userData = InMemoryUserData("userId")
    private val postRepository = PostRepository(userData, postCatalog)
    private val dispatchers = TestDispatchers()
    private val viewModel = CreatePostViewModel(postRepository, dispatchers)

    @Test
    fun `UI states are delivered in particular order`() {
        val deliveredStates = mutableListOf<CreatePostState>()
        viewModel.postState.observeForever { deliveredStates.add(it) }
        val post = Post("postId", "userId", "Post Text", 1L)

        viewModel.createPost("Post Text")

        assertEquals(
            listOf(CreatePostState.Loading, CreatePostState.Created(post)),
            deliveredStates
        )
    }
}