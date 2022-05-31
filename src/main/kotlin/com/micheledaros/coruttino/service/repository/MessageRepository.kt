package com.micheledaros.coruttino.service.repository

import com.micheledaros.coruttino.model.Message
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface MessageRepository: ReactiveMongoRepository<Message,String> {

    fun findAllByReceiverId(receiverId: String): Flux<Message>
    override fun findById(id: String): Mono<Message>
}
