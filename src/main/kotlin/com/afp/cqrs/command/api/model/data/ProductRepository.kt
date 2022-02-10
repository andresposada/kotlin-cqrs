package com.afp.cqrs.command.api.model.data

import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository: JpaRepository<ProductEntity, String> {

}