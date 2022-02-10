package com.afp.cqrs.common.model

import java.math.BigDecimal

data class Product(
    val id: String,
    val name : String,
    val price: BigDecimal,
    val quantity: Int
)
