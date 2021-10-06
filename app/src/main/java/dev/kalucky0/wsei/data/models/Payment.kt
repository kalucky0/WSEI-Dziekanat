package dev.kalucky0.wsei.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import dev.kalucky0.wsei.data.converters.DateConverter
import kotlinx.datetime.LocalDate

@Entity
data class Payment(
    @PrimaryKey(autoGenerate = true) val uid: Int?,
    val name: String,
    @TypeConverters(DateConverter::class) val due: LocalDate,
     val state: String,
     val amountNow: Int,
     val amount: Int,
    @TypeConverters(DateConverter::class) val paymentDate: LocalDate,
    val additionalInfo: String,
)