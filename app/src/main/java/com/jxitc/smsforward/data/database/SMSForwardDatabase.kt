package com.jxitc.smsforward.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(
    entities = [MessageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SMSForwardDatabase : RoomDatabase() {
    
    abstract fun messageDao(): MessageDao
    
    companion object {
        @Volatile
        private var INSTANCE: SMSForwardDatabase? = null
        
        fun getDatabase(context: Context): SMSForwardDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SMSForwardDatabase::class.java,
                    "sms_forward_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}