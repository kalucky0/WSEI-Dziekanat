package dev.kalucky0.wsei.data.daos

import androidx.room.*
import dev.kalucky0.wsei.data.models.Student

@Dao
interface StudentDao {
    @Query("SELECT * FROM student")
    fun getAll(): List<Student>

    @Query("SELECT * FROM student WHERE uid = 0")
    fun getStudent(): Student

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: Student)

    @Delete
    fun delete(user: Student)
}