package com.example.training.service

import com.example.training.domain.training.model.Training
import com.example.training.domain.training.repository.TrainingRepository
import com.example.training.service.request.CreateTrainingRequest
import com.example.training.service.request.UpdateTrainingRequest
import org.springframework.stereotype.Service
import java.time.ZonedDateTime
import java.util.*
import javax.xml.bind.ValidationException

interface TrainingService {

    fun createTraining(request: CreateTrainingRequest)
    fun findAll(dateTimeFrom: ZonedDateTime? = null, dateTimeTo: ZonedDateTime? = null): List<Training>
    fun updateTraining(trainingId: UUID, request: UpdateTrainingRequest)
}

@Service
internal class TrainingServiceImpl(private val trainingRepository: TrainingRepository) : TrainingService {

    override fun createTraining(request: CreateTrainingRequest) {

        takeIf { trainingRepository.findByName(request.name) == null }
            ?.let { trainingRepository.create(request) }
            ?: throw Exception("Training already exists for the given name ${request.name}")

    }

    override fun findAll(dateTimeFrom: ZonedDateTime?, dateTimeTo: ZonedDateTime?): List<Training> = when {
        dateTimeFrom != null && dateTimeTo != null -> validateDateTimeAndFindAllBetween(dateTimeFrom, dateTimeTo)
        else -> trainingRepository.findAll()
    }

    override fun updateTraining(trainingId: UUID, request: UpdateTrainingRequest) {
        trainingRepository.findById(trainingId)
            ?.let { trainingRepository.update(it, request) }
            ?: throw Exception("Training with $trainingId not found")
    }

    private fun validateDateTimeAndFindAllBetween(
        dateTimeFrom: ZonedDateTime,
        dateTimeTo: ZonedDateTime
    ): List<Training> =
        when (dateTimeFrom.isBefore(dateTimeTo)) {
            true -> trainingRepository.findAllBetween(dateTimeFrom, dateTimeTo)
            else -> throw ValidationException("'dateTimeFrom' can not be after 'dateTimeTo'")
        }

}


