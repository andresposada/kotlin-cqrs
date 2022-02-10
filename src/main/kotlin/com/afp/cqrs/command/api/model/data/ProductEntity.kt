package com.afp.cqrs.command.api.model.data

import com.afp.cqrs.common.model.Product
import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.Id

@Entity
open class ProductEntity(
    @Id
    open var productId: String,
    open var name : String,
    open var price: BigDecimal,
    open var quantity: Int
) {

    fun toDomain(): Product =
        Product(
            id = this.productId,
            name = this.name,
            price = this.price,
            quantity = this.quantity
        )

}
