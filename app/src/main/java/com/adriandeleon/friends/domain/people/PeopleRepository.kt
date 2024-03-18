package com.adriandeleon.friends.domain.people

import com.adriandeleon.friends.domain.exceptions.BackendException
import com.adriandeleon.friends.domain.exceptions.ConnectionUnavailableException
import com.adriandeleon.friends.people.state.PeopleState

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