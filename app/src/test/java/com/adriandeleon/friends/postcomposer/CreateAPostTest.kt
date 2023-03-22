package com.adriandeleon.friends.postcomposer

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.domain.post.Post
import com.adriandeleon.friends.postcomposer.state.CreatePostState
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutor::class)
class CreateAPostTest {

    @Test
    fun `a post is created`() {
        val postText = "First post"
        val post = Post("postId", "userId", postText, 1L)
        val viewModel = CreatePostViewModel()

        viewModel.createPost(postText)

        assertEquals(CreatePostState.Created(post), viewModel.postState.value)
    }
}