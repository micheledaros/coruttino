package com.micheledaros.coruttino.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
class Message(
    @Id
    val id: String,
    val senderId: String,
    val receiverId: String,
    val text: String,
    val createdAt: LocalDateTime,
    var read: Boolean = false,
    var readAt: LocalDateTime? = null

) {
    fun markAsRead (now: LocalDateTime) {
        read = true
        readAt = now
    }
}