package com.example.virt3

import javax.persistence.*

@Table(name = "elements")
@Entity
data class Element(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int
)
