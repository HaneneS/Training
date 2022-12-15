package com.example.training.domain.customer.repository

import com.example.training.domain.customer.model.CustomerEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomerDao: CrudRepository<CustomerEntity, UUID>