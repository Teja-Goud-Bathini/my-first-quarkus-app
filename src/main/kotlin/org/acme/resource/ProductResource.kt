package org.acme.resource

import jakarta.transaction.Transactional
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.acme.model.Product
import org.acme.repository.ProductRepository

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class ProductResource(private val productRepository: ProductRepository) {

    @GET
    fun getAll(): List<Product> = productRepository.listAll()

    @GET
    @Path("/{id}")
    fun getById(@PathParam("id") id: Long): Product? = productRepository.findById(id)

    @POST
    @Transactional
    fun createProduct(product: Product): Product {
        productRepository.persist(product)
        return product
    }

    @PUT
    @Path("/{id}")
    @Transactional
    fun updateProduct(@PathParam("id") id: Long, updatedProduct: Product): Product? {
        val product = productRepository.findById(id)
        product?.apply {
            name = updatedProduct.name
            description = updatedProduct.description
            price = updatedProduct.price
        }
        return product
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    fun deleteProduct(@PathParam("id") id: Long): Boolean = productRepository.deleteById(id)
}
