package org.acme.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.NotFoundException
import org.acme.dto.WishlistRequest
import org.acme.dto.WishlistResponse
import org.acme.model.Wishlist
import org.acme.repository.ProductRepository
import org.acme.repository.UserRepository
import org.acme.repository.WishlistRepository

@ApplicationScoped
class WishlistService(
    private val wishlistRepository: WishlistRepository,
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository
) {

    fun getAll(): List<Wishlist> = wishlistRepository.listAll()

    fun addToWishlist(request: WishlistRequest): WishlistResponse {
        val user = userRepository.findById(request.userId)
            ?: throw NotFoundException("User not found")

        val product = productRepository.findById(request.productId)
            ?: throw NotFoundException("Product not found")

        val wishlist = Wishlist(user = user, product = product)
        wishlistRepository.persist(wishlist)

        return WishlistResponse(
            id = wishlist.id ?: 0,
            userId = user.id ?: 0,
            productId = product.id ?: 0,
            productName = product.name
        )
    }

    fun removeFromWishlist(id: Long): Boolean = wishlistRepository.deleteById(id)
}
