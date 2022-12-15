package com.example.training.domain.trainingAppointment.repository

import com.example.training.domain.trainingAppointment.model.TrainingAppointmentEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TrainingAppointmentDao: CrudRepository<TrainingAppointmentEntity, UUID> {

    @Query(
        """
        SELECT trapp FROM TrainingAppointmentEntity trapp
        WHERE trapp.training.id = ?1
        """
    )
    fun findTrainingAppointmentEntityByTraining(trainingId: UUID): List<TrainingAppointmentEntity>

}