package org.acme.resource

import jakarta.transaction.Transactional
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.acme.dto.WishlistRequest
import org.acme.dto.WishlistResponse
import org.acme.model.ProductSize
import org.acme.model.Wishlist
import org.acme.repository.ProductRepository
import org.acme.repository.UserRepository
import org.acme.repository.WishlistRepository

@Path("/wishlists")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class WishlistResource(
    private val wishlistRepository: WishlistRepository,
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository
) {

    @GET
    fun getAll(): List<Wishlist> = wishlistRepository.listAll()

    @POST
    @Transactional
    fun addToWishlist(request: WishlistRequest): Response {
        val user = userRepository.findById(request.userId)
            ?: throw NotFoundException("User not found")

        val product = productRepository.findById(request.productId)
            ?: throw NotFoundException("Product not found")

        val wishlist = Wishlist(user = user, product = product)
        wishlistRepository.persist(wishlist)

        val response = wishlist.id?.let {
            user.id?.let { it1 ->
                product.id?.let { it2 ->
                    WishlistResponse(
                        id = it,
                        userId = it1,
                        productId = it2,
                        productName = product.name
                    )
                }
            }
        }

        return Response.status(Response.Status.CREATED).entity(response).build()
    }


    @DELETE
    @Path("/{id}")
    @Transactional
    fun removeFromWishlist(@PathParam("id") id: Long): Boolean {
        return wishlistRepository.deleteById(id)
    }
}
