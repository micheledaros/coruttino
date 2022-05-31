package com.micheledaros.coruttino.controller

import com.micheledaros.coruttino.authentication.UserContext
import com.micheledaros.coruttino.controller.dto.MarkMessageAsReadDto
import com.micheledaros.coruttino.controller.dto.MessageDto
import com.micheledaros.coruttino.controller.dto.MessageMapper
import com.micheledaros.coruttino.controller.dto.SendMessageRequestDto
import com.micheledaros.coruttino.service.MessageService
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ServerWebExchange

@RestController
class MessageController(
    val messageService: MessageService,
    val messageMapper: MessageMapper
) {

    @GetMapping("testEnrichContext")
    private suspend fun enrichContext(exchange: ServerWebExchange) {
        coroutineScope {
            withContext(coroutineContext.plus(UserContext("dummy"))) {
                println("I'm done here")
            }
        }
    }

    @GetMapping("/message")
    suspend fun getAll(pageable: Pageable): List<MessageDto> =
        messageService.findAll(pageable).map { messageMapper.toDto(it) }


    @PostMapping("/message")
    suspend fun writeMessage(
        @RequestParam("receiver-id") receiverId: String,
        @RequestBody requestBody: SendMessageRequestDto,
    ): MessageDto = messageService.sendMessage(
        text = requestBody.text,
        receiverId = receiverId,
    ).let { messageMapper.toDto(it) }


    @PutMapping("/message/{id}")
    suspend fun markMessageAsRead(
        @PathVariable("id") id: String,
        @RequestBody requestBody: MarkMessageAsReadDto
    ) =
        messageService.markAsRead(id = id)
            .let { messageMapper.toDto(it) }

}