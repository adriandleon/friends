package com.adriandeleon.friends.app

import android.app.Application
import org.koin.core.context.startKoin

@Suppress("unused")
class FriendsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(applicationModule)
        }
    }
}