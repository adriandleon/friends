package com.adriandeleon.friends.infrastructure

import java.util.UUID

class UUIDGenerator : IdGenerator {

    override fun next(): String {
        return UUID.randomUUID().toString()
    }
}
