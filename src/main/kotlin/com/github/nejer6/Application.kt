package com.github.nejer6

import com.github.nejer6.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.thymeleaf.*
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import org.thymeleaf.templateresolver.FileTemplateResolver

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(Thymeleaf) {
        setTemplateResolver((if (developmentMode) {
            FileTemplateResolver().apply {
                cacheManager = null
                prefix = "src/main/resources/templates/"
            }
        } else {
            ClassLoaderTemplateResolver().apply {
                prefix = "templates/"
            }
        }).apply {
            suffix = ".html"
            characterEncoding = "utf-8"
        })
    }
    configureRouting()
}
