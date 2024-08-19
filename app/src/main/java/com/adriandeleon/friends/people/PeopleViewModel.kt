package com.adriandeleon.friends.people

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adriandeleon.friends.domain.people.PeopleRepository
import com.adriandeleon.friends.people.state.PeopleState
import kotlinx.coroutines.launch

class PeopleViewModel(
    private val peopleRepository: PeopleRepository
) : ViewModel() {
    private val mutablePeopleState = MutableLiveData<PeopleState>()
    val peopleState: LiveData<PeopleState> = mutablePeopleState

    fun loadPeople(userId: String) {
        viewModelScope.launch {
            val result = peopleRepository.loadPeopleFor(userId)
            mutablePeopleState.value = result
        }
    }
}