package org.acme.rest

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.acme.dto.OrderRequest
import org.acme.model.Order
import org.acme.service.OrderService

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class OrderResource(private val orderService: OrderService) {

    @GET
    fun getAll(): List<Order> = orderService.getAll()

    @GET
    @Path("/{id}")
    fun getById(@PathParam("id") id: Long): Order? = orderService.getById(id)

    @POST
    fun createOrder(orderRequest: OrderRequest): Response {
        val order = orderService.createOrder(orderRequest)
        return Response.status(Response.Status.CREATED).entity(order).build()
    }

    @DELETE
    @Path("/{id}")
    fun deleteOrder(@PathParam("id") id: Long): Boolean = orderService.deleteOrder(id)
}
