package org.acme

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/movies")
class Hello {

    // Dummy in-memory list (you can replace this with DB access)
    private val movies = mutableListOf<MovieDTO>()

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getMovies(): List<MovieDTO> {
        return movies
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun createMovie(movieDto: MovieDTO): MovieDTO {
        movies.add(movieDto)
        return movieDto
    }
}

data class MovieDTO(
    val id: Int,
    val name: String
)
