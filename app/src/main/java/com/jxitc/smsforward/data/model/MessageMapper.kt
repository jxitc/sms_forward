package com.jxitc.smsforward.data.model

import com.jxitc.smsforward.data.database.MessageEntity
import com.jxitc.smsforward.domain.model.Message
import com.jxitc.smsforward.domain.model.MessageType

object MessageMapper {
    
    fun toEntity(message: Message): MessageEntity {
        return MessageEntity(
            id = message.id,
            type = message.type.name,
            sender = message.sender,
            content = message.content,
            timestamp = message.timestamp,
            isForwarded = message.isForwarded,
            retryCount = message.retryCount
        )
    }
    
    fun toDomain(entity: MessageEntity): Message {
        return Message(
            id = entity.id,
            type = MessageType.valueOf(entity.type),
            sender = entity.sender,
            content = entity.content,
            timestamp = entity.timestamp,
            isForwarded = entity.isForwarded,
            retryCount = entity.retryCount
        )
    }
    
    fun toDomainList(entities: List<MessageEntity>): List<Message> {
        return entities.map { toDomain(it) }
    }
}