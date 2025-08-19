package com.jxitc.smsforward.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.jxitc.smsforward.utils.SmsParser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SmsReceiver : BroadcastReceiver() {
    
    @Inject
    lateinit var smsParser: SmsParser
    
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            
            smsMessages?.forEach { smsMessage ->
                coroutineScope.launch {
                    smsParser.processSmsMessage(smsMessage)
                }
            }
        }
    }
}