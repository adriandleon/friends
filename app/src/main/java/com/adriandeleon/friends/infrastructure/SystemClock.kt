package com.adriandeleon.friends.infrastructure

class SystemClock : Clock {

    override fun now(): Long {
        return System.currentTimeMillis()
    }
}