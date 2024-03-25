package com.adriandeleon.friends.domain.people

import com.adriandeleon.friends.domain.exceptions.BackendException
import com.adriandeleon.friends.domain.exceptions.ConnectionUnavailableException
import com.adriandeleon.friends.people.state.PeopleState

class PeopleRepository(
    private val peopleCatalog: InMemoryPeopleCatalog
) {
    fun loadPeopleFor(userId: String): PeopleState {
        return try {
            val peopleForUserId = peopleCatalog.loadPeopleFor(userId)
            PeopleState.Loaded(peopleForUserId)
        } catch (backendException: BackendException) {
            PeopleState.BackendError
        } catch (offlineException: ConnectionUnavailableException) {
            PeopleState.Offline
        }
    }
}