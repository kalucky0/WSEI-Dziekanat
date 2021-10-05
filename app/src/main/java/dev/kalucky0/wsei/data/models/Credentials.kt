package dev.kalucky0.wsei.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Credentials(
    @PrimaryKey val uid: Int,
    val login: String,
    val password: String
)