package com.afp.cqrs.command.api.controller

import com.afp.cqrs.command.api.commands.CreateProductCommand
import com.afp.cqrs.command.api.commands.UpdateProductCommand
import com.afp.cqrs.command.api.model.request.AddProductApiRequest
import com.afp.cqrs.command.api.model.request.UpdateProductApiRequest
import com.afp.cqrs.common.api.response.CommandApiResponse
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/products")
class ProductCommandController(
    val commandGateway: CommandGateway
) {

    @PostMapping
    fun addProduct(
        @RequestBody productRequest: AddProductApiRequest
    ) : ResponseEntity<CommandApiResponse> {
        val result = commandGateway.sendAndWait<String>(productRequest.toCommand())
        return ResponseEntity.ok(CommandApiResponse(result, "Product created!!"))
    }

    @PutMapping("{id}")
    fun updateProduct(
        @PathVariable(value = "id") productId: String,
        @RequestBody updateProductApiRequest: UpdateProductApiRequest
    ):ResponseEntity<CommandApiResponse> {
        val result = commandGateway.sendAndWait<String>(updateProductApiRequest.toCommand(productId))
        return ResponseEntity.ok(CommandApiResponse(result, "Product updated!!"))
    }

    fun AddProductApiRequest.toCommand() =
        CreateProductCommand(
            name = this.name,
            price = this.price,
            quantity = this.quantity,
            productId = UUID.randomUUID().toString()
        )

    fun UpdateProductApiRequest.toCommand(productId: String) =
        UpdateProductCommand(
            name = this.name,
            productId = productId,
            price = this.price,
            quantity = this.quantity
        )
}