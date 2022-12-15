package com.example.training.service.request

data class CreateTrainingRequest(
    val name: String,
    val description: String,
    val price: Float,
    val professor: String
)
