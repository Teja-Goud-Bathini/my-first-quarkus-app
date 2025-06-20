package org.acme.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.acme.dto.CartRequest
import org.acme.model.Cart
import org.acme.repository.CartRepository
import org.acme.repository.ProductRepository
import org.acme.repository.UserRepository
import jakarta.ws.rs.NotFoundException

@ApplicationScoped
class CartService(
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository
) {

    fun listAll(): List<Cart> = cartRepository.listAll()

    fun findById(id: Long): Cart? = cartRepository.findById(id)

    @Transactional
    fun createCart(cartRequest: CartRequest): Cart {
        val user = userRepository.findById(cartRequest.userId)
            ?: throw NotFoundException("User not found with id ${cartRequest.userId}")

        val products = cartRequest.productIds.map { id ->
            productRepository.findById(id)
                ?: throw NotFoundException("Product not found with id $id")
        }

        val cart = Cart(user = user, products = products.toMutableList())
        cartRepository.persist(cart)
        return cart
    }

    @Transactional
    fun deleteCart(id: Long): Boolean = cartRepository.deleteById(id)
}
