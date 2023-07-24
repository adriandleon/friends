package com.adriandeleon.friends.postcomposer

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.domain.post.InMemoryPostCatalog
import com.adriandeleon.friends.domain.post.Post
import com.adriandeleon.friends.domain.post.PostRepository
import com.adriandeleon.friends.domain.user.InMemoryUserData
import com.adriandeleon.friends.infrastructure.ControllableClock
import com.adriandeleon.friends.infrastructure.ControllableIdGenerator
import com.adriandeleon.friends.postcomposer.state.CreatePostState
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutor::class)
class CreateAPostTest {

    private val userId = "userId"
    private val userData = InMemoryUserData(userId)

    @Test
    fun `a post is created`() {

        val postText = "First post"
        val post = Post("postId1", userId, postText, 1L)
        val clock = ControllableClock(1L)
        val idGenerator = ControllableIdGenerator("postId1")
        val viewModel = CreatePostViewModel(
            PostRepository(
                userData, InMemoryPostCatalog(
                    idGenerator = idGenerator,
                    clock = clock
                )
            )
        )

        viewModel.createPost(postText)

        assertEquals(CreatePostState.Created(post), viewModel.postState.value)
    }

    @Test
    fun `another post created`() {
        val postText = "Second post"
        val anotherPost = Post("postId2", userId, postText, 2L)
        val clock = ControllableClock(2L)
        val idGenerator = ControllableIdGenerator("postId2")
        val viewModel = CreatePostViewModel(
            PostRepository(
                userData, InMemoryPostCatalog(
                    idGenerator = idGenerator,
                    clock = clock
                )
            )
        )

        viewModel.createPost(postText)

        assertEquals(CreatePostState.Created(anotherPost), viewModel.postState.value)
    }
}