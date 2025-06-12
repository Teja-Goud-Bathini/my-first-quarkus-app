package org.acme.repository

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import org.acme.model.Category

@ApplicationScoped
class CategoryRepository : PanacheRepository<Category>
