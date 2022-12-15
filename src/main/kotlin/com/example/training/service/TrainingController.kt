package com.example.training.service

import com.example.training.domain.training.model.Training
import com.example.training.service.request.CreateTrainingRequest
import com.example.training.service.request.UpdateTrainingRequest
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.ZonedDateTime
import java.util.*

const val DATE_TIME = "yyyy-MM-dd'T'HH:mm:ssXXX"

@RestController
@RequestMapping
class TrainingController(val trainingService: TrainingService) {

    @PostMapping
    fun createTraining(@RequestBody request: CreateTrainingRequest) {
        trainingService.createTraining(request)
    }

    @GetMapping
    fun getAllTraining(
        @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME) dateTimeFrom: ZonedDateTime?,
        @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME) dateTimeTo: ZonedDateTime?,
    ): List<Training> {
        return trainingService.findAll(dateTimeFrom, dateTimeTo)
    }

    @PutMapping(path = ["{trainingId}"])
    fun updateTraining(
        @PathVariable trainingId: UUID,
        @RequestBody request: UpdateTrainingRequest
    ): ResponseEntity<Unit> {
        trainingService.updateTraining(trainingId, request)
        return ResponseEntity.noContent().build()
    }
}
