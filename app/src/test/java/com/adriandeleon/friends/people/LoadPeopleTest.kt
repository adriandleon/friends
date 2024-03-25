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
class LoadPeopleTest {

    @Test
    fun `no people found`() {
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

        viewModel.loadPeople("saraId")

        assertEquals(PeopleState.Loaded(emptyList()), viewModel.peopleState.value)
    }

    @Test
    fun `loaded a single person`() {
        val tom = Friend(User("tomId", "", ""), isFollowee = false)
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

        viewModel.loadPeople("annaId")

        assertEquals(PeopleState.Loaded(listOf(tom)), viewModel.peopleState.value)
    }

    @Test
    fun `loaded multiple people`() {
        val anna = Friend(User("annaId", "", ""), isFollowee = true)
        val sara = Friend(User("saraId", "", ""), isFollowee = false)
        val tom = Friend(User("tomId", "", ""), isFollowee = false)
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

        viewModel.loadPeople("lucyId")

        assertEquals(
            PeopleState.Loaded(listOf(anna, sara, tom)),
            viewModel.peopleState.value
        )
    }
}