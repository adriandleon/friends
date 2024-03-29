package com.adriandeleon.friends.timeline

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adriandeleon.friends.R
import com.adriandeleon.friends.domain.post.Post
import com.adriandeleon.friends.timeline.state.TimelineScreenState
import com.adriandeleon.friends.timeline.state.TimelineState
import com.adriandeleon.friends.ui.composables.BlockingLoading
import com.adriandeleon.friends.ui.composables.InfoMessage
import com.adriandeleon.friends.ui.composables.ScreenTitle
import com.adriandeleon.friends.ui.extensions.toDateTime
import org.koin.androidx.compose.koinViewModel

@Composable
fun TimelineScreen(
    userId: String,
    onCreateNewPost: () -> Unit
) {

    val timelineViewModel = koinViewModel<TimelineViewModel>()
    val screenState by remember { mutableStateOf(TimelineScreenState()) }
    val timelineState by timelineViewModel.timelineState.observeAsState()

    if (screenState.shouldLoadPostsFor(userId)) {
        timelineViewModel.timelineFor(userId)
    }

    when (timelineState) {
        is TimelineState.Loading -> screenState.showLoading()
        is TimelineState.Posts -> {
            val posts = (timelineState as TimelineState.Posts).posts
            screenState.updatePosts(posts)
        }

        is TimelineState.BackendError ->
            screenState.showInfoMessage(R.string.fetchingTimelineError)

        is TimelineState.OfflineError ->
            screenState.showInfoMessage(R.string.offlineError)

        else -> {}
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ScreenTitle(resourceId = R.string.timeline)
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxSize()) {
                PostsList(
                    posts = screenState.posts,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
                FloatingActionButton(
                    onClick = onCreateNewPost,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .testTag(stringResource(id = R.string.createNewPost))
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.createNewPost)
                    )
                }
            }
        }

        InfoMessage(
            stringResource = screenState.currentInfoMessage
        )

        BlockingLoading(isShowing = screenState.isLoading)
    }
}

@Composable
private fun PostsList(
    posts: List<Post>,
    modifier: Modifier = Modifier
) {
    if (posts.isEmpty()) {
        Text(
            text = stringResource(id = R.string.emptyTimelineMessage),
            modifier = modifier
        )
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(posts) { post ->
                PostItem(post)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun PostItem(
    post: Post,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.onSurface,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = post.userId)
                Text(text = post.timestamp.toDateTime())
            }

            Text(
                text = post.postText,
                style = MaterialTheme.typography.h5,
                modifier = modifier.padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PostsListPreview() {
    val posts = (0..100).map { index ->
        Post("$index", "user$index", "This is a post number $index", index.toLong())
    }
    PostsList(posts)
}
