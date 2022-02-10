package com.afp.cqrs.common.exceptions

import java.lang.Exception

class CommandValidationException: Exception {
    constructor(): super()
    constructor(message: String?) : super(message)
}

class ObjectNotFoundException: Exception {
    constructor(): super()
    constructor(message: String?) : super(message)
}