package com.afp.cqrs.query.api.controller

import com.afp.cqrs.common.model.Product
import com.afp.cqrs.query.api.model.response.ProductApiResponse
import com.afp.cqrs.query.api.queries.GetProductQueries
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/products")
class ProductQueryController(
    val queryGateway: QueryGateway
) {

    @GetMapping
    fun getProducts() : ResponseEntity<List<ProductApiResponse>> {
        return ResponseEntity.ok(
        queryGateway
            .query(GetProductQueries(), ResponseTypes.multipleInstancesOf(Product::class.java)).join()
            .map { it.toResponse() })
    }

    fun Product.toResponse() =
        ProductApiResponse(
            id = this.id,
            name = this.name,
            price = this.price,
            quantity = this.quantity
        )

}