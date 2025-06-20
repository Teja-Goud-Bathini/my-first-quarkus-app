package org.acme.rest

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.acme.model.User
import org.acme.service.UserService

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class UserResource(private val userService: UserService) {

    @GET
    fun getAll(): List<User> = userService.getAllUsers()

    @GET
    @Path("/{id}")
    fun getById(@PathParam("id") id: Long): User? = userService.getUserById(id)

    @POST
    fun createUser(user: User): User = userService.createUser(user)

    @PUT
    @Path("/{id}")
    fun updateUser(@PathParam("id") id: Long, updatedUser: User): User? =
        userService.updateUser(id, updatedUser)

    @DELETE
    @Path("/{id}")
    fun deleteUser(@PathParam("id") id: Long): Boolean = userService.deleteUser(id)
}
