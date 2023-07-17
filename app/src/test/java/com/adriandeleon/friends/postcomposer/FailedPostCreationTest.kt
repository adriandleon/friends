package com.adriandeleon.friends.postcomposer

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.domain.user.InMemoryUserData
import com.adriandeleon.friends.infrastructure.ControllableClock
import com.adriandeleon.friends.infrastructure.ControllableIdGenerator
import com.adriandeleon.friends.postcomposer.state.CreatePostState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutor::class)
class FailedPostCreationTest {

    @Test
    fun `backend error`() {

        val viewModel = CreatePostViewModel(
            InMemoryUserData("userId"),
            ControllableClock(1L),
            ControllableIdGenerator("postId1")
        )
        viewModel.createPost(":backend:")

        assertEquals(CreatePostState.BackendError, viewModel.postState.value)
    }

    @Test
    fun `offline error`() {
        val viewModel = CreatePostViewModel(
            InMemoryUserData("userId"),
            ControllableClock(1L),
            ControllableIdGenerator("postId1")
        )

        viewModel.createPost(":offline:")

        assertEquals(CreatePostState.Offline, viewModel.postState.value)
    }
}