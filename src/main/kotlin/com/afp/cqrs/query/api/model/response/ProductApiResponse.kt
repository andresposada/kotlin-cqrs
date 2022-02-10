package com.afp.cqrs.query.api.model.response

import java.math.BigDecimal

data class ProductApiResponse(
    val id: String,
    val name : String,
    val price: BigDecimal,
    val quantity: Int
)
