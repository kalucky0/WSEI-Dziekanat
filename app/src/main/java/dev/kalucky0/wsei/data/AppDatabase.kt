package dev.kalucky0.wsei.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.kalucky0.wsei.data.converters.DateConverter
import dev.kalucky0.wsei.data.daos.CredentialsDao
import dev.kalucky0.wsei.data.daos.PaymentDao
import dev.kalucky0.wsei.data.daos.ScheduleDao
import dev.kalucky0.wsei.data.daos.StudentDao
import dev.kalucky0.wsei.data.models.Credentials
import dev.kalucky0.wsei.data.models.Payment
import dev.kalucky0.wsei.data.models.Schedule
import dev.kalucky0.wsei.data.models.Student

@Database(entities = [Credentials::class, Student::class, Schedule::class, Payment::class], version = 3)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
    abstract fun scheduleDao(): ScheduleDao
    abstract fun credentialsDao(): CredentialsDao
    abstract fun paymentDao(): PaymentDao
}
