package dev.kalucky0.wsei.data.converters

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate

class DateConverter {
    @TypeConverter
    fun toDate(timestamp: String?): LocalDate? {
        return timestamp?.let {  LocalDate.parse(timestamp) }
    }

    @TypeConverter
    fun toTimestamp(date: LocalDate?): String? {
        return date?.toString()
    }
}