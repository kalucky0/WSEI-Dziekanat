package dev.kalucky0.wsei.data.models

import kotlinx.datetime.LocalDate

data class Student(
    val name: String,
    val surname: String,
    val secondName: String,
    val pesel: String,
    val birthdate: LocalDate,
    val sex: String,
    val id: String,
    val email: String,
    val street: String,
    val postCode: String,
    val town: String,
    val post: String
)