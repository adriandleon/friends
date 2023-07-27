package com.adriandeleon.friends.domain.post

import com.adriandeleon.friends.domain.exceptions.BackendException

class UnavailablePostCatalog : PostCatalog {
    override fun addPost(userId: String, postText: String): Post {
        throw BackendException()
    }

    override suspend fun postsFor(userIds: List<String>): List<Post> {
        throw BackendException()
    }
}
