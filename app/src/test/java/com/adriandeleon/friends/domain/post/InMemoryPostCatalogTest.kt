package com.adriandeleon.friends.domain.post

class InMemoryPostCatalogTest : PostCatalogContract() {

    override fun postCatalogWith(vararg availablePosts: Post): PostCatalog {
        return InMemoryPostCatalog(
            availablePosts = availablePosts.toMutableList()
        )
    }
}