package com.afp.cqrs.common.controller

import com.afp.cqrs.common.api.response.ErrorResponse
import mu.KotlinLogging
import org.apache.commons.lang3.exception.ExceptionUtils
import org.axonframework.commandhandling.CommandExecutionException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CQRSControllerAdvice {

    private val logger = KotlinLogging.logger {}

    @ExceptionHandler( Exception::class )
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    fun handle(exception: Exception): ErrorResponse = buildResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR)

    @ExceptionHandler( CommandExecutionException::class )
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun handleCommandValidationException(commandExecutionException: CommandExecutionException): ErrorResponse =
        buildResponse(commandExecutionException, HttpStatus.BAD_REQUEST)


    private fun buildResponse(exception: Exception, httpStatus: HttpStatus) : ErrorResponse {
        logger.error { "Exception thrown in the service, error: ${ExceptionUtils.getStackTrace(exception)}" }
        return ErrorResponse(
            message = exception.message!!,
            statusCode = httpStatus.value()
        )
    }

}