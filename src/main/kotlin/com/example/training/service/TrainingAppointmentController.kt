package com.example.training.service

import com.example.training.domain.trainingAppointment.model.TrainingAppointment
import com.example.training.service.request.CreateTrainingAppointmentRequest
import com.example.training.service.request.UpdateTrainingAppointmentRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(path = ["appointment"])
class TrainingAppointmentController(val trainingAppointmentService: TrainingAppointmentService) {

    @PostMapping
    fun createAppointmentForTraining(@RequestBody request: CreateTrainingAppointmentRequest) {
        trainingAppointmentService.createTrainingAppointment(request)
    }

    @GetMapping(path = ["{trainingId}"])
    fun getAllAppointmentsOfTraining(@PathVariable trainingId: UUID): ResponseEntity<List<TrainingAppointment>> =
        trainingAppointmentService.findAllAppointmentForTraining(trainingId).let { response ->
            ResponseEntity.ok(response)
        }

    @PutMapping(path = ["{appointmentId}"])
    fun updateAppointmentOfTraining(
        @PathVariable appointmentId: UUID,
        @RequestBody request: UpdateTrainingAppointmentRequest
    ): ResponseEntity<Unit> {
        trainingAppointmentService.updateTrainingAppointment(appointmentId, request)
        return ResponseEntity.noContent().build()
    }
}