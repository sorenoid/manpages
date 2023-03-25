package com.bandor.model

import kotlinx.serialization.Serializable

@Serializable
data class Manual constructor(
    val name: String,
    val body: String,
    val headings: MutableList<String> = mutableListOf(),
    val level: Int = 1,
    val path: String = "$level/$name.html"
)

