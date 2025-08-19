package com.jxitc.smsforward.domain.usecase

import android.util.Log
import com.jxitc.smsforward.domain.model.Message
import com.jxitc.smsforward.domain.model.MessageType

class MessageProcessorUseCase(
    private val saveMessageUseCase: SaveMessageUseCase,
    private val messageValidationUseCase: MessageValidationUseCase
) {
    
    companion object {
        private const val TAG = "MessageProcessor"
    }
    
    suspend fun processIncomingSms(
        sender: String,
        content: String,
        timestamp: Long
    ): ProcessingResult {
        
        Log.d(TAG, "Processing SMS from $sender: ${content.take(50)}...")
        
        // Create message object
        val rawMessage = Message(
            type = MessageType.SMS,
            sender = sender,
            content = content,
            timestamp = timestamp
        )
        
        // Sanitize message
        val sanitizedMessage = messageValidationUseCase.sanitizeMessage(rawMessage)
        
        // Validate message
        val validationResult = messageValidationUseCase.validateMessage(sanitizedMessage)
        if (!validationResult.isValid) {
            val errors = (validationResult as ValidationResult.Invalid).errors
            Log.w(TAG, "Message validation failed: ${errors.joinToString()}")
            return ProcessingResult.ValidationFailed(errors)
        }
        
        // Save to database
        return try {
            val saveResult = saveMessageUseCase.invoke(sanitizedMessage)
            if (saveResult.isSuccess) {
                val messageId = saveResult.getOrThrow()
                Log.d(TAG, "SMS successfully saved with ID: $messageId")
                ProcessingResult.Success(messageId, sanitizedMessage)
            } else {
                val error = saveResult.exceptionOrNull()!!
                Log.e(TAG, "Failed to save SMS", error)
                ProcessingResult.SaveFailed(error)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error processing SMS", e)
            ProcessingResult.SaveFailed(e)
        }
    }
    
    suspend fun processMessage(message: Message): ProcessingResult {
        Log.d(TAG, "Processing ${message.type} message from ${message.sender}")
        
        // Sanitize message
        val sanitizedMessage = messageValidationUseCase.sanitizeMessage(message)
        
        // Validate message
        val validationResult = messageValidationUseCase.validateMessage(sanitizedMessage)
        if (!validationResult.isValid) {
            val errors = (validationResult as ValidationResult.Invalid).errors
            Log.w(TAG, "Message validation failed: ${errors.joinToString()}")
            return ProcessingResult.ValidationFailed(errors)
        }
        
        // Save to database
        return try {
            val saveResult = saveMessageUseCase.invoke(sanitizedMessage)
            if (saveResult.isSuccess) {
                val messageId = saveResult.getOrThrow()
                Log.d(TAG, "Message successfully saved with ID: $messageId")
                ProcessingResult.Success(messageId, sanitizedMessage)
            } else {
                val error = saveResult.exceptionOrNull()!!
                Log.e(TAG, "Failed to save message", error)
                ProcessingResult.SaveFailed(error)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error processing message", e)
            ProcessingResult.SaveFailed(e)
        }
    }
}

sealed class ProcessingResult {
    data class Success(val messageId: Long, val message: Message) : ProcessingResult()
    data class ValidationFailed(val errors: List<String>) : ProcessingResult()
    data class SaveFailed(val error: Throwable) : ProcessingResult()
    
    val isSuccess: Boolean
        get() = this is Success
}