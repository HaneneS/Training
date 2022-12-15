package com.example.training.service.request

import java.time.ZonedDateTime
import java.util.*

data class UpdateTrainingAppointmentRequest(
    val start: ZonedDateTime? = null,
    val end: ZonedDateTime? = null,
)
