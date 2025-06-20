package org.acme.rest

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.acme.model.Gender
import org.acme.service.GenderService

@Path("/genders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class GenderResource(private val genderService: GenderService) {

    @GET
    fun list(): List<Gender> = genderService.listAll()

    @POST
    fun add(gender: Gender): Response {
        val created = genderService.add(gender)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{id}")
    fun update(@PathParam("id") id: Long, gender: Gender): Response {
        val updated = genderService.update(id, gender)
            ?: return Response.status(Response.Status.NOT_FOUND).build()
        return Response.ok(updated).build()
    }
}
