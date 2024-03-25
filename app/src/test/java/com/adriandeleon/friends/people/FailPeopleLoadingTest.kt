package com.adriandeleon.friends.people

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.domain.exceptions.ConnectionUnavailableException
import com.adriandeleon.friends.domain.people.InMemoryPeopleCatalog
import com.adriandeleon.friends.domain.people.PeopleCatalog
import com.adriandeleon.friends.domain.people.PeopleRepository
import com.adriandeleon.friends.domain.user.Friend
import com.adriandeleon.friends.domain.user.User
import com.adriandeleon.friends.people.state.PeopleState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutor::class)
class FailPeopleLoadingTest {

    private val tom = Friend(User("tomId", "", ""), isFollowee = false)
    private val anna = Friend(User("annaId", "", ""), isFollowee = true)
    private val sara = Friend(User("saraId", "", ""), isFollowee = false)

    private val peopleCatalog = InMemoryPeopleCatalog(
        mapOf(
            "annaId" to listOf(tom),
            "lucyId" to listOf(anna, sara, tom),
            "saraId" to emptyList()
        )
    )

    @Test
    fun `backend error`() {
        val viewModel = PeopleViewModel(PeopleRepository(peopleCatalog))

        viewModel.loadPeople("johnId")

        assertEquals(PeopleState.BackendError, viewModel.peopleState.value)
    }

    @Test
    fun `offline error`() {
        val viewModel = PeopleViewModel(PeopleRepository(OfflinePeopleCatalog()))

        viewModel.loadPeople(":irrelevant:")

        assertEquals(PeopleState.Offline, viewModel.peopleState.value)
    }

    private class OfflinePeopleCatalog : PeopleCatalog {
        override fun loadPeopleFor(userId: String): List<Friend> {
            throw ConnectionUnavailableException()
        }
    }
}