package com.bandor.model

import kotlinx.serialization.Serializable

@Serializable
data class Manual (
    val name: String,
    val description: String,
    val body: String,
    val headings: MutableList<String> = mutableListOf(),
    val level: Int = 1,
    val path: String = "$level/$name.html"
)

