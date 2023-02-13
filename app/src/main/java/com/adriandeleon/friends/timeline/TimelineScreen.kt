package com.adriandeleon.friends.timeline

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adriandeleon.friends.R
import com.adriandeleon.friends.domain.post.Post
import com.adriandeleon.friends.timeline.state.TimelineState
import com.adriandeleon.friends.ui.composables.ScreenTitle

class TimelineScreenState {
    var posts by mutableStateOf(emptyList<Post>())

    fun updatePosts(newPosts: List<Post>) {
        this.posts = newPosts
    }
}

@Composable
fun TimelineScreen(
    userId: String,
    timelineViewModel: TimelineViewModel
) {
    val screenState by remember { mutableStateOf(TimelineScreenState()) }
    val timelineState by timelineViewModel.timelineState.observeAsState()
    timelineViewModel.timelineFor(userId)

    if (timelineState is TimelineState.Posts) {
        val posts = (timelineState as TimelineState.Posts).posts
        screenState.updatePosts(posts)
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        ScreenTitle(resourceId = R.string.timeline)
        PostsList(screenState.posts)
    }
}

@Composable
private fun PostsList(posts: List<Post>) {
    if (posts.isEmpty()) {
        Text(text = stringResource(id = R.string.emptyTimelineMessage))
    } else {
        LazyColumn {
            items(posts) { post ->
                PostItem(post)
            }
        }
    }
}

@Composable
fun PostItem(post: Post) {
    Text(text = post.postText)
}
