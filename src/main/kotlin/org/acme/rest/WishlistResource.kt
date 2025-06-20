package org.acme.rest

import jakarta.transaction.Transactional
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.acme.dto.WishlistRequest
import org.acme.dto.WishlistResponse
import org.acme.model.Wishlist
import org.acme.service.WishlistService

@Path("/wishlists")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class WishlistResource(
    private val wishlistService: WishlistService
) {

    @GET
    fun getAll(): List<Wishlist> = wishlistService.getAll()

    @POST
    @Transactional
    fun addToWishlist(request: WishlistRequest): Response {
        val response: WishlistResponse = wishlistService.addToWishlist(request)
        return Response.status(Response.Status.CREATED).entity(response).build()
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    fun removeFromWishlist(@PathParam("id") id: Long): Boolean = wishlistService.removeFromWishlist(id)
}
