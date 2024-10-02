package com.adriandeleon.friends.friends

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adriandeleon.friends.app.CoroutineDispatchers
import com.adriandeleon.friends.domain.friends.FriendsRepository
import com.adriandeleon.friends.friends.state.FriendsState
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FriendsViewModel(
    private val friendsRepository: FriendsRepository,
    private val dispatchers: CoroutineDispatchers
) : ViewModel() {
    private val mutableFriendsState = MutableLiveData<FriendsState>()
    val friendsState: LiveData<FriendsState> = mutableFriendsState

    @SuppressLint("NullSafeMutableLiveData")
    fun loadFriends(userId: String) {
        viewModelScope.launch {
            mutableFriendsState.value = FriendsState.Loading
            mutableFriendsState.value = withContext(dispatchers.background) {
                friendsRepository.loadFriendsFor(userId)
            }
        }
    }
}