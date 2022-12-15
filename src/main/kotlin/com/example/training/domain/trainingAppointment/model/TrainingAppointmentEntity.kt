package com.example.training.domain.trainingAppointment.model

import com.example.training.domain.customer.model.CustomerEntity
import com.example.training.domain.training.model.TrainingEntity
import java.time.ZonedDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "appointment")
data class TrainingAppointmentEntity(

    @Id @GeneratedValue
    @Column
    val id: UUID?,

    @Column(name = "start_date_time", nullable = false)
    var start: ZonedDateTime,

    @Column(name = "end_date_time", nullable = false)
    var end: ZonedDateTime,

){
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_id")
    var training: TrainingEntity? = null

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "appointments")
    var customers: MutableSet<CustomerEntity> = mutableSetOf()

}
