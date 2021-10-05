package dev.kalucky0.wsei.data.daos

import androidx.room.*
import dev.kalucky0.wsei.data.models.Schedule
import kotlinx.datetime.LocalDate

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM schedule")
    fun getAll(): List<Schedule>

    @Query("SELECT day from schedule GROUP BY day")
    fun getAllDates(): List<LocalDate>

    @Query("SELECT * from schedule WHERE day = :day")
    fun getDay(day: String): List<Schedule>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: Schedule)

    @Delete
    fun delete(user: Schedule)

}