package org.acme.resource

import jakarta.transaction.Transactional
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.acme.model.Brand
import org.acme.model.Gender

import org.acme.repository.GenderRepository
@Path("/genders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class GenderResource(private val genderRepo: GenderRepository) {

    @GET
    fun list(): List<Gender> = genderRepo.listAll()
    @PUT
    @Path("/{id}")
    @Transactional
    fun update(@PathParam("id") id: Long, gender: Gender): Response {
        val existingGender = genderRepo.findById(id)
            ?: return Response.status(Response.Status.NOT_FOUND).build()

        existingGender.name = gender.name
        return Response.ok(existingGender).build()
    }

    @POST
    @Transactional
    fun add(gender: Gender): Response {
        genderRepo.persist(gender)
        return Response.status(Response.Status.CREATED).entity(gender).build()
    }
}
