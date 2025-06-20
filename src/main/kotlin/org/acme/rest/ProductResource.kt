package org.acme.rest

import jakarta.transaction.Transactional
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.acme.dto.ProductRequest
import org.acme.model.Product
import org.acme.service.ProductService

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class ProductResource(
    private val productService: ProductService
) {

    @GET
    fun getAll(): List<Product> = productService.getAll()

    @GET
    @Path("/{id}")
    fun getById(@PathParam("id") id: Long): Product? = productService.getById(id)

    @GET
    @Path("/search")
    fun queryProducts(
        @QueryParam("gender") gender: String?,
        @QueryParam("category") category: String?,
        @QueryParam("brand") brand: String?,
        @QueryParam("ageGroup") ageGroup: String?,
        @QueryParam("name") name: String?,
        @QueryParam("priceMin") priceMin: Double?,
        @QueryParam("priceMax") priceMax: Double?,
        @QueryParam("sort") sort: String?
    ): Response {
        val products = productService.searchProducts(gender, category, brand, ageGroup, name, priceMin, priceMax, sort)
        return Response.ok(products).build()
    }

    @GET
    @Path("/filter")
    fun filterProducts(
        @QueryParam("genderId") genderId: Long?,
        @QueryParam("categoryId") categoryId: Long?,
        @QueryParam("brandId") brandId: Long?,
        @QueryParam("ageGroupId") ageGroupId: Long?,
        @QueryParam("priceMin") priceMin: Double?,
        @QueryParam("priceMax") priceMax: Double?,
        @QueryParam("sort") sort: String?
    ): Response {
        val products = productService.filterProducts(genderId, categoryId, brandId, ageGroupId, priceMin, priceMax, sort)
        return Response.ok(products).build()
    }

    @POST
    @Transactional
    fun create(productRequest: ProductRequest): Response {
        val product = productService.create(productRequest)
        return Response.status(Response.Status.CREATED).entity(product).build()
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    fun delete(@PathParam("id") id: Long): Boolean = productService.delete(id)
}
