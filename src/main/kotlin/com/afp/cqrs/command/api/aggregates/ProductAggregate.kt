package com.afp.cqrs.command.api.aggregates

import com.afp.cqrs.command.api.commands.CreateProductCommand
import com.afp.cqrs.command.api.commands.UpdateProductCommand
import com.afp.cqrs.command.api.events.ProductCreatedEvent
import com.afp.cqrs.command.api.events.ProductUpdatedEvent
import com.afp.cqrs.common.exceptions.CommandValidationException
import org.apache.commons.lang3.StringUtils
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.messaging.interceptors.ExceptionHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle.apply
import org.axonframework.spring.stereotype.Aggregate
import java.math.BigDecimal
import kotlin.properties.Delegates

@Aggregate
class ProductAggregate {

    @AggregateIdentifier
    private lateinit var productId: String
    private lateinit var name: String
    private lateinit var price: BigDecimal
    private var quantity by Delegates.notNull<Int>()

    @CommandHandler
    constructor(createProductCommand: CreateProductCommand) {
        validateCreateCommand(createProductCommand)
        apply(
            ProductCreatedEvent(
                productId = createProductCommand.productId,
                name = createProductCommand.name,
                price = createProductCommand.price,
                quantity = createProductCommand.quantity
            )
        )
    }

    private fun validateCreateCommand(createProductCommand: CreateProductCommand) {
        if (createProductCommand.price <= BigDecimal.ZERO) {
            throw CommandValidationException("The price must be a positive integer")
        } else if (createProductCommand.quantity <= 0) {
            throw CommandValidationException("The quantity must be greater than 0")
        }
    }

    @CommandHandler
    constructor(updateProductCommand: UpdateProductCommand) {
        validateUpdateCommand(updateProductCommand)
        apply(
            ProductUpdatedEvent(
                productId = updateProductCommand.productId,
                name = updateProductCommand.name,
                price = updateProductCommand.price,
                quantity = updateProductCommand.quantity
            )
        )
    }

    private fun validateUpdateCommand(updateProductCommand: UpdateProductCommand) {
        if (updateProductCommand.name!= null && StringUtils.isBlank(updateProductCommand.name)) {
            throw CommandValidationException("The new name must be not blank")
        } else if (updateProductCommand.price != null && updateProductCommand.price <= BigDecimal.ZERO) {
            throw CommandValidationException("The price must be a positive integer")
        } else if (updateProductCommand.quantity != null && updateProductCommand.quantity <= 0) {
            throw CommandValidationException("The quantity must be greater than 0")
        }
    }

    @EventSourcingHandler
    fun on(productCreatedEvent: ProductCreatedEvent) = this.apply {
        productId = productCreatedEvent.productId
        name = productCreatedEvent.name
        quantity = productCreatedEvent.quantity
        price = productCreatedEvent.price
    }

    @EventSourcingHandler
    fun on(productUpdatedEvent: ProductUpdatedEvent) {
        this.apply {
            productId = productUpdatedEvent.productId
            name = productUpdatedEvent.name ?: ""
            quantity = productUpdatedEvent.quantity ?: 0
            price = productUpdatedEvent.price ?: BigDecimal.ZERO
        }
    }

    @ExceptionHandler
    fun handleAll(exception: Exception) {
        throw exception
    }
}

