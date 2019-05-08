package be.countries

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.http.ContentType
import io.ktor.jackson.JacksonConverter
import io.ktor.jackson.jackson
import io.ktor.routing.Routing


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation){
        jackson {
            register(ContentType.Application.Json, JacksonConverter())
        }

    }
    val dbFactory = DatabaseFactory()

    dbFactory.init()

    val countriesService = CountriesService(dbFactory)

    install(Routing){
        country(countriesService)
    }
}

