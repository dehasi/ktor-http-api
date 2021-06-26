import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod.Companion.Get
import io.ktor.http.HttpMethod.Companion.Post
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import me.dehasi.httpapi.module
import org.junit.Test
import kotlin.test.assertEquals

class OrderRouteTests {

    @Test fun testGetOrder() {
        withTestApplication({ module(testing = true) }) {

            handleRequest(Post, "/customer") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    """
                        {
                          "id": "300",
                          "firstName": "Mary",
                          "lastName": "Smith",
                          "email": "mary.smith@company.com"
                        }
                    """.trimIndent()
                )
            }.apply {
                assertEquals(OK, response.status())
            }
            handleRequest(Get, "/customer/300").apply {
                assertEquals(
                    """{}""",
                    response.content
                )
                assertEquals(OK, response.status())
            }
        }
    }
}
