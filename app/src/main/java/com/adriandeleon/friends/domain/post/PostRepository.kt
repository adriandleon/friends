package com.adriandeleon.friends.domain.post

import com.adriandeleon.friends.domain.exceptions.BackendException
import com.adriandeleon.friends.domain.exceptions.ConnectionUnavailableException
import com.adriandeleon.friends.domain.user.InMemoryUserDataStore
import com.adriandeleon.friends.postcomposer.state.CreatePostState

class PostRepository(
    private val userData: InMemoryUserDataStore,
    private val postCatalog: PostCatalog
) {

    suspend fun createNewPost(postText: String): CreatePostState {
        return try {
            val post = postCatalog.addPost(userData.loggedInUserId(), postText)
            CreatePostState.Created(post)
        } catch (backendException: BackendException) {
            CreatePostState.BackendError
        } catch (offlineException: ConnectionUnavailableException) {
            CreatePostState.Offline
        }
    }
}