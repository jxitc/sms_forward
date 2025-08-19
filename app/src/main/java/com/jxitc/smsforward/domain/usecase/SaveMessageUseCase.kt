package com.jxitc.smsforward.domain.usecase

import com.jxitc.smsforward.domain.model.Message
import com.jxitc.smsforward.domain.repository.MessageRepository

class SaveMessageUseCase(
    private val messageRepository: MessageRepository
) {
    
    suspend operator fun invoke(message: Message): Result<Long> {
        return try {
            val messageId = messageRepository.insertMessage(message)
            Result.success(messageId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun saveMessages(messages: List<Message>): Result<Unit> {
        return try {
            messageRepository.insertMessages(messages)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}