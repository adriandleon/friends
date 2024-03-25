package com.adriandeleon.friends.people

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.domain.people.InMemoryPeopleCatalog
import com.adriandeleon.friends.domain.people.PeopleRepository
import com.adriandeleon.friends.domain.user.Friend
import com.adriandeleon.friends.domain.user.User
import com.adriandeleon.friends.people.state.PeopleState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutor::class)
class FailPeopleLoadingTest {

    @Test
    fun `backend error`() {
        val viewModel = PeopleViewModel(PeopleRepository(InMemoryPeopleCatalog(
            mapOf(
                "annaId" to listOf(Friend(User("tomId", "", ""), isFollowee = false)),
                "lucyId" to listOf(
                    Friend(User("annaId", "", ""), isFollowee = true),
                    Friend(User("saraId", "", ""), isFollowee = false),
                    Friend(User("tomId", "", ""), isFollowee = false)
                ),
                "saraId" to emptyList()
            )
        )
        ))

        viewModel.loadPeople("johnId")

        assertEquals(PeopleState.BackendError, viewModel.peopleState.value)
    }

    @Test
    fun `offline error`() {
        val viewModel = PeopleViewModel(PeopleRepository(InMemoryPeopleCatalog(
            mapOf(
                "annaId" to listOf(Friend(User("tomId", "", ""), isFollowee = false)),
                "lucyId" to listOf(
                    Friend(User("annaId", "", ""), isFollowee = true),
                    Friend(User("saraId", "", ""), isFollowee = false),
                    Friend(User("tomId", "", ""), isFollowee = false)
                ),
                "saraId" to emptyList()
            )
        )
        ))

        viewModel.loadPeople("")

        assertEquals(PeopleState.Offline, viewModel.peopleState.value)
    }
}