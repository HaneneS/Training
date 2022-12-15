package com.example.training.domain.trainingAppointment.model

import java.time.ZonedDateTime
import java.util.*

data class TrainingAppointment(
    val id: UUID,
    val start: ZonedDateTime,
    val end: ZonedDateTime,
)


fun TrainingAppointmentEntity.mapToTrainingAppointment() = TrainingAppointment(
    id = requireNotNull(id),
    start = start,
    end = end
)