package com.adriandeleon.friends.app

import com.adriandeleon.friends.domain.post.InMemoryPostCatalog
import com.adriandeleon.friends.domain.post.PostCatalog
import com.adriandeleon.friends.domain.post.PostRepository
import com.adriandeleon.friends.domain.timeline.TimelineRepository
import com.adriandeleon.friends.domain.user.InMemoryUserCatalog
import com.adriandeleon.friends.domain.user.InMemoryUserDataStore
import com.adriandeleon.friends.domain.user.UserCatalog
import com.adriandeleon.friends.domain.user.UserDataStore
import com.adriandeleon.friends.domain.user.UserRepository
import com.adriandeleon.friends.domain.validation.RegexCredentialsValidator
import com.adriandeleon.friends.postcomposer.CreatePostViewModel
import com.adriandeleon.friends.signup.SignUpViewModel
import com.adriandeleon.friends.timeline.TimelineViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module {
    single<CoroutineDispatchers> { DefaultDispatchers() }
    single<UserCatalog> { InMemoryUserCatalog() }
    single<PostCatalog> { InMemoryPostCatalog() }
    single<UserDataStore> { InMemoryUserDataStore() }
    factory { RegexCredentialsValidator() }
    factory { UserRepository(userCatalog = get(), userDataStore = get()) }
    factory { TimelineRepository(userCatalog = get(), postCatalog = get()) }
    factory { PostRepository(userDataStore = get(), postCatalog = get()) }

    viewModel {
        SignUpViewModel(
            credentialsValidator = get(),
            userRepository = get(),
            dispatchers = get(),
        )
    }

    viewModel {
        TimelineViewModel(timelineRepository = get(), dispatchers = get())
    }

    viewModel {
        CreatePostViewModel(postRepository = get(), dispatchers = get())
    }
}