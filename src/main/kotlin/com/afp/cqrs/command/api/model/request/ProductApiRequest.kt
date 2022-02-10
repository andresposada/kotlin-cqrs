package com.afp.cqrs.command.api.model.request

import java.math.BigDecimal

data class AddProductApiRequest(
    val name : String,
    val price: BigDecimal,
    val quantity: Int
)

data class UpdateProductApiRequest(
    val productId: String? = null,
    val name : String? = null,
    val price: BigDecimal? = null,
    val quantity: Int? = null
)
