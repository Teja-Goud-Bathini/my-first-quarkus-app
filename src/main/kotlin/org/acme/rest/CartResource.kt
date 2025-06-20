package org.acme.rest

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.acme.dto.CartRequest
import org.acme.model.Cart
import org.acme.service.CartService

@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class CartResource(private val cartService: CartService) {

    @GET
    fun getAll(): List<Cart> = cartService.listAll()

    @GET
    @Path("/{id}")
    fun getById(@PathParam("id") id: Long): Cart? = cartService.findById(id)

    @POST
    fun createCart(cartRequest: CartRequest): Response {
        val cart = cartService.createCart(cartRequest)
        return Response.status(Response.Status.CREATED).entity(cart).build()
    }

    @DELETE
    @Path("/{id}")
    fun deleteCart(@PathParam("id") id: Long): Boolean = cartService.deleteCart(id)
}
