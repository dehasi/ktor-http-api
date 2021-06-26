package me.dehasi.httpapi.models

import customerStorage
import io.ktor.application.call
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route

fun Route.customerRouting() = {
    route("/customer") {
        get {
            if (customerStorage.isNotEmpty()) call.respond(customerStorage)
            else call.respondText("not found", status = NotFound)
        }
        get("{id}") {}
        post { }
        delete("{id}") {}
    }
}