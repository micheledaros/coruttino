package com.micheledaros.coruttino.controller.dto

import com.micheledaros.coruttino.model.Message
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

class MessageDto(
    val id: String,
    val receiverId: String,
    val text: String,
    val createdAt: LocalDateTime,
    var read: Boolean = false,
    var readAt: LocalDateTime? = null,

    )

class SendMessageRequestDto (val text: String)

class MarkMessageAsReadDto(val read: Boolean) {
    init {
        if (! read) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        }
    }
}

@Service
class MessageMapper {

    fun toDto(message: Message) = with(message) {
        MessageDto(
            id = this.id,
            receiverId = this.receiverId,
            createdAt = this.createdAt,
            read = this.read,
            readAt = this.readAt,
            text = this.text
        )
    }

}