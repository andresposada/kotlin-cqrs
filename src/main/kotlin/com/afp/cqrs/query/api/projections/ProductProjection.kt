package com.afp.cqrs.query.api.projections

import com.afp.cqrs.command.api.model.data.ProductEntity
import com.afp.cqrs.command.api.model.data.ProductRepository
import com.afp.cqrs.common.model.Product
import com.afp.cqrs.query.api.queries.GetProductQueries
import org.axonframework.queryhandling.QueryHandler
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class ProductProjection(
    val productRepository: ProductRepository
) {

    @QueryHandler
    fun handle(getProductQueries: GetProductQueries): List<Product> = if (getProductQueries.all) {
        productRepository.findAll().map { it.toDomain() }
    } else {
        productRepository.findAll(Pageable.ofSize(10)).content.map { it.toDomain() }
    }

}