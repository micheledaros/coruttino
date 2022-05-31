package com.micheledaros.coruttino.controller

import com.micheledaros.coruttino.authentication.UserContext
import com.micheledaros.coruttino.controller.dto.MessageDto
import com.micheledaros.coruttino.controller.dto.SendMessageRequestDto
import com.micheledaros.coruttino.service.MessageService
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ServerWebExchange

@RestController
class MessageHandler(
    val messageService: MessageService,
    val messageMapper: MessageMapper
) {

    @GetMapping("testEnrichContext")
    private suspend fun enrichContext(exchange: ServerWebExchange) {
        coroutineScope {
            withContext(coroutineContext.plus(UserContext("dummy"))) {

            }
        }
    }

    @GetMapping("message")
    suspend fun getAll(pageable: Pageable): List<MessageDto> {
          return messageService.findAll(pageable).map { messageMapper.toDto(it) }
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
        )
    }
}