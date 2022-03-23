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
    val secondName: String,
    val surname: String,
    val course: String,
    val index: String,
    val pesel: String,
    @TypeConverters(DateConverter::class) val birthdate: LocalDate,
    val birtPlace: String,
    val nip: String,
    val sex: String,
    val martialStatus: String,
    val nationality: String,
    val citizenship: String,
    val id: String,
    val idIssuedBy: String,
    val passport: String,
    val militaryId: String,
    val motherName: String,
    val motherSurname: String,
    val fatherName: String,
    val fatherSurname: String,
    val accountNumber: String,
    val bankName: String,
    val street: String,
    val postCode: String,
    val town: String,
    val post: String,
    val mailStreet: String,
    val mailPostCode: String,
    val mailTown: String,
    val mailPost: String,
    val personalEmail: String,
    val email: String,
    val landline: String,
    val mobile: String,
    val additional: String,
    val maturaId: String,
    @TypeConverters(DateConverter::class) val maturaDate: LocalDate,
    val graduationHighSchool: Int,
    val highSchool: String,
    val maturaType: String,
    val diplomaNumber: String,
    @TypeConverters(DateConverter::class) val diplomaIssueDate: LocalDate,
    @TypeConverters(DateConverter::class) val graduationUni: LocalDate,
    val university: String,
    val faculty: String,
    val specialization: String,
    val otherUniversity: String,
    val workExperience: String,
)