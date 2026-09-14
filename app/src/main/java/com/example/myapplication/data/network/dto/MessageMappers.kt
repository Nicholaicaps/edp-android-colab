package com.example.myapplication.data.network.dto

import com.example.myapplication.data.local.MessageEntity
import com.example.myapplication.domain.Message
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.longOrNull
import java.time.Instant

fun MessageDto.toDomain(): Message = Message(
    id = id ?: "",
    sender = sender ?: "Unknown",
    text = text ?: "",
    createdAt = createdAt.toLongSafe()
)

fun List<MessageDto>.toDomain(): List<Message> =
    map { it.toDomain() }

fun Message.toEntity(): MessageEntity = MessageEntity(
    id = id,
    sender = sender,
    text = text,
    createdAt = createdAt
)

fun MessageEntity.toDomain(): Message = Message(
    id = id,
    sender = sender,
    text = text,
    createdAt = createdAt
)

private fun JsonElement?.toLongSafe(): Long {
    val primitive = this as? JsonPrimitive ?: return 0L
    return primitive.longOrNull 
        ?: primitive.content.toLongOrNull() 
        ?: try { Instant.parse(primitive.content).toEpochMilli() } catch (e: Exception) { 0L }
}
