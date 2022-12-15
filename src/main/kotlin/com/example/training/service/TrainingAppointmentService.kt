package com.example.training.service

import com.example.training.domain.training.repository.TrainingRepository
import com.example.training.domain.trainingAppointment.model.TrainingAppointment
import com.example.training.domain.trainingAppointment.repository.TrainingAppointmentRepository
import com.example.training.service.request.CreateTrainingAppointmentRequest
import com.example.training.service.request.UpdateTrainingAppointmentRequest
import org.springframework.stereotype.Service
import java.util.*
import javax.xml.bind.ValidationException

interface TrainingAppointmentService {

    fun createTrainingAppointment(request: CreateTrainingAppointmentRequest)
    fun findAllAppointmentForTraining(trainingId: UUID): List<TrainingAppointment>
    fun updateTrainingAppointment(appointmentId: UUID, request: UpdateTrainingAppointmentRequest)
}

@Service
internal class TrainingAppointmentServiceImpl(
    private val appointmentRepository: TrainingAppointmentRepository,
    private val trainingRepository: TrainingRepository,
) : TrainingAppointmentService {

    override fun createTrainingAppointment(request: CreateTrainingAppointmentRequest) {
        takeIf { request.start.isBefore(request.end) }
            ?.let { findTrainingAndCreateAppointment(request) }
            ?: throw ValidationException("The training 'start' can not be after 'end'")
    }

    override fun findAllAppointmentForTraining(trainingId: UUID): List<TrainingAppointment> =
        appointmentRepository.findAllTrainingAppointmentsForTraining(trainingId)

    override fun updateTrainingAppointment(appointmentId: UUID, request: UpdateTrainingAppointmentRequest) {
        appointmentRepository.update(appointmentId, request)
    }

    private fun findTrainingAndCreateAppointment(request: CreateTrainingAppointmentRequest) {
        trainingRepository.findById(request.trainingId)
            ?.let { training -> appointmentRepository.create(request, training) }
            ?: throw Exception("Training with ${request.trainingId} not found")
    }

}

