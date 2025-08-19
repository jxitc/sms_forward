package com.jxitc.smsforward.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    
    @Query("SELECT * FROM messages ORDER BY timestamp DESC")
    fun getAllMessages(): Flow<List<MessageEntity>>
    
    @Query("SELECT * FROM messages WHERE id = :id")
    suspend fun getMessageById(id: Long): MessageEntity?
    
    @Query("SELECT * FROM messages WHERE isForwarded = 0")
    suspend fun getUnforwardedMessages(): List<MessageEntity>
    
    @Query("SELECT * FROM messages ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentMessages(limit: Int): Flow<List<MessageEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<MessageEntity>)
    
    @Update
    suspend fun updateMessage(message: MessageEntity)
    
    @Query("UPDATE messages SET isForwarded = :isForwarded, retryCount = :retryCount WHERE id = :id")
    suspend fun updateForwardingStatus(id: Long, isForwarded: Boolean, retryCount: Int)
    
    @Delete
    suspend fun deleteMessage(message: MessageEntity)
    
    @Query("DELETE FROM messages WHERE timestamp < :beforeTimestamp")
    suspend fun deleteOldMessages(beforeTimestamp: Long): Int
    
    @Query("DELETE FROM messages")
    suspend fun deleteAllMessages()
}