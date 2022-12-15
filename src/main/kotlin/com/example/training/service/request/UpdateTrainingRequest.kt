package com.example.training.service.request

data class UpdateTrainingRequest(
    val name: String? = null,
    val description: String? = null,
    val price: Float? = null,
    val professor: String? = null,
)
