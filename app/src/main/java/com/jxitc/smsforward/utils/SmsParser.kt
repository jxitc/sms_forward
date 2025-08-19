package com.jxitc.smsforward.utils

import android.telephony.SmsMessage
import android.util.Log
import com.jxitc.smsforward.domain.usecase.MessageProcessorUseCase
import com.jxitc.smsforward.domain.usecase.ProcessingResult

class SmsParser {
    
    companion object {
        private const val TAG = "SmsParser"
    }
    
    suspend fun processSmsMessage(smsMessage: SmsMessage) {
        try {
            val sender = smsMessage.displayOriginatingAddress ?: "Unknown"
            val content = smsMessage.messageBody ?: ""
            val timestamp = smsMessage.timestampMillis
            
            Log.d(TAG, "Received SMS from $sender: ${content.take(50)}...")
            
            // TODO: Re-enable when Hilt is working
            // val result = messageProcessorUseCase.processIncomingSms(...)
            // For now, just log the SMS
            Log.d(TAG, "SMS processing temporarily disabled (no DI)")
            
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