package com.adriandeleon.friends.app

import kotlinx.coroutines.Dispatchers

class DefaultDispatchers : CoroutineDispatchers {
    override val background = Dispatchers.IO
}
