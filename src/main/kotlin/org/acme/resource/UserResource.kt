


package org.acme.resource

import jakarta.transaction.Transactional
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.acme.model.User
import org.acme.repository.UserRepository

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class UserResource(private val userRepository: UserRepository) {

    @GET
    fun getAll(): List<User> = userRepository.listAll()




    @GET
    @Path("/{id}")
    fun getById(@PathParam("id") id: Long): User? = userRepository.findById(id)

    @POST
    @Transactional
    fun createUser(user: User): User {
        userRepository.persist(user)
        return user
    }

    @PUT
    @Path("/{id}")
    @Transactional
    fun updateUser(@PathParam("id") id: Long, updatedUser: User): User? {
        val user = userRepository.findById(id)
        user?.apply {
            name = updatedUser.name
            email = updatedUser.email
            age = updatedUser.age
        }

        return user
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    fun deleteUser(@PathParam("id") id: Long): Boolean = userRepository.deleteById(id)
}
