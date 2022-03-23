package dev.kalucky0.wsei.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.kalucky0.wsei.data.converters.DateConverter
import dev.kalucky0.wsei.data.daos.*
import dev.kalucky0.wsei.data.models.*

@Database(
    entities = [Credentials::class, Student::class, Schedule::class, Payment::class, Announcement::class],
    version = 3
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
    abstract fun scheduleDao(): ScheduleDao
    abstract fun credentialsDao(): CredentialsDao
    abstract fun paymentDao(): PaymentDao
    abstract fun announcementDao(): AnnouncementDao
}
