package com.adriandeleon.friends.domain.post

import com.adriandeleon.friends.domain.exceptions.ConnectionUnavailableException

public class OfflinePostCatalog : PostCatalog {
    override fun addPost(userId: String, postText: String): Post {
        throw ConnectionUnavailableException()
    }

    override suspend fun postsFor(userIds: List<String>): List<Post> {
        throw ConnectionUnavailableException()
    }
}