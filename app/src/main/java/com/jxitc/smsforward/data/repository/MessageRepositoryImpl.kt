package com.jxitc.smsforward.data.repository

import com.jxitc.smsforward.data.database.MessageDao
import com.jxitc.smsforward.data.model.MessageMapper
import com.jxitc.smsforward.domain.model.Message
import com.jxitc.smsforward.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MessageRepositoryImpl(
    private val messageDao: MessageDao
) : MessageRepository {
    
    override fun getAllMessages(): Flow<List<Message>> {
        return messageDao.getAllMessages().map { entities ->
            MessageMapper.toDomainList(entities)
        }
    }
    
    override suspend fun getMessageById(id: Long): Message? {
        return messageDao.getMessageById(id)?.let { entity ->
            MessageMapper.toDomain(entity)
        }
    }
    
    override suspend fun getUnforwardedMessages(): List<Message> {
        return MessageMapper.toDomainList(messageDao.getUnforwardedMessages())
    }
    
    override fun getRecentMessages(limit: Int): Flow<List<Message>> {
        return messageDao.getRecentMessages(limit).map { entities ->
            MessageMapper.toDomainList(entities)
        }
    }
    
    override suspend fun insertMessage(message: Message): Long {
        return messageDao.insertMessage(MessageMapper.toEntity(message))
    }
    
    override suspend fun insertMessages(messages: List<Message>) {
        val entities = messages.map { MessageMapper.toEntity(it) }
        messageDao.insertMessages(entities)
    }
    
    override suspend fun updateMessage(message: Message) {
        messageDao.updateMessage(MessageMapper.toEntity(message))
    }
    
    override suspend fun updateForwardingStatus(id: Long, isForwarded: Boolean, retryCount: Int) {
        messageDao.updateForwardingStatus(id, isForwarded, retryCount)
    }
    
    override suspend fun deleteMessage(message: Message) {
        messageDao.deleteMessage(MessageMapper.toEntity(message))
    }
    
    override suspend fun deleteOldMessages(beforeTimestamp: Long): Int {
        return messageDao.deleteOldMessages(beforeTimestamp)
    }
    
    override suspend fun deleteAllMessages() {
        messageDao.deleteAllMessages()
    }
}