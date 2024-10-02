package com.adriandeleon.friends.friends

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.app.TestDispatchers
import com.adriandeleon.friends.domain.friends.FriendsRepository
import com.adriandeleon.friends.domain.user.Following
import com.adriandeleon.friends.domain.user.Friend
import com.adriandeleon.friends.domain.user.InMemoryUserCatalog
import com.adriandeleon.friends.domain.user.UserCatalog
import com.adriandeleon.friends.infrastructure.builder.UserBuilder.Companion.aUser
import com.adriandeleon.friends.friends.state.FriendsState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutor::class)
class RenderingFriendsStatesTest {

    private val anna = aUser().withId("annaId").build()
    private val tom = aUser().withId("tomId").build()
    private val jov = aUser().withId("jovId").build()
    private val friendAnna = Friend(anna, isFollower = true)
    private val friendTom = Friend(tom, isFollower = true)
    private val userCatalog: UserCatalog = InMemoryUserCatalog(
        usersForPassword = mutableMapOf(":irrelevant:" to mutableListOf(tom, anna)),
        followings = mutableListOf(Following(jov.id, anna.id), Following(jov.id, tom.id))
    )
    private val viewModel = FriendsViewModel(
        FriendsRepository(userCatalog),
        TestDispatchers()
    )

    @Test
    fun `friends states delivered to an observer in particular order`() {
        val deliveredStates = mutableListOf<FriendsState>()
        viewModel.friendsState.observeForever { deliveredStates.add(it) }

        viewModel.loadFriends(jov.id)

        assertEquals(
            listOf(FriendsState.Loading, FriendsState.Loaded(listOf(friendTom, friendAnna))),
            deliveredStates
        )
    }
}