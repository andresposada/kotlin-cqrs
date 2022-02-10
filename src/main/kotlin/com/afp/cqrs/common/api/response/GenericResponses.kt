package com.afp.cqrs.common.api.response

data class ErrorResponse(
    val message : String,
    val statusCode: Int
)

data class CommandApiResponse(
    val identifier: String,
    val message: String
)