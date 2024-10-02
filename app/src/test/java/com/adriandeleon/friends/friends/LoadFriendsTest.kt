package com.adriandeleon.friends.friends

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.app.TestDispatchers
import com.adriandeleon.friends.domain.friends.FriendsRepository
import com.adriandeleon.friends.domain.user.Following
import com.adriandeleon.friends.domain.user.Friend
import com.adriandeleon.friends.domain.user.InMemoryUserCatalog
import com.adriandeleon.friends.infrastructure.builder.UserBuilder.Companion.aUser
import com.adriandeleon.friends.friends.state.FriendsState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutor::class)
class LoadFriendsTest {

    private val tom = aUser().withId("tomId").build()
    private val anna = aUser().withId("annaId").build()
    private val sara = aUser().withId("saraId").build()
    private val lucy = aUser().withId("lucyId").build()
    private val friendTom = Friend(tom, isFollower = false)
    private val friendAnna = Friend(anna, isFollower = true)
    private val friendSara = Friend(sara, isFollower = false)

    @Test
    fun `no friends found`() {
        val userCatalog = InMemoryUserCatalog()
        val viewModel = FriendsViewModel(
            FriendsRepository(userCatalog), TestDispatchers()
        )

        viewModel.loadFriends(sara.id)

        assertEquals(FriendsState.Loaded(emptyList()), viewModel.friendsState.value)
    }

    @Test
    fun `loaded a single person`() {
        val userCatalog = InMemoryUserCatalog(
            usersForPassword = mutableMapOf(":irrelevant:" to mutableListOf(tom))
        )
        val viewModel =
            FriendsViewModel(FriendsRepository(userCatalog), TestDispatchers())

        viewModel.loadFriends(anna.id)

        assertEquals(FriendsState.Loaded(listOf(friendTom)), viewModel.friendsState.value)
    }

    @Test
    fun `loaded multiple friends`() {
        val userCatalog = InMemoryUserCatalog(
            usersForPassword = mutableMapOf(":irrelevant:" to mutableListOf(anna, sara, tom)),
            followings = mutableListOf(Following(lucy.id, anna.id))
        )
        val viewModel = FriendsViewModel(
            FriendsRepository(userCatalog), TestDispatchers()
        )

        viewModel.loadFriends(lucy.id)

        assertEquals(
            FriendsState.Loaded(listOf(friendAnna, friendSara, friendTom)),
            viewModel.friendsState.value
        )
    }
}