package com.jxitc.smsforward.utils

import android.telephony.SmsMessage
import android.util.Log
import com.jxitc.smsforward.domain.model.Message
import com.jxitc.smsforward.domain.model.MessageType
import com.jxitc.smsforward.domain.repository.MessageRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SmsParser @Inject constructor(
    private val messageRepository: MessageRepository
) {
    
    companion object {
        private const val TAG = "SmsParser"
    }
    
    suspend fun processSmsMessage(smsMessage: SmsMessage) {
        try {
            val message = Message(
                type = MessageType.SMS,
                sender = smsMessage.displayOriginatingAddress ?: "Unknown",
                content = smsMessage.messageBody ?: "",
                timestamp = smsMessage.timestampMillis
            )
            
            Log.d(TAG, "Processing SMS: ${message.sender} - ${message.content}")
            
            val messageId = messageRepository.insertMessage(message)
            Log.d(TAG, "SMS saved with ID: $messageId")
            
        } catch (e: Exception) {
            Log.e(TAG, "Error processing SMS message", e)
        }
    }
    
    fun extractSmsInfo(smsMessage: SmsMessage): SmsInfo {
        return SmsInfo(
            sender = smsMessage.displayOriginatingAddress ?: "Unknown",
            content = smsMessage.messageBody ?: "",
            timestamp = smsMessage.timestampMillis,
            serviceCenter = smsMessage.serviceCenterAddress
        )
    }
}

data class SmsInfo(
    val sender: String,
    val content: String,
    val timestamp: Long,
    val serviceCenter: String?
)