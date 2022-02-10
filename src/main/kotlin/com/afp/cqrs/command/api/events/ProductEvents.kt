package com.afp.cqrs.command.api.events

import java.math.BigDecimal

data class ProductCreatedEvent(
    val productId: String,
    val name : String,
    val price: BigDecimal,
    val quantity: Int
): Event(eventName = "ProductCreatedEvent")

data class ProductUpdatedEvent(
    val productId: String,
    val name : String?,
    val price: BigDecimal?,
    val quantity: Int?
): Event(eventName = "ProductUpdatedEvent")
