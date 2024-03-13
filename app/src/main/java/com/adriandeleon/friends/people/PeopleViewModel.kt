package com.adriandeleon.friends.people

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adriandeleon.friends.domain.user.Friend
import com.adriandeleon.friends.domain.user.User
import com.adriandeleon.friends.people.state.PeopleState

class PeopleViewModel {
    private val mutablePeopleState = MutableLiveData<PeopleState>()
    val peopleState: LiveData<PeopleState> = mutablePeopleState

    fun loadPeople(userId: String) {
        val tom = Friend(User("tomId", "", ""), isFollowee = false)
        val anna = Friend(User("annaId", "", ""), isFollowee = true)
        val sara = Friend(User("saraId", "", ""), isFollowee = false)
        val peopleForUserId = mapOf(
            "annaId" to listOf(tom),
            "lucyId" to listOf(anna, sara, tom),
            "saraId" to emptyList()
        )

        val isKnownUserId = peopleForUserId.contains(userId)

        val result = if (isKnownUserId) {
            PeopleState.Loaded(peopleForUserId.getValue(userId))
        } else if (userId == "johnId") {
            PeopleState.BackendError
        } else if (userId.isBlank()) {
            PeopleState.Offline
        } else {
            TODO()
        }

        mutablePeopleState.value = result
    }
}