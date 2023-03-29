package com.adriandeleon.friends.infrastructure

interface IdGenerator {
    fun next(): String
}