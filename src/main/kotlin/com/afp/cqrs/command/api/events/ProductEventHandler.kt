package com.afp.cqrs.command.api.events

import com.afp.cqrs.command.api.model.data.ProductEntity
import com.afp.cqrs.command.api.model.data.ProductRepository
import com.afp.cqrs.common.exceptions.ObjectNotFoundException
import org.axonframework.eventhandling.EventHandler
import org.axonframework.messaging.interceptors.ExceptionHandler
import org.springframework.stereotype.Component

@Component
class ProductEventHandler(
    val productRepository: ProductRepository
) {
    @EventHandler
    fun on(productCreatedEvent: ProductCreatedEvent) {
        with(productCreatedEvent) {
            productRepository.save(
                ProductEntity(
                productId = this.productId,
                name = this.name,
                price = this.price,
                quantity = this.quantity
            )
            )
        }
    }

    @EventHandler
    fun on(productUpdatedEvent: ProductUpdatedEvent) {
        val productEntity = productRepository.findById(productUpdatedEvent.productId).orElseThrow {
            ObjectNotFoundException("Product was not found with id ${productUpdatedEvent.productId}")
        }
        with(productUpdatedEvent) {
            productEntity.name = this.name ?: productEntity.name
            productEntity.price = this.price ?: productEntity.price
            productEntity.quantity = this.quantity ?: productEntity.quantity
        }
        productRepository.save(productEntity)
    }

    @ExceptionHandler
    fun handleAll(exception: Exception) {
        throw exception
    }
}