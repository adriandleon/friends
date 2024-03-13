package com.adriandeleon.friends.people

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.people.state.PeopleState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutor::class)
class FailPeopleLoadingTest {

    @Test
    fun `backend error`() {
        val viewModel = PeopleViewModel()

        viewModel.loadPeople("johnId")

        assertEquals(PeopleState.BackendError, viewModel.peopleState.value)
    }

    @Test
    fun `offline error`() {
        val viewModel = PeopleViewModel()

        viewModel.loadPeople("")

        assertEquals(PeopleState.Offline, viewModel.peopleState.value)
    }
}