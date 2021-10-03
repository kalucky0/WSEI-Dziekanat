package dev.kalucky0.wsei.data.models

data class Activity(
    val timeFrom: Float,
    val timeTo: Float,
    val subject: String,
    val instructor: String,
    val type: String,
    val location: String
)
