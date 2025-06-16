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
class CategoryResource(private val categoryRepository: CategoryRepository) {

    @GET
    fun getAll(): List<Category> = categoryRepository.listAll()

    @GET
    @Path("/{id}")
    fun getById(@PathParam("id") id: Long): Response {
        val category = categoryRepository.findById(id)
            ?: return Response.status(Response.Status.NOT_FOUND).build()
        return Response.ok(category).build()
    }

    @POST
    @Transactional
    fun create(category: Category): Response {
        categoryRepository.persist(category)
        return Response.status(Response.Status.CREATED).entity(category).build()
    }

    @PUT
    @Path("/{id}")
    @Transactional
    fun update(@PathParam("id") id: Long, updatedCategory: Category): Response {
        val category = categoryRepository.findById(id)
            ?: return Response.status(Response.Status.NOT_FOUND).build()

        category.name = updatedCategory.name
        return Response.ok(category).build()
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    fun delete(@PathParam("id") id: Long): Response {
        val deleted = categoryRepository.deleteById(id)
        return if (deleted) Response.noContent().build()
        else Response.status(Response.Status.NOT_FOUND).build()
    }
}
