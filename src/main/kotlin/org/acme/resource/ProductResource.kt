package org.acme.resource

import jakarta.transaction.Transactional
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.acme.dto.ProductRequest
import org.acme.model.Product
import org.acme.repository.*

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class ProductResource(
    private val productRepo: ProductRepository,
    private val genderRepo: GenderRepository,
    private val ageGroupRepo: AgeGroupRepository,
    private val categoryRepo: CategoryRepository,
    private val brandRepo: BrandRepository
) {

    @GET
    fun getAll(): List<Product> = productRepo.listAll()

    @GET
    @Path("/{id}")
    fun getById(@PathParam("id") id: Long): Product? = productRepo.findById(id)

    @POST
    @Transactional
    fun create(productRequest: ProductRequest): Response {
        val gender = genderRepo.findById(productRequest.genderId)
            ?: throw NotFoundException("Gender not found with id ${productRequest.genderId}")
        val ageGroup = ageGroupRepo.findById(productRequest.ageGroupId)
            ?: throw NotFoundException("Age group not found with id ${productRequest.ageGroupId}")
        val category = categoryRepo.findById(productRequest.categoryId)
            ?: throw NotFoundException("Category not found with id ${productRequest.categoryId}")
        val brand = brandRepo.findById(productRequest.brandId)
            ?: throw NotFoundException("Brand not found with id ${productRequest.brandId}")

        val product = Product(
            name = productRequest.name,
            description = productRequest.description,
            price = productRequest.price,
            gender = gender,
            ageGroup = ageGroup,
            category = category,
            brand = brand
        )

        productRepo.persist(product)
        return Response.status(Response.Status.CREATED).entity(product).build()
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    fun delete(@PathParam("id") id: Long): Boolean = productRepo.deleteById(id)
}
