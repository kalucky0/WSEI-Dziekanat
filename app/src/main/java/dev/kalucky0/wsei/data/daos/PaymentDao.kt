package dev.kalucky0.wsei.data.daos

import androidx.room.*
import dev.kalucky0.wsei.data.models.Payment

@Dao
interface PaymentDao {
    @Query("SELECT * FROM payment ORDER BY due")
    fun getAll(): List<Payment>

    @Insert()
    fun insertAll(vararg users: Payment)

    @Delete
    fun delete(user: Payment)
}