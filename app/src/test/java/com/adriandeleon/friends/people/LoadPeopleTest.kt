package com.adriandeleon.friends.people

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.app.TestDispatchers
import com.adriandeleon.friends.domain.people.InMemoryPeopleCatalog
import com.adriandeleon.friends.domain.people.PeopleRepository
import com.adriandeleon.friends.domain.user.Following
import com.adriandeleon.friends.domain.user.Friend
import com.adriandeleon.friends.domain.user.InMemoryUserCatalog
import com.adriandeleon.friends.infrastructure.builder.UserBuilder.Companion.aUser
import com.adriandeleon.friends.people.state.PeopleState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutor::class)
class LoadPeopleTest {

    private val tom = aUser().withId("tomId").build()
    private val anna = aUser().withId("annaId").build()
    private val sara = aUser().withId("saraId").build()
    private val lucy = aUser().withId("lucyId").build()
    private val friendTom = Friend(tom, isFollower = false)
    private val friendAnna = Friend(anna, isFollower = true)
    private val friendSara = Friend(sara, isFollower = false)

    private val peopleCatalog = InMemoryPeopleCatalog(
        mapOf(
            anna.id to listOf(friendTom),
            lucy.id to listOf(friendAnna, friendSara, friendTom),
            sara.id to emptyList()
        )
    )

    @Test
    fun `no people found`() {
        val userCatalog = InMemoryUserCatalog()
        val viewModel = PeopleViewModel(
            PeopleRepository(peopleCatalog, userCatalog), TestDispatchers()
        )

        viewModel.loadPeople(sara.id)

        assertEquals(PeopleState.Loaded(emptyList()), viewModel.peopleState.value)
    }

    @Test
    fun `loaded a single person`() {
        val userCatalog = InMemoryUserCatalog(
            usersForPassword = mutableMapOf(":irrelevant:" to mutableListOf(tom))
        )
        val viewModel =
            PeopleViewModel(PeopleRepository(peopleCatalog, userCatalog), TestDispatchers())

        viewModel.loadPeople(anna.id)

        assertEquals(PeopleState.Loaded(listOf(friendTom)), viewModel.peopleState.value)
    }

    @Test
    fun `loaded multiple people`() {
        val userCatalog = InMemoryUserCatalog(
            usersForPassword = mutableMapOf(":irrelevant:" to mutableListOf(anna, sara, tom)),
            followings = mutableListOf(Following(lucy.id, anna.id))
        )
        val viewModel = PeopleViewModel(
            PeopleRepository(peopleCatalog, userCatalog), TestDispatchers()
        )

        viewModel.loadPeople(lucy.id)

        assertEquals(
            PeopleState.Loaded(listOf(friendAnna, friendSara, friendTom)),
            viewModel.peopleState.value
        )
    }
}