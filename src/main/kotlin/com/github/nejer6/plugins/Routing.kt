package com.github.nejer6.plugins

import com.github.nejer6.RandomPathPicker
import com.github.nejer6.models.RandomFileResponse
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting() {
    val dirPath = "/random"
    val dir = File(dirPath)
    val randomPathPicker = RandomPathPicker(dir) {
        it.name.first() != '.'
    }

    routing {
        get("randomfile") {
            val files = randomPathPicker.getRandomFiles()
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
