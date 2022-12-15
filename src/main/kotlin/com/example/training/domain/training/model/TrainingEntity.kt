package com.example.training.domain.training.model

import com.example.training.domain.trainingAppointment.model.TrainingAppointmentEntity
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "training")
data class TrainingEntity (

    @Id @GeneratedValue
    @Column
    val id: UUID?,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "description", nullable = false)
    var description: String,

    @Column(name = "price", nullable = false)
    var price: Float,

    @Column(name = "professor", nullable = false)
    var professor: String,
){
    @OneToMany(mappedBy = "training", cascade = [CascadeType.ALL])
    var trainingAppointments: MutableList<TrainingAppointmentEntity> = mutableListOf()
}