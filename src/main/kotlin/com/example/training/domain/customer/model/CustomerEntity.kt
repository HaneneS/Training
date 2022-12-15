package com.example.training.domain.customer.model

import com.example.training.domain.trainingAppointment.model.TrainingAppointmentEntity
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "customer")
data class CustomerEntity(
    @Id @GeneratedValue
    @Column
    val id: UUID?,

    @Column(name = "first_name", nullable = false)
    var firstName: String,

    @Column(name = "last_name", nullable = false)
    var lastName: String,

    @Column(name = "birthdate", nullable = false)
    var birthdate: LocalDate,

    @Column(name = "created_at", nullable = false)
    val createdAt: ZonedDateTime,
){
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name="customer__appointment",
        joinColumns= [JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)],
        inverseJoinColumns=[JoinColumn(name = "appointment_id", referencedColumnName = "id", nullable = false)]
    )
    var appointments: MutableSet<TrainingAppointmentEntity> = mutableSetOf()
}
