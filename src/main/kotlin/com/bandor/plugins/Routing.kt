package com.bandor.plugins

import FileSlurper
import io.ktor.server.routing.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*
import io.ktor.server.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun Application.configureRouting() {

    routing {
        runBlocking {
            FileSlurper.sentinel.takeWhile {
                it == ServerStatus.NotReady
            }
        }

       // trace { application.log.trace(it.buildText()) }
        static {
            staticBasePackage = "manual-html"
            resource("robots.txt")
            resource("sitemap.xml.gz")
            resource("___/css/style.css", resource = "styles.css" )
            resource("___/css/layout.css", resource = "layout.css" )
            resource("___/css/bg.gif", resource = "bg.gif" )
        }

        get("/") {
            call.respond(FreeMarkerContent("index.ftl", mapOf("manuals" to FileSlurper.manuals)))
        }

        get("{level}/{name}") {
            try {
                val level = call.parameters.getOrFail<Int>("level").toInt()
                val name = call.parameters.getOrFail<String>("name").toString()
                //application.log.debug("GOT $level and $name")
                val manual = FileSlurper.manuals.find { man -> man.level == level && man.name.lowercase() == name.lowercase() }
                if (manual != null) {
                    call.respond(
                        FreeMarkerContent(
                            "show.ftl",
                            mapOf("manual" to manual)
                        )
                    )
                } else {
                    call.respondRedirect("/")
                }
            } catch (e: Exception) {
                //application.log.debug("GOT exception ")
                e.printStackTrace()
                call.respondRedirect("/")
            }
        }

        get("{level}") {
            try {
                val param = call.parameters.getOrFail("level")
                //application.log.debug("GOT $param")
                param.toIntOrNull()?.let { level ->
                    FileSlurper.manuals.find { it.level == level && it.name.lowercase() == "intro" }?.let {
                        call.respond(
                            FreeMarkerContent(
                                "show.ftl",
                                mapOf("manual" to it)
                            )
                        )
                    } ?: call.respondRedirect("/")
                } ?: run {
                    //application.log.debug("GOT $param as name")
                    FileSlurper.manuals.find { it.name.lowercase() == param.lowercase() }?.let {
                        call.respond(
                            FreeMarkerContent(
                                "show.ftl",
                                mapOf("manual" to it)
                            )
                        )
                    } ?: call.respondRedirect("/")

                }

            } catch (e: Exception) {
                //application.log.debug("GOT ${e.stackTrace}")
                call.respondRedirect("/")
            }

        }

    }
}

