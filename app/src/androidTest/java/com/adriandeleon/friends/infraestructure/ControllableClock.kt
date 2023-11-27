package com.adriandeleon.friends.infraestructure

import com.adriandeleon.friends.infrastructure.Clock

class ControllableClock(
    private val timestamp: Long
) : Clock {

    override fun now(): Long {
        return timestamp
    }
}