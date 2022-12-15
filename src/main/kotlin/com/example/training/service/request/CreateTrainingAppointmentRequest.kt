package com.example.training.service.request

import java.time.ZonedDateTime
import java.util.*

data class CreateTrainingAppointmentRequest(
    val trainingId: UUID,
    val start: ZonedDateTime,
    val end: ZonedDateTime,
)
