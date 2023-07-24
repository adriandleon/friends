package com.adriandeleon.friends.domain.post

import com.adriandeleon.friends.infrastructure.Clock
import com.adriandeleon.friends.infrastructure.IdGenerator
import com.adriandeleon.friends.infrastructure.SystemClock
import com.adriandeleon.friends.infrastructure.UUIDGenerator

class InMemoryPostCatalog(
    private val availablePosts: List<Post> = emptyList(),
    private val idGenerator: IdGenerator = UUIDGenerator(),
    private val clock: Clock = SystemClock(),
) : PostCatalog {

    override fun addPost(userId: String, postText: String): Post {
        val timestamp = clock.now()
        val postId = idGenerator.next()
        return Post(postId, userId, postText, timestamp)
    }

    override suspend fun postsFor(userIds: List<String>): List<Post> {
        return availablePosts.filter { userIds.contains(it.userId) }
    }
}