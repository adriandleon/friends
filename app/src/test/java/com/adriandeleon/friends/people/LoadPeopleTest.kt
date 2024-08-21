package com.adriandeleon.friends.people

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.app.TestDispatchers
import com.adriandeleon.friends.domain.people.InMemoryPeopleCatalog
import com.adriandeleon.friends.domain.people.PeopleRepository
import com.adriandeleon.friends.domain.user.Friend
import com.adriandeleon.friends.domain.user.User
import com.adriandeleon.friends.people.state.PeopleState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutor::class)
class LoadPeopleTest {

    private val tom = Friend(User("tomId", "", ""), isFollower = false)
    private val anna = Friend(User("annaId", "", ""), isFollower = true)
    private val sara = Friend(User("saraId", "", ""), isFollower = false)

    private val peopleCatalog = InMemoryPeopleCatalog(
        mapOf(
            "annaId" to listOf(tom),
            "lucyId" to listOf(anna, sara, tom),
            "saraId" to emptyList()
        )
    )

    @Test
    fun `no people found`() {
        val viewModel = PeopleViewModel(PeopleRepository(peopleCatalog), TestDispatchers())

        viewModel.loadPeople("saraId")

        assertEquals(PeopleState.Loaded(emptyList()), viewModel.peopleState.value)
    }

    @Test
    fun `loaded a single person`() {
        val viewModel = PeopleViewModel(PeopleRepository(peopleCatalog), TestDispatchers())

        viewModel.loadPeople("annaId")

        assertEquals(PeopleState.Loaded(listOf(tom)), viewModel.peopleState.value)
    }

    @Test
    fun `loaded multiple people`() {
        val viewModel = PeopleViewModel(PeopleRepository(peopleCatalog), TestDispatchers())

        viewModel.loadPeople("lucyId")

        assertEquals(
            PeopleState.Loaded(listOf(anna, sara, tom)),
            viewModel.peopleState.value
        )
    }
}