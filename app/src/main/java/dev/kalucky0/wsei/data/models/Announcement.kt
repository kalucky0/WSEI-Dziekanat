package dev.kalucky0.wsei.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Announcement(
    @PrimaryKey val uid: Int,
    val title: String,
    val priority: String,
    val date: String,
    val isRead: Boolean
)