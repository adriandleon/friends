package com.adriandeleon.friends.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adriandeleon.friends.domain.timeline.TimelineRepository
import com.adriandeleon.friends.timeline.state.TimelineState

class TimelineViewModel(
    private val timelineRepository: TimelineRepository
) : ViewModel() {

    private val mutableTimelineState = MutableLiveData<TimelineState>()
    val timelineState: LiveData<TimelineState> = mutableTimelineState

    fun timelineFor(userId: String) {
        val result = timelineRepository.getTimelineFor(userId)
        mutableTimelineState.value = result
    }
}
