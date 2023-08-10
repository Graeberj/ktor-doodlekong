package com.plcoding

import com.plcoding.routes.createRoomRoute
import com.plcoding.session.DrawingSession
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.gson.*
import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.websocket.*
import io.ktor.http.cio.websocket.*
import io.ktor.sessions.*
import io.ktor.util.*
import java.time.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

val server = DrawingServer()

@Suppress("unused") // Referenced in application.conf
@JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(Sessions){
        cookie<DrawingSession>("SESSION")
    }
    intercept(ApplicationCallPipeline.Features){
        if (call.sessions.get<DrawingSession>() == null){
            val clientId = call.parameters["client_id"] ?: ""
            call.sessions.set(DrawingSession(clientId, generateNonce()))
        }
    }

    install(ContentNegotiation) {
        gson {
        }
    }

    install(CallLogging)
    install(WebSockets)

    install(Routing){
        createRoomRoute()
    }
}

