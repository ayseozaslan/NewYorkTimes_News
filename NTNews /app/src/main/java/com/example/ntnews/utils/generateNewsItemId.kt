package com.example.ntnews.utils

import java.util.UUID

fun generateNewsItemId(newsUrl: String): String {
    val id = UUID.nameUUIDFromBytes(newsUrl.toByteArray()).toString()
    return id
}
