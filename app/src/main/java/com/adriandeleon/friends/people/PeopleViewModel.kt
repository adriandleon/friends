package com.adriandeleon.friends.people

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adriandeleon.friends.domain.people.PeopleRepository
import com.adriandeleon.friends.people.state.PeopleState

class PeopleViewModel(
    private val peopleRepository: PeopleRepository
) {
    private val mutablePeopleState = MutableLiveData<PeopleState>()
    val peopleState: LiveData<PeopleState> = mutablePeopleState

    fun loadPeople(userId: String) {
        val result = peopleRepository.loadPeopleFor(userId)
        mutablePeopleState.value = result
    }
}