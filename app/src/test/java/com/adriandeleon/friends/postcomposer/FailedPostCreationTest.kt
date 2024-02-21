package com.adriandeleon.friends.postcomposer

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.app.TestDispatchers
import com.adriandeleon.friends.domain.post.OfflinePostCatalog
import com.adriandeleon.friends.domain.post.PostRepository
import com.adriandeleon.friends.domain.post.UnavailablePostCatalog
import com.adriandeleon.friends.domain.user.InMemoryUserDataStore
import com.adriandeleon.friends.postcomposer.state.CreatePostState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutor::class)
class FailedPostCreationTest {

    @Test
    fun `backend error`() {

        val viewModel = CreatePostViewModel(
            PostRepository(
                InMemoryUserDataStore("userId"),
                UnavailablePostCatalog()
            ),
            dispatchers = TestDispatchers()
        )
        viewModel.createPost(":backend:")

        assertEquals(CreatePostState.BackendError, viewModel.postState.value)
    }

    @Test
    fun `offline error`() {
        val viewModel = CreatePostViewModel(
            PostRepository(
                InMemoryUserDataStore("userId"),
                OfflinePostCatalog()
            ),
            dispatchers = TestDispatchers()
        )

        viewModel.createPost(":offline:")

        assertEquals(CreatePostState.Offline, viewModel.postState.value)
    }
}