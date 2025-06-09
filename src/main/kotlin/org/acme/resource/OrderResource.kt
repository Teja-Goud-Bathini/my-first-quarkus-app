package org.acme.resource

import jakarta.transaction.Transactional
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.acme.model.Order
import org.acme.model.Product
import org.acme.model.User
import org.acme.repository.OrderRepository
import org.acme.repository.ProductRepository
import org.acme.repository.UserRepository
import org.acme.model.OrderRequest
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class OrderResource(
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository
) {

    @GET
    fun getAll(): List<Order> = orderRepository.listAll()

    @GET
    @Path("/{id}")
    fun getById(@PathParam("id") id: Long): Order? = orderRepository.findById(id)

    @POST
    @Transactional
    fun createOrder(orderRequest: OrderRequest): Order {
        val user = userRepository.findById(orderRequest.userId)
            ?: throw NotFoundException("User not found with id ${orderRequest.userId}")

        val products = orderRequest.productIds.map { productId ->
            productRepository.findById(productId)
                ?: throw NotFoundException("Product not found with id $productId")
        }

       val orderDate = orderRequest.orderDate.toString()


        val order = Order(
            orderDate = orderDate,
            user = user,
            products = products.toMutableList()
        )

        orderRepository.persist(order)
        return order
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    fun deleteOrder(@PathParam("id") id: Long): Boolean = orderRepository.deleteById(id)
}