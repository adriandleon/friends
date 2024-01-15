package com.adriandeleon.friends.postcomposer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.adriandeleon.friends.R
import com.adriandeleon.friends.postcomposer.state.CreatePostState
import com.adriandeleon.friends.ui.composables.ScreenTitle

class CreateNewPostScreenState {
    var isPostSubmitted by mutableStateOf(false)

    fun setPostSubmitted() {
        isPostSubmitted = true
    }
}

@Composable
fun CreateNewPostScreen(
    createPostViewModel: CreatePostViewModel,
    onPostCreated: () -> Unit,
) {

    val screenState by remember { mutableStateOf(CreateNewPostScreenState()) }
    var postText by remember { mutableStateOf("") }
    val createPostState by createPostViewModel.postState.observeAsState()

    when (createPostState) {
        is CreatePostState.Created -> {
            if (screenState.isPostSubmitted) {
                onPostCreated()
            }
        }
        else -> {}
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ScreenTitle(resourceId = R.string.createNewPost)
        Box(modifier = Modifier.fillMaxSize()) {
            PostComposer(postText) { postText = it }
            FloatingActionButton(
                onClick = {
                    screenState.setPostSubmitted()
                    createPostViewModel.createPost(postText)
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .testTag(stringResource(id = R.string.submitPost))
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = stringResource(id = R.string.submitPost)
                )
            }
        }
    }
}

@Composable
private fun PostComposer(
    postText: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = postText,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = stringResource(id = R.string.newPostHint)) }
    )
}