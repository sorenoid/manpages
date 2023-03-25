package com.bandor

import FileSlurper
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.bandor.plugins.*
import configureTemplating
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun main() {
    //prime this list so it is initialized for the first get request.
    CoroutineScope(Dispatchers.Default).launch {
        println("ready to serve up ${FileSlurper.manuals.size} manuals")
        FileSlurper.sentinel.value = ServerStatus.Ready
    }
    embeddedServer(Netty, port = (System.getenv("PORT")?:"8080").toInt(), module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureTemplating()
    configureRouting()
}
