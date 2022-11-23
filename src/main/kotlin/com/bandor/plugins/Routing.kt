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
            call.respondRedirect("manuals")
        }

        route("manuals") {
            get {
                call.respond(FreeMarkerContent("index.ftl", mapOf("manuals" to Html2Json.manuals)))
            }

        }

        get("{level}/{name}") {
            val level = try { call.parameters.getOrFail<Int>("level").toInt() } catch (e: Exception) {}
            val name = call.parameters.getOrFail<String>("name").toString()
            call.respond(
                FreeMarkerContent(
                    "show.ftl",
                    mapOf("manual" to Html2Json.manuals.find { it.level == level && it.name.lowercase() == name.lowercase() })
                )
            )
        }
    }
}
