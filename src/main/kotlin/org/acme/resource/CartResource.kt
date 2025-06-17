package org.acme.resource

import jakarta.transaction.Transactional
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.acme.model.Cart
import org.acme.dto.CartRequest
import org.acme.repository.CartRepository
import org.acme.repository.ProductRepository
import org.acme.repository.UserRepository

@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class CartResource(
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository
) {

    @GET
    fun getAll(): List<Cart> = cartRepository.listAll()

    @GET
    @Path("/{id}")
    fun getById(@PathParam("id") id: Long): Cart? = cartRepository.findById(id)

    @POST
    @Transactional
    fun createCart(cartRequest: CartRequest): Response {
        val user = userRepository.findById(cartRequest.userId)
            ?: throw NotFoundException("User not found with id ${cartRequest.userId}")

        val products = cartRequest.productIds.map { productId ->
            productRepository.findById(productId)
                ?: throw NotFoundException("Product not found with id $productId")
        }

        val cart = Cart(
            user = user,
            products = products.toMutableList()
        )

        cartRepository.persist(cart)
        return Response.status(Response.Status.CREATED).entity(cart).build()
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    fun deleteCart(@PathParam("id") id: Long): Boolean = cartRepository.deleteById(id)
}
