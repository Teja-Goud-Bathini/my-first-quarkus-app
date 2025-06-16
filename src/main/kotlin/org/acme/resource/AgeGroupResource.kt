package org.acme.resource

import jakarta.transaction.Transactional
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.acme.model.AgeGroup
import org.acme.repository.AgeGroupRepository

@Path("/age-groups")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AgeGroupResource(private val ageGroupRepo: AgeGroupRepository) {

    @GET
    fun list(): List<AgeGroup> = ageGroupRepo.listAll()

    @GET
    @Path("/{id}")
    fun getById(@PathParam("id") id: Long): Response {
        val ageGroup = ageGroupRepo.findById(id)
            ?: return Response.status(Response.Status.NOT_FOUND).build()
        return Response.ok(ageGroup).build()
    }

    @POST
    @Transactional
    fun add(ageGroup: AgeGroup): Response {
        ageGroupRepo.persist(ageGroup)
        return Response.status(Response.Status.CREATED).entity(ageGroup).build()
    }

    @PUT
    @Path("/{id}")
    @Transactional
    fun update(@PathParam("id") id: Long, updated: AgeGroup): Response {
        val existing = ageGroupRepo.findById(id)
            ?: return Response.status(Response.Status.NOT_FOUND).build()
        existing.name = updated.name
        return Response.ok(existing).build()
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    fun delete(@PathParam("id") id: Long): Response {
        val deleted = ageGroupRepo.deleteById(id)
        return if (deleted) Response.noContent().build()
        else Response.status(Response.Status.NOT_FOUND).build()
    }
}
