package com.adriandeleon.friends.people

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adriandeleon.friends.domain.exceptions.BackendException
import com.adriandeleon.friends.domain.exceptions.ConnectionUnavailableException
import com.adriandeleon.friends.domain.people.InMemoryPeopleCatalog
import com.adriandeleon.friends.people.state.PeopleState

class PeopleViewModel {
    private val mutablePeopleState = MutableLiveData<PeopleState>()
    val peopleState: LiveData<PeopleState> = mutablePeopleState

    fun loadPeople(userId: String) {
        val result = PeopleRepository().loadPeopleFor(userId)
        mutablePeopleState.value = result
    }

    class PeopleRepository {
        fun loadPeopleFor(userId: String): PeopleState {
            return try {
                val peopleForUserId = InMemoryPeopleCatalog().loadPeopleFor(userId)
                PeopleState.Loaded(peopleForUserId)
            } catch (backendException: BackendException) {
                PeopleState.BackendError
            } catch (offlineException: ConnectionUnavailableException) {
                PeopleState.Offline
            }
        }
    }
}