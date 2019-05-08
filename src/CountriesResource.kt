package be.countries

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*

fun Route.country(countriesService: CountriesService){

    get("/api/countries/"){

        //call.respond(HttpStatusCode.OK, "Test")
        call.respond(HttpStatusCode.OK, countriesService.getAllCountries())
    }

    get("/api/countries/{id}"){
        val id = call.parameters["id"]?.toInt()
        countriesService.getCountry(id!!)?.let { it -> call.respond(it) }
    }

    post("/api/countries/"){
        val country = call.receive<Country>()
        call.respond(HttpStatusCode.Created, countriesService.createCountry(country)!!)
    }

    put("/api/countries/"){
        val country = call.receive<Country>()
        call.respond(HttpStatusCode.OK , countriesService.updateCountry(country))
    }

    delete("/api/countries/{id}"){
        val id = call.parameters["id"]?.toInt()
        countriesService.deleteCountry(id)
        call.respond(HttpStatusCode.NotFound)
    }

}