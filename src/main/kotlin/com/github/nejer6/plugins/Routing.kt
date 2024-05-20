package com.github.nejer6.plugins

import com.github.nejer6.RandomFilePicker
import com.github.nejer6.models.RandomFileResponse
import com.github.nejer6.util.previousFiles
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
            val files = randomFile.previousFiles().drop(1)
            val list = files.map { file ->
                val parts = file.absolutePath.split('/')
                val location = "/" + parts.subList(2, parts.size - 1).joinToString("/")

                RandomFileResponse(
                    fileName = file.name,
                    location = location
                )
            }

            call.respond(list)
        }
    }
}
