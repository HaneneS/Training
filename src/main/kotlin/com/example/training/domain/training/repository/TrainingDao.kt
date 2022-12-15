package com.example.training.domain.training.repository

import com.example.training.domain.training.model.TrainingEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.ZonedDateTime
import java.util.*

@Repository
interface TrainingDao : CrudRepository<TrainingEntity, UUID> {

    @Query(
        """
        SELECT tr FROM TrainingEntity tr
        JOIN FETCH tr.trainingAppointments trapp
        WHERE trapp.start > ?1
        AND trapp.end < ?2
        """
    )
    fun findByTrainingAppointmentsIsBetween(from: ZonedDateTime, to: ZonedDateTime): List<TrainingEntity>

    fun findByName(name: String): TrainingEntity?
}