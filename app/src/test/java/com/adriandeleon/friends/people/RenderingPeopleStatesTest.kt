package com.adriandeleon.friends.people

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.app.TestDispatchers
import com.adriandeleon.friends.domain.people.InMemoryPeopleCatalog
import com.adriandeleon.friends.domain.people.PeopleCatalog
import com.adriandeleon.friends.domain.people.PeopleRepository
import com.adriandeleon.friends.domain.user.Following
import com.adriandeleon.friends.domain.user.Friend
import com.adriandeleon.friends.domain.user.InMemoryUserCatalog
import com.adriandeleon.friends.domain.user.UserCatalog
import com.adriandeleon.friends.infrastructure.builder.UserBuilder.Companion.aUser
import com.adriandeleon.friends.people.state.PeopleState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutor::class)
class RenderingPeopleStatesTest {

    private val anna = aUser().withId("annaId").build()
    private val tom = aUser().withId("tomId").build()
    private val jov = aUser().withId("jovId").build()
    private val friendAnna = Friend(anna, isFollower = true)
    private val friendTom = Friend(tom, isFollower = true)
    private val peopleCatalog: PeopleCatalog = InMemoryPeopleCatalog(
        mapOf(jov.id to listOf(friendTom, friendAnna))
    )
    private val userCatalog: UserCatalog = InMemoryUserCatalog(
        usersForPassword = mutableMapOf(":irrelevant:" to mutableListOf(tom, anna)),
        followings = mutableListOf(Following(jov.id, anna.id), Following(jov.id, tom.id))
    )
    private val viewModel = PeopleViewModel(
        PeopleRepository(peopleCatalog, userCatalog),
        TestDispatchers()
    )

    @Test
    fun `people states delivered to an observer in particular order`() {
        val deliveredStates = mutableListOf<PeopleState>()
        viewModel.peopleState.observeForever { deliveredStates.add(it) }

        viewModel.loadPeople(jov.id)

        assertEquals(
            listOf(PeopleState.Loading, PeopleState.Loaded(listOf(friendTom, friendAnna))),
            deliveredStates
        )
    }
}