package com.jxitc.smsforward.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: String,
    val sender: String,
    val content: String,
    val timestamp: Long,
    val isForwarded: Boolean = false,
    val retryCount: Int = 0
)