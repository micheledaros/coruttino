package com.micheledaros.coruttino.service

import com.micheledaros.coruttino.model.Message
import com.micheledaros.coruttino.service.repository.MessageRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import java.util.*

@Service
class MessageService(
    val repository: MessageRepository
) {

    suspend fun findAll() =
        repository.findAll().asFlow().toList()


    suspend fun findOne(id: String) =
        repository.findById(id).awaitFirst()


    suspend fun sendMessage(text: String, senderId: String, receiverId: String) =
        Message(
            id = UUID.randomUUID().toString(),
            senderId = senderId,
            receiverId = receiverId,
            text = text
        )
            .let { repository.save(it) }
            .let { it.awaitSingle() }
}