package com.jxitc.smsforward.domain.usecase

import com.jxitc.smsforward.domain.model.Message
import com.jxitc.smsforward.domain.model.MessageType

class MessageValidationUseCase {
    
    fun validateMessage(message: Message): ValidationResult {
        val errors = mutableListOf<String>()
        
        // Validate sender
        if (message.sender.isBlank()) {
            errors.add("Sender cannot be empty")
        }
        
        // Validate content
        if (message.content.isBlank()) {
            errors.add("Message content cannot be empty")
        }
        
        // Validate timestamp
        if (message.timestamp <= 0) {
            errors.add("Invalid timestamp")
        }
        
        // Validate message length (SMS limit is typically 160 characters for single SMS)
        if (message.content.length > 1600) { // Allow for concatenated SMS
            errors.add("Message content too long")
        }
        
        return if (errors.isEmpty()) {
            ValidationResult.Valid
        } else {
            ValidationResult.Invalid(errors)
        }
    }
    
    fun sanitizeMessage(message: Message): Message {
        return message.copy(
            sender = sanitizeSender(message.sender),
            content = sanitizeContent(message.content)
        )
    }
    
    private fun sanitizeSender(sender: String): String {
        return sender.trim()
            .take(50) // Limit sender length
            .replace(Regex("[^\\w\\s+()-]"), "") // Allow only alphanumeric, spaces, and common phone chars
    }
    
    private fun sanitizeContent(content: String): String {
        return content.trim()
            .take(2000) // Reasonable limit for message content
            .replace(Regex("\\s+"), " ") // Normalize whitespace
    }
}

sealed class ValidationResult {
    object Valid : ValidationResult()
    data class Invalid(val errors: List<String>) : ValidationResult()
    
    val isValid: Boolean
        get() = this is Valid
}