package com.adriandeleon.friends.people

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.domain.exceptions.BackendException
import com.adriandeleon.friends.domain.exceptions.ConnectionUnavailableException
import com.adriandeleon.friends.domain.people.PeopleCatalog
import com.adriandeleon.friends.domain.people.PeopleRepository
import com.adriandeleon.friends.domain.user.Friend
import com.adriandeleon.friends.people.state.PeopleState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutor::class)
class FailPeopleLoadingTest {

    @Test
    fun `backend error`() {
        val viewModel = PeopleViewModel(PeopleRepository(UnavailablePeopleCatalog()))

        viewModel.loadPeople(":irrelevant:")

        assertEquals(PeopleState.BackendError, viewModel.peopleState.value)
    }

    @Test
    fun `offline error`() {
        val viewModel = PeopleViewModel(PeopleRepository(OfflinePeopleCatalog()))

        viewModel.loadPeople(":irrelevant:")

        assertEquals(PeopleState.Offline, viewModel.peopleState.value)
    }

    private class UnavailablePeopleCatalog : PeopleCatalog {
        override suspend fun loadPeopleFor(userId: String): List<Friend> {
            throw BackendException()
        }
    }

    private class OfflinePeopleCatalog : PeopleCatalog {
        override suspend fun loadPeopleFor(userId: String): List<Friend> {
            throw ConnectionUnavailableException()
        }
    }
}