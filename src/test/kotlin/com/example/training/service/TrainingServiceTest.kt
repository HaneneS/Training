package com.example.training.service

import com.example.training.domain.training.model.Training
import com.example.training.domain.training.repository.TrainingRepository
import com.example.training.service.request.CreateTrainingRequest
import com.example.training.service.request.UpdateTrainingRequest
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.mockito.kotlin.*
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import javax.xml.bind.ValidationException

class TrainingServiceTest {

    private val trainingRepository = mock<TrainingRepository>()
    private val service = TrainingServiceImpl(trainingRepository)

    private val createRequest = CreateTrainingRequest(
        name = "Training A",
        description = "For beginner",
        price = 25.99f,
        professor = "Professor A"
    )

    private val updateRequest = UpdateTrainingRequest(
        price = 29.99f
    )

    private val training = Training(
        id = UUID.randomUUID(),
        name = "Training A",
        description = "For beginner",
        price = 25.99f,
        professor = "Professor A"
    )

    @Test
    fun createTraining() {

        whenever(trainingRepository.findByName(any())).thenReturn(null)

        service.createTraining(createRequest)

        verify(trainingRepository).create(any())
    }

    @Test(expected = Exception::class)
    fun `try to create an existing Training `() {

        whenever(trainingRepository.findByName(any())).thenReturn(training)

        service.createTraining(createRequest)

        verify(trainingRepository, never()).create(any())
    }

    @Test
    fun findAllTraining() {

        whenever(trainingRepository.findAll()).thenReturn(listOf(training))

        val result = service.findAll()

        assertTrue(result.size == 1)
        verify(trainingRepository).findAll()
    }

    @Test
    fun findAllTrainingBetween() {

        val from = ZonedDateTime.now(ZoneId.of("UTC"))
        val to = from.plusMonths(1)

        whenever(trainingRepository.findAllBetween(from, to)).thenReturn(listOf(training))

        val result = service.findAll(from, to)

        assertTrue(result.size == 1)
        verify(trainingRepository).findAllBetween(from, to)
    }

    @Test(expected = ValidationException::class)
    fun `findAllTrainingBetween wrong datetime`() {

        val from = ZonedDateTime.now(ZoneId.of("UTC"))
        val to = from.minusMonths(1)

        whenever(trainingRepository.findAllBetween(from, to)).thenReturn(listOf(training))

        service.findAll(from, to)

        verify(trainingRepository, never()).findAllBetween(from, to)
    }

    @Test
    fun updateTraining() {

        whenever(trainingRepository.findById(any())).thenReturn(training)

        service.updateTraining(training.id, updateRequest)

        verify(trainingRepository).findById(training.id)
        verify(trainingRepository).update(training, updateRequest)
    }

    @Test(expected = Exception::class)
    fun `try to update a missing Training `() {

        whenever(trainingRepository.findById(any())).thenReturn(null)

        service.updateTraining(training.id, updateRequest)

        verify(trainingRepository).findById(training.id)
        verify(trainingRepository, never()).update(training, updateRequest)
    }
}