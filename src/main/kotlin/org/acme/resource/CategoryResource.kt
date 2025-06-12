package org.acme.resource

import jakarta.transaction.Transactional
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.acme.model.Category
import org.acme.repository.CategoryRepository

@Path("/categories")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class CategoryResource(
    private val categoryRepository: CategoryRepository
) {

    @GET
    fun getAll(): List<Category> = categoryRepository.listAll()

    @POST
    @Transactional
    fun create(category: Category): Response {
        categoryRepository.persist(category)
        return Response.status(Response.Status.CREATED).entity(category).build()
    }

    @GET
    @Path("/{id}")
    fun getById(@PathParam("id") id: Long): Category? = categoryRepository.findById(id)
}
