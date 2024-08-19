package com.adriandeleon.friends.people

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adriandeleon.friends.app.CoroutineDispatchers
import com.adriandeleon.friends.domain.people.PeopleRepository
import com.adriandeleon.friends.people.state.PeopleState
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PeopleViewModel(
    private val peopleRepository: PeopleRepository,
    private val dispatchers: CoroutineDispatchers
) : ViewModel() {
    private val mutablePeopleState = MutableLiveData<PeopleState>()
    val peopleState: LiveData<PeopleState> = mutablePeopleState

    @SuppressLint("NullSafeMutableLiveData")
    fun loadPeople(userId: String) {
        viewModelScope.launch {
            mutablePeopleState.value = PeopleState.Loading
            mutablePeopleState.value = withContext(dispatchers.background) {
                peopleRepository.loadPeopleFor(userId)
            }
        }
    }
}