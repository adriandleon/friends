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

    private val tom = User("tomId", "", "")
    private val anna = User("annaId", "", "")
    private val sara = User("saraId", "", "")
    private val friendTom = Friend(tom, isFollower = false)
    private val friendAnna = Friend(anna, isFollower = true)
    private val friendSara = Friend(sara, isFollower = false)

    private val peopleCatalog = InMemoryPeopleCatalog(
        mapOf(
            "annaId" to listOf(friendTom),
            "lucyId" to listOf(friendAnna, friendSara, friendTom),
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

        assertEquals(PeopleState.Loaded(listOf(friendTom)), viewModel.peopleState.value)
    }

    @Test
    fun `loaded multiple people`() {
        val viewModel = PeopleViewModel(PeopleRepository(peopleCatalog), TestDispatchers())

        viewModel.loadPeople("lucyId")

        assertEquals(
            PeopleState.Loaded(listOf(friendAnna, friendSara, friendTom)),
            viewModel.peopleState.value
        )
    }
}