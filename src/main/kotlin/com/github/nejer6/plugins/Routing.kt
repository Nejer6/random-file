package com.github.nejer6.plugins

import com.github.nejer6.RandomFilePicker
import com.github.nejer6.models.RandomFileResponse
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
            val randomFile = randomFilePicker.getRandomFile() ?: return@get call.respond(
                HttpStatusCode.NotFound,
                "File not found"
            )

            call.respond(
                ThymeleafContent(
                    "index",
                    mapOf("fileName" to randomFile.name)
                )
            )
        }

        get("randomfile") {
            val randomFile = randomFilePicker.getRandomFile() ?: return@get call.respond(
                HttpStatusCode.NotFound,
                "File not found"
            )
            val name = randomFile.name
            val location = randomFile.absolutePath
            val parts = location.split('/')
            val resultLocation = "/" + parts.subList(2, parts.size - 1).joinToString("/")

            call.respond(
                RandomFileResponse(
                    fileName = name,
                    location = resultLocation
                )
            )
        }

        get("refresh") {
            randomFilePicker.updateFiles()
            call.respond(HttpStatusCode.OK)
        }
    }
}
