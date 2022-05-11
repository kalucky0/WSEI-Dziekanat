package dev.kalucky0.wsei.data.daos

import androidx.room.*
import dev.kalucky0.wsei.data.models.Announcement

@Dao
interface AnnouncementDao {
    @Query("SELECT * FROM announcement")
    fun getAll(): List<Announcement>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg announcements: Announcement)

    @Delete
    fun delete(announcement: Announcement)
}