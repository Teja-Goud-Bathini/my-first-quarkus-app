package org.acme.rest

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.acme.model.AgeGroup
import org.acme.service.AgeGroupService

@Path("/age-groups")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AgeGroupResource(private val ageGroupService: AgeGroupService) {

    @GET
    fun list(): List<AgeGroup> = ageGroupService.listAll()

    @GET
    @Path("/{id}")
    fun getById(@PathParam("id") id: Long): Response {
        val ageGroup = ageGroupService.findById(id)
            ?: return Response.status(Response.Status.NOT_FOUND).build()
        return Response.ok(ageGroup).build()
    }

    @POST
    fun add(ageGroup: AgeGroup): Response {
        val created = ageGroupService.add(ageGroup)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{id}")
    fun update(@PathParam("id") id: Long, updated: AgeGroup): Response {
        val existing = ageGroupService.update(id, updated)
            ?: return Response.status(Response.Status.NOT_FOUND).build()
        return Response.ok(existing).build()
    }

    @DELETE
    @Path("/{id}")
    fun delete(@PathParam("id") id: Long): Response {
        return if (ageGroupService.delete(id))
            Response.noContent().build()
        else
            Response.status(Response.Status.NOT_FOUND).build()
    }
}
