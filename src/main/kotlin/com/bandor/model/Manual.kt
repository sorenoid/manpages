package com.bandor.model

class Manual constructor(val name: String, val body: String, val headings: List<String> = listOf(), val level: Int = 1, val path: String = "$level/$name.html")

