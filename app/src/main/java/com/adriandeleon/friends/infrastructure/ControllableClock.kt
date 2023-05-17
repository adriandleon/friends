package com.adriandeleon.friends.infrastructure

class ControllableClock(private val timestamp: Long) : Clock {

    override fun now(): Long {
        return timestamp
    }
}