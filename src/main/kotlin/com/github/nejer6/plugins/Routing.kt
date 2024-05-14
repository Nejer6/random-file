package com.github.nejer6.plugins

import com.github.nejer6.RandomFilePicker
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.*
import java.io.File

fun Application.configureRouting() {
    val dirPath = "/random"
    val dir = File(dirPath)
    val randomFilePicker = RandomFilePicker(dir)

    routing {
        get("/") {
            call.respond(
                ThymeleafContent(
                    "index",
                    mapOf("fileName" to randomFilePicker.getRandomFile().name)
                )
            )
        }

        get("refresh") {
            randomFilePicker.updateFiles()
            call.respond(HttpStatusCode.OK)
        }
    }
}
