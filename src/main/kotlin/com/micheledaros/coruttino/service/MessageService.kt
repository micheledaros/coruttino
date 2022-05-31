package com.micheledaros.coruttino.service

import com.micheledaros.coruttino.authentication.UserIdProvider
import com.micheledaros.coruttino.model.Message
import com.micheledaros.coruttino.service.repository.MessageRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class MessageService(
    val repository: MessageRepository,
    val userIdProvider: UserIdProvider,
    val timeProvider: TimeProvider
) {

    suspend fun findAll(pageable: Pageable): List<Message> {
        val receiverId = userIdProvider.get()
        return repository.findAllByReceiverIdOrderByCreatedAtDesc(receiverId, pageable).asFlow().toList()
    }

    suspend fun sendMessage(text: String, receiverId: String): Message =
        Message(
            id = UUID.randomUUID().toString(),
            senderId = userIdProvider.get(),
            receiverId = receiverId,
            text = text,
            createdAt = timeProvider.get()
        )
            .let { repository.save(it) }
            .let { it.awaitSingle() }


    suspend fun markAsRead(id: String): Message =
        findOne(id)
            .also { message ->  message.markAsRead(timeProvider.get())}
            .let {  message -> repository.save(message)}
            .let { mono -> mono.awaitSingle() }

    public suspend fun findOne(id: String): Message {
        return userIdProvider.get().let { receiverId ->
            repository.findFirstByIdAndReceiverId(id = id, receiverId = receiverId)
                .awaitSingleOrNull()
                ?: throw Exception("message with id $id and receiverId $receiverId not found")
        }
    }


}

