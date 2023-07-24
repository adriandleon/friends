package com.adriandeleon.friends.postcomposer

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.domain.exceptions.BackendException
import com.adriandeleon.friends.domain.exceptions.ConnectionUnavailableException
import com.adriandeleon.friends.domain.post.Post
import com.adriandeleon.friends.domain.post.PostCatalog
import com.adriandeleon.friends.domain.post.PostRepository
import com.adriandeleon.friends.domain.user.InMemoryUserData
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
                InMemoryUserData("userId"),
                UnavailablePostCatalog()
            )
        )
        viewModel.createPost(":backend:")

        assertEquals(CreatePostState.BackendError, viewModel.postState.value)
    }

    @Test
    fun `offline error`() {
        val viewModel = CreatePostViewModel(
            PostRepository(
                InMemoryUserData("userId"),
                OfflinePostCatalog()
            )
        )

        viewModel.createPost(":offline:")

        assertEquals(CreatePostState.Offline, viewModel.postState.value)
    }

    private class UnavailablePostCatalog : PostCatalog {
        override fun addPost(userId: String, postText: String): Post {
            throw BackendException()
        }

        override suspend fun postsFor(userIds: List<String>): List<Post> {
            TODO("Not yet implemented")
        }
    }

    private class OfflinePostCatalog : PostCatalog {
        override fun addPost(userId: String, postText: String): Post {
            throw ConnectionUnavailableException()
        }

        override suspend fun postsFor(userIds: List<String>): List<Post> {
            TODO("Not yet implemented")
        }
    }
}