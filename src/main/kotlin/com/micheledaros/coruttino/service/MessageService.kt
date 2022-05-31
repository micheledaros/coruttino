package com.micheledaros.coruttino.service

import com.micheledaros.coruttino.authentication.UserIdProvider
import com.micheledaros.coruttino.model.Message
import com.micheledaros.coruttino.service.repository.MessageRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import java.util.*

@Service
class MessageService(
    val repository: MessageRepository,
    val userIdProvider: UserIdProvider
) {

    suspend fun findAll(): List<Message> {
        val receiverId = userIdProvider.get()
        println(receiverId)
        return repository.findAllByReceiverId(receiverId).asFlow().toList()
    }


    suspend fun sendMessage(text: String, receiverId: String) =
        userIdProvider.get().also { senderId ->
            Message(
                id = UUID.randomUUID().toString(),
                receiverId = receiverId,
                text = text,
                senderId = senderId
            )
                .let { repository.save(it) }
                .let { it.awaitSingle() }
        }

}