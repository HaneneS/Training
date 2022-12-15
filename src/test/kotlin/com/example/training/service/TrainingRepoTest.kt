package com.example.training.service

import com.example.training.domain.training.repository.TrainingDao
import com.example.training.domain.training.repository.TrainingRepositoryImpl
import com.example.training.service.request.CreateTrainingRequest
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.stereotype.Component
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.testcontainers.containers.PostgreSQLContainer

@RunWith(SpringRunner::class)
@SpringBootTest(
    classes = [RepositoryTestApp::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
internal class TrainingControllerIntegrationTest {

    @Autowired
    lateinit var trainingDao: TrainingDao

    @Autowired
    lateinit var trainingRepository: TrainingRepositoryImpl

    @Before
    fun setup() {
        trainingDao.deleteAll()
    }

    private val createRequest = CreateTrainingRequest(
        name = "Training A",
        description = "For beginner",
        price = 25.99f,
        professor = "Professor A"
    )

    @Test
    fun findTraining() {
        createTraining(createRequest)
        val result = trainingRepository.findAll().first()

        assertTrue(result.name == createRequest.name)
        assertTrue(result.description == createRequest.description)
        assertTrue(result.price == createRequest.price)
        assertTrue(result.professor == createRequest.professor)
    }

    private fun createTraining(request: CreateTrainingRequest) {
        trainingRepository.create(request)
    }
}

@SpringBootApplication
class RepositoryTestApp {
    init {
        with(PostgreSQLContainer<Nothing>("postgres:13.1-alpine")) {
            withUsername("postgres")
            withPassword("postgres")
            withDatabaseName("test")
            portBindings = listOf("5555:5432")
            start()
        }
    }
}