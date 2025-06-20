package org.acme.rest

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.acme.model.Brand
import org.acme.service.BrandService

@Path("/brands")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class BrandResource(private val brandService: BrandService) {

    @GET
    fun list(): List<Brand> = brandService.listAll()

    @POST
    fun add(brand: Brand): Response {
        val created = brandService.add(brand)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{id}")
    fun update(@PathParam("id") id: Long, brand: Brand): Response {
        val updated = brandService.update(id, brand)
            ?: return Response.status(Response.Status.NOT_FOUND).build()
        return Response.ok(updated).build()
    }
}
