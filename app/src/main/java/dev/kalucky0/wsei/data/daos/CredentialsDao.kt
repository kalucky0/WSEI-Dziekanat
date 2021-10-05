package dev.kalucky0.wsei.data.daos

import androidx.room.*
import dev.kalucky0.wsei.data.models.Credentials
import dev.kalucky0.wsei.data.models.Student

@Dao
interface CredentialsDao {
    @Query("SELECT * FROM credentials")
    fun getAll(): List<Credentials>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: Credentials)

    @Delete
    fun delete(user: Credentials)
}