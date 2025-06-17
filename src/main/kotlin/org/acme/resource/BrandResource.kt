package org.acme.resource

import jakarta.transaction.Transactional
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.acme.model.Brand
import org.acme.repository.BrandRepository

@Path("/brands")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class BrandResource(private val brandRepo: BrandRepository) {

    @GET
    fun list(): List<Brand> = brandRepo.listAll()

    @PUT
    @Path("/{id}")
    @Transactional
    fun update(@PathParam("id") id: Long, brand: Brand): Response {
        val existingBrand = brandRepo.findById(id)
            ?: return Response.status(Response.Status.NOT_FOUND).build()

        existingBrand.name = brand.name
        return Response.ok(existingBrand).build()
    }


    @POST
    @Transactional
    fun add(brand: Brand): Response {
        brandRepo.persist(brand)
        return Response.status(Response.Status.CREATED).entity(brand).build()
    }
}
