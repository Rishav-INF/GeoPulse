package com.example

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureAdministration()
    configureSockets()
    configureSerialization()
    configureDatabases()
    configureSecurity()
    configureHTTP()

    install(ContentNegotiation) {
        json()
    }

    DatabaseFactory.init()
    routing{
        userRoutes
    }

}
