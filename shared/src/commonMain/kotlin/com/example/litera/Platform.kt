package com.example.litera

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform