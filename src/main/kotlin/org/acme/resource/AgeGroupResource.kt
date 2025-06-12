package org.acme.resource

import jakarta.transaction.Transactional
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.acme.model.Cart
import org.acme.dto.CartRequest
import org.acme.model.AgeGroup
import org.acme.repository.CartRepository
import org.acme.repository.ProductRepository
import org.acme.repository.AgeGroupRepository
@Path("/age-groups")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AgeGroupResource(private val ageGroupRepo: AgeGroupRepository) {

    @GET
    fun list(): List<AgeGroup> = ageGroupRepo.listAll()

    @POST
    @Transactional
    fun add(ageGroup: AgeGroup): Response {
        ageGroupRepo.persist(ageGroup)
        return Response.status(Response.Status.CREATED).entity(ageGroup).build()
    }
}
