package me.dehasi.httpapi.models

import Customer
import customerStorage
import io.ktor.application.call
import io.ktor.http.HttpStatusCode.Companion.Accepted
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.request.receive
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
        get("{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText("id is needed", status = BadRequest)
            val customer = customerStorage.find { it.id == id } ?: return@get call.respond(status = NotFound, "")
            call.respond(customer)
        }
        post {
            val customer = call.receive<Customer>()
            customerStorage.add(customer)
            call.respond(status = Created, "")
        }
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respondText("id is needed", status = BadRequest)
            if (customerStorage.removeIf { it.id == id }) call.respondText("Customer removed", status = Accepted)
            else call.respond(status = NotFound, "")
        }
    }
}