package com.adriandeleon.friends.people

import com.adriandeleon.friends.InstantTaskExecutor
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
        val viewModel = PeopleViewModel()

        viewModel.loadPeople("saraId")

        assertEquals(PeopleState.Loaded(emptyList()), viewModel.peopleState.value)
    }

    @Test
    fun `loaded a single person`() {
        val user = User("tomId", "", "")
        val tomFriend = Friend(user, isFollowee = false)
        val viewModel = PeopleViewModel()

        viewModel.loadPeople("annaId")

        assertEquals(PeopleState.Loaded(listOf(tomFriend)), viewModel.peopleState.value)
    }

    @Test
    fun `loaded multiple people`() {
        val friendAnna = Friend(User("annaId", "", ""), isFollowee = true)
        val friendSara = Friend(User("saraId", "", ""), isFollowee = false)
        val friendTom = Friend(User("tomId", "", ""), isFollowee = false)
        val viewModel = PeopleViewModel()

        viewModel.loadPeople("lucyId")

        assertEquals(
            PeopleState.Loaded(listOf(friendAnna, friendSara, friendTom)),
            viewModel.peopleState.value
        )
    }
}