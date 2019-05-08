import be.countries.module
import io.ktor.application.Application
import io.ktor.http.*
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class CountriesTests{

    @Test
    fun testRequests() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, "/api/countries")) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
        with(handleRequest(HttpMethod.Get, "/")) {
            assertFalse(requestHandled)
        }
    }

    @Test
    fun createNewCountry() : Unit = withTestApplication(Application::module){
        handleRequest(HttpMethod.Post, "/api/countries") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody("{\n" +
                    "    \"name\": \"Argentina\",\n" +
                    "    \"alpha2code\": \"AR\",\n" +
                    "    \"alpha3code\": \"ARG\",\n" +
                    "    \"numericcode\": \"656\"\n" +
                    "}")
        }.apply {
            assertEquals(201, response.status()?.value)
        }
    }
}