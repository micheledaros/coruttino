package com.micheledaros.coruttino.controller

import com.micheledaros.coruttino.controller.dto.MessageDto
import com.micheledaros.coruttino.model.Message
import org.springframework.stereotype.Service

@Service
class MessageMapper {

    fun toDto(message: Message) = with(message) {
        MessageDto(id = id, senderId = senderId, text = text)
    }

}