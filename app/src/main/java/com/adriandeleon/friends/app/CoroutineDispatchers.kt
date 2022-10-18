package com.adriandeleon.friends.app

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatchers {
    val background: CoroutineDispatcher
}