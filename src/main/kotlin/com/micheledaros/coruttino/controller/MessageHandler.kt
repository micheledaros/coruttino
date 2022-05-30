package com.micheledaros.coruttino.controller

import com.micheledaros.coruttino.controller.dto.MessageDto
import com.micheledaros.coruttino.controller.dto.SendMessageRequestDto
import com.micheledaros.coruttino.service.MessageService
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ServerWebExchange

@RestController
class MessageHandler(
    val messageService: MessageService,
    val messageMapper: MessageMapper) {

    @GetMapping("message")
    suspend fun getAll(): List<MessageDto> {
        return messageService.findAll().map { messageMapper.toDto(it) }
    }

    @PostMapping("/message/{receiver-id}")
    suspend fun writeMessage(
        @PathVariable("receiver-id") receiverId: String,
        @RequestBody requestBody: SendMessageRequestDto,
        exchange: ServerWebExchange
    ) {
        messageService.sendMessage(
            text = requestBody.text,
            receiverId = receiverId,
            senderId = exchange.request.headers["sender-id"]?.first()?:throw RuntimeException("sender-id header not found")
        )
    }

}