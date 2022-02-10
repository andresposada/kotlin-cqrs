package com.afp.cqrs.command.api.events

import java.time.LocalDateTime
import java.util.*

abstract class Event(
    val eventName: String,
    val id: UUID = UUID.randomUUID(),
    val created: LocalDateTime = LocalDateTime.now()
)
