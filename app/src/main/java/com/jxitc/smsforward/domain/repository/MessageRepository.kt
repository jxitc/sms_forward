package com.jxitc.smsforward.domain.repository

import com.jxitc.smsforward.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    
    fun getAllMessages(): Flow<List<Message>>
    
    suspend fun getMessageById(id: Long): Message?
    
    suspend fun getUnforwardedMessages(): List<Message>
    
    fun getRecentMessages(limit: Int): Flow<List<Message>>
    
    suspend fun insertMessage(message: Message): Long
    
    suspend fun insertMessages(messages: List<Message>)
    
    suspend fun updateMessage(message: Message)
    
    suspend fun updateForwardingStatus(id: Long, isForwarded: Boolean, retryCount: Int)
    
    suspend fun deleteMessage(message: Message)
    
    suspend fun deleteOldMessages(beforeTimestamp: Long): Int
    
    suspend fun deleteAllMessages()
}