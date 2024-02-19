package com.adriandeleon.friends.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adriandeleon.friends.app.CoroutineDispatchers
import com.adriandeleon.friends.domain.timeline.TimelineRepository
import com.adriandeleon.friends.timeline.state.TimelineState
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TimelineViewModel(
    private val timelineRepository: TimelineRepository,
    private val dispatchers: CoroutineDispatchers
) : ViewModel() {

    private val mutableTimelineState = MutableLiveData<TimelineState?>()
    val timelineState: LiveData<TimelineState?> = mutableTimelineState

    fun timelineFor(userId: String) {
        viewModelScope.launch {
            mutableTimelineState.value = TimelineState.Loading
            mutableTimelineState.value = withContext(dispatchers.background) {
                timelineRepository.getTimelineFor(userId)
            }
        }
    }
}
