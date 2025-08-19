package com.jxitc.smsforward.domain.model

data class Message(
    val id: Long = 0,
    val type: MessageType,
    val sender: String,
    val content: String,
    val timestamp: Long,
    val isForwarded: Boolean = false,
    val retryCount: Int = 0
)

enum class MessageType {
    SMS,
    NOTIFICATION
}