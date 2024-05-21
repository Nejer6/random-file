package com.github.nejer6.plugins

import com.github.nejer6.RandomPathPicker
import com.github.nejer6.models.RandomFileResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting() {
    val dirPath = "/random"
    val dir = File(dirPath)
//    val randomFilePicker = RandomFilePicker(dir)
    val randomPathPicker = RandomPathPicker(dir)

    routing {
        get("randomfile") {
            val randomFile = randomPathPicker.getRandomPart() ?: return@get call.respond(
                HttpStatusCode.NotFound,
                "File not found"
            )
            val files = randomFile.allPrevious(
                //filter = { it.name.first() != '.' }
            ).map { it.file!! }
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
