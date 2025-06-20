package org.acme.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.acme.dto.OrderRequest
import org.acme.model.Order
import org.acme.repository.OrderRepository
import org.acme.repository.ProductRepository
import org.acme.repository.UserRepository
import jakarta.ws.rs.NotFoundException

@ApplicationScoped
class OrderService(
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository
) {

    fun getAll(): List<Order> = orderRepository.listAll()

    fun getById(id: Long): Order? = orderRepository.findById(id)

    @Transactional
    fun createOrder(orderRequest: OrderRequest): Order {
        val user = userRepository.findById(orderRequest.userId)
            ?: throw NotFoundException("User not found with id ${orderRequest.userId}")

        val products = orderRequest.productIds.map { productId ->
            productRepository.findById(productId)
                ?: throw NotFoundException("Product not found with id $productId")
        }

        val totalPrice = products.sumOf { it.price ?: 0.0 }

        val order = Order(
            orderDate = orderRequest.orderDate,
            totalPrice = totalPrice,
            user = user,
            products = products.toMutableList()
        )


        orderRepository.persist(order)
        return order
    }

    @Transactional
    fun deleteOrder(id: Long): Boolean = orderRepository.deleteById(id)
}
