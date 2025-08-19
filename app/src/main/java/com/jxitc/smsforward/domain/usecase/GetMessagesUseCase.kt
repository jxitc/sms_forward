package com.jxitc.smsforward.domain.usecase

import com.jxitc.smsforward.domain.model.Message
import com.jxitc.smsforward.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow

class GetMessagesUseCase(
    private val messageRepository: MessageRepository
) {
    
    operator fun invoke(): Flow<List<Message>> {
        return messageRepository.getAllMessages()
    }
    
    fun getRecentMessages(limit: Int = 50): Flow<List<Message>> {
        return messageRepository.getRecentMessages(limit)
    }
    
    suspend fun getMessageById(id: Long): Message? {
        return messageRepository.getMessageById(id)
    }
    
    suspend fun getUnforwardedMessages(): List<Message> {
        return messageRepository.getUnforwardedMessages()
    }
}