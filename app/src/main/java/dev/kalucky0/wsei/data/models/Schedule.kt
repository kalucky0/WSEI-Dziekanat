package dev.kalucky0.wsei.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import dev.kalucky0.wsei.data.converters.DateConverter
import kotlinx.datetime.LocalDate

@Entity
data class Schedule(
    @PrimaryKey val uid: Int,
    @TypeConverters(DateConverter::class) val day: LocalDate,
    val timeFrom: Float,
    val timeTo: Float,
    val subject: String,
    val instructor: String,
    val type: String,
    val location: String
)
