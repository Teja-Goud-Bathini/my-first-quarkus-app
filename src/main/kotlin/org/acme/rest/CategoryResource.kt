package org.acme.rest

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.acme.model.Category
import org.acme.service.CategoryService

@Path("/categories")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class CategoryResource(private val categoryService: CategoryService) {

    @GET
    fun getAll(): List<Category> = categoryService.listAll()

    @GET
    @Path("/{id}")
    fun getById(@PathParam("id") id: Long): Response {
        val category = categoryService.findById(id)
        return Response.ok(category).build()
    }

    @POST
    fun create(category: Category): Response {
        val created = categoryService.create(category)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{id}")
    fun update(@PathParam("id") id: Long, updatedCategory: Category): Response {
        val updated = categoryService.update(id, updatedCategory)
        return Response.ok(updated).build()
    }

    @DELETE
    @Path("/{id}")
    fun delete(@PathParam("id") id: Long): Response {
        val deleted = categoryService.delete(id)
        return if (deleted) Response.noContent().build()
        else Response.status(Response.Status.NOT_FOUND).build()
    }
}
