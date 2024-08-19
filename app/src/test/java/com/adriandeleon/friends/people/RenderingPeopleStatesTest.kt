package com.adriandeleon.friends.people

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.app.TestDispatchers
import com.adriandeleon.friends.domain.people.InMemoryPeopleCatalog
import com.adriandeleon.friends.domain.people.PeopleCatalog
import com.adriandeleon.friends.domain.people.PeopleRepository
import com.adriandeleon.friends.domain.user.Friend
import com.adriandeleon.friends.infrastructure.builder.UserBuilder.Companion.aUser
import com.adriandeleon.friends.people.state.PeopleState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutor::class)
class RenderingPeopleStatesTest {

    private val anna = Friend(aUser().withId("annaId").build(), isFollowee = true)
    private val tom = Friend(aUser().withId("tomId").build(), isFollowee = true)
    private val peopleCatalog: PeopleCatalog = InMemoryPeopleCatalog(
        mapOf("jovId" to listOf(tom, anna))
    )

    @Test
    fun `people states delivered to an observer in particular order`() {
        val viewModel = PeopleViewModel(PeopleRepository(peopleCatalog), TestDispatchers())
        val deliveredStates = mutableListOf<PeopleState>()
        viewModel.peopleState.observeForever { deliveredStates.add(it) }

        viewModel.loadPeople("jovId")

        assertEquals(
            listOf(PeopleState.Loading, PeopleState.Loaded(listOf(tom, anna))),
            deliveredStates
        )
    }
}