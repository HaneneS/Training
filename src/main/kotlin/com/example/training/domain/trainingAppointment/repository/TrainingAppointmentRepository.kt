package com.example.training.domain.trainingAppointment.repository

import com.example.training.domain.training.model.Training
import com.example.training.domain.training.model.mapToTrainingEntity
import com.example.training.domain.trainingAppointment.model.TrainingAppointment
import com.example.training.domain.trainingAppointment.model.TrainingAppointmentEntity
import com.example.training.domain.trainingAppointment.model.mapToTrainingAppointment
import com.example.training.service.request.CreateTrainingAppointmentRequest
import com.example.training.service.request.UpdateTrainingAppointmentRequest
import org.springframework.stereotype.Component
import java.util.*

internal interface TrainingAppointmentRepository {

    fun create(request: CreateTrainingAppointmentRequest, training: Training)
    fun findAllTrainingAppointmentsForTraining(trainingId: UUID): List<TrainingAppointment>
    fun findById(appointmentId: UUID): TrainingAppointment?
    fun update(appointmentId: UUID, request: UpdateTrainingAppointmentRequest)
}

@Component
internal class TrainingAppointmentRepositoryImpl(
    private val trainingAppointmentDao: TrainingAppointmentDao
) : TrainingAppointmentRepository {

    override fun create(request: CreateTrainingAppointmentRequest, training: Training) {
        trainingAppointmentDao.save(
            TrainingAppointmentEntity(
                id = null,
                start = request.start,
                end = request.end,
            ).apply {
                this.training = training.mapToTrainingEntity()
            }
        )
    }

    override fun findAllTrainingAppointmentsForTraining(trainingId: UUID): List<TrainingAppointment> =
        trainingAppointmentDao.findTrainingAppointmentEntityByTraining(trainingId)
            .map { it.mapToTrainingAppointment() }

    override fun findById(appointmentId: UUID): TrainingAppointment? =
        trainingAppointmentDao.findById(appointmentId).orElse(null)
            ?.mapToTrainingAppointment()

    override fun update(appointmentId: UUID, request: UpdateTrainingAppointmentRequest) {
        trainingAppointmentDao.findById(appointmentId).orElse(null)
            ?.let { appointment -> save(appointment, request) }
            ?: throw Exception("Training with $appointmentId not found")

    }

    private fun save(
        appointment: TrainingAppointmentEntity,
        request: UpdateTrainingAppointmentRequest
    ) = trainingAppointmentDao.save(
        appointment.apply {
            if (request.start != null) this.start = requireNotNull(request.start)
            if (request.end != null) this.end = requireNotNull(request.end)
        }
    )
}