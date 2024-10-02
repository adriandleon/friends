package com.adriandeleon.friends.friends

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.app.TestDispatchers
import com.adriandeleon.friends.domain.friends.FriendsRepository
import com.adriandeleon.friends.domain.user.OfflineUserCatalog
import com.adriandeleon.friends.domain.user.UnavailableUserCatalog
import com.adriandeleon.friends.friends.state.FriendsState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutor::class)
class FailFriendsLoadingTest {

    @Test
    fun `backend error`() {
        val viewModel = FriendsViewModel(
            FriendsRepository(UnavailableUserCatalog()),
            TestDispatchers()
        )

        viewModel.loadFriends(":irrelevant:")

        assertEquals(FriendsState.BackendError, viewModel.friendsState.value)
    }

    @Test
    fun `offline error`() {
        val viewModel = FriendsViewModel(FriendsRepository(
            OfflineUserCatalog()
        ), TestDispatchers())

        viewModel.loadFriends(":irrelevant:")

        assertEquals(FriendsState.Offline, viewModel.friendsState.value)
    }
}