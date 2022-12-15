package com.example.training.domain.training.model

import java.util.*

data class Training(
    val id: UUID,
    val name: String,
    val description: String,
    val price: Float,
    val professor: String,
)


fun TrainingEntity.mapToTraining() = Training(
    id = requireNotNull(id),
    name = name,
    description = description,
    price = price,
    professor = professor
)

fun Training.mapToTrainingEntity() = TrainingEntity(
    id = id,
    name = name,
    description = description,
    price = price,
    professor = professor
)