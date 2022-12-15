package com.example.training.domain.training.repository

import com.example.training.domain.training.model.Training
import com.example.training.domain.training.model.TrainingEntity
import com.example.training.domain.training.model.mapToTraining
import com.example.training.domain.training.model.mapToTrainingEntity
import com.example.training.service.request.CreateTrainingRequest
import com.example.training.service.request.UpdateTrainingRequest
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.util.*

internal interface TrainingRepository {

    fun create(request: CreateTrainingRequest)
    fun findAll(): List<Training>
    fun findByName(name: String): Training?
    fun findById(id: UUID): Training?
    fun findAllBetween(from: ZonedDateTime, to: ZonedDateTime): List<Training>
    fun update(training: Training, request: UpdateTrainingRequest)
}

@Component
internal class TrainingRepositoryImpl(private val trainingDao: TrainingDao) : TrainingRepository {

    override fun create(request: CreateTrainingRequest) {
        trainingDao.save(
            TrainingEntity(
                id = null,
                name = request.name,
                description = request.description,
                price = request.price,
                professor = request.professor
            )
        )
    }

    override fun findAll(): List<Training> = trainingDao.findAll().map { it.mapToTraining() }

    override fun findByName(name: String): Training? = trainingDao.findByName(name)?.mapToTraining()

    override fun findById(id: UUID): Training? =
        trainingDao.findById(id)
            .orElse(null)
            ?.mapToTraining()

    override fun findAllBetween(from: ZonedDateTime, to: ZonedDateTime): List<Training> =
        trainingDao.findByTrainingAppointmentsIsBetween(from, to).map { it.mapToTraining() }

    override fun update(training: Training, request: UpdateTrainingRequest) {
        trainingDao.save(
            training.mapToTrainingEntity()
                .apply {
                    if (!request.name.isNullOrBlank()) this.name = requireNotNull(request.name)
                    if (!request.description.isNullOrBlank()) this.description = requireNotNull(request.description)
                    if (request.price != null) this.price = requireNotNull(request.price)
                    if (!request.professor.isNullOrBlank()) this.professor = requireNotNull(request.professor)
                }
        )
    }

}