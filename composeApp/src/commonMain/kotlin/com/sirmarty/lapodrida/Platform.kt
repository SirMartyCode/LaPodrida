package com.sirmarty.lapodrida

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform