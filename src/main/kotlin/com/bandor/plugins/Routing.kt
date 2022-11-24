package com.bandor.plugins

import Html2Json
import io.ktor.server.routing.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*
import io.ktor.server.util.*

fun Application.configureRouting() {

    routing {
        static("/static") {
            resources("manual-html")
        }

        get("/") {
            call.respond(FreeMarkerContent("index.ftl", mapOf("manuals" to Html2Json.manuals)))
        }

        get("{level}") {
            try {
                val level = call.parameters.getOrFail<Int>("level").toInt()
                Html2Json.manuals.find { it.level == level && it.name.lowercase() == "intro" }?.let {
                    call.respond(
                        FreeMarkerContent(
                            "show.ftl",
                            mapOf("manual" to it)
                        )
                    )
                } ?: call.respondRedirect("/")
            } catch (e: Exception) {
                call.respondRedirect("/")
            }

        }

        get("{level}/{name}") {
            try {
                val level = call.parameters.getOrFail<Int>("level").toInt()
                val name = call.parameters.getOrFail<String>("name").toString()
                Html2Json.manuals.find { it.level == level && it.name.lowercase() == name.lowercase() }?.let {
                    call.respond(
                        FreeMarkerContent(
                            "show.ftl",
                            mapOf("manual" to it)
                        )
                    )
                } ?: call.respondRedirect("/")
            } catch (e: Exception) {
                call.respondRedirect("/")
            }
        }
    }
}

