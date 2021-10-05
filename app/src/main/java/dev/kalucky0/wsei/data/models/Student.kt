package dev.kalucky0.wsei.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import dev.kalucky0.wsei.data.converters.DateConverter
import kotlinx.datetime.LocalDate

@Entity
data class Student(
    @PrimaryKey val uid: Int,
    val name: String,
    val surname: String,
    val secondName: String,
    val pesel: String,
    @TypeConverters(DateConverter::class) val birthdate: LocalDate,
    val sex: String,
    val id: String,
    val email: String,
    val street: String,
    val postCode: String,
    val town: String,
    val post: String
)