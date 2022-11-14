package com.bandor

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.bandor.plugins.*
import configureTemplating

fun main() {
    embeddedServer(Netty, port = (System.getenv("PORT")?:"5000").toInt(), module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureTemplating()
    configureRouting()
}
