package com.jxitc.smsforward.di

import android.content.Context
import androidx.room.Room
import com.jxitc.smsforward.data.database.MessageDao
import com.jxitc.smsforward.data.database.SMSForwardDatabase
import com.jxitc.smsforward.data.repository.MessageRepositoryImpl
import com.jxitc.smsforward.domain.repository.MessageRepository
import dagger.Module
import dagger.Provides
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SMSForwardDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            SMSForwardDatabase::class.java,
            "sms_forward_database"
        )
        .fallbackToDestructiveMigration() // For MVP - will add proper migrations later
        .build()
    }
    
    @Provides
    fun provideMessageDao(database: SMSForwardDatabase): MessageDao {
        return database.messageDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    abstract fun bindMessageRepository(
        messageRepositoryImpl: MessageRepositoryImpl
    ): MessageRepository
}