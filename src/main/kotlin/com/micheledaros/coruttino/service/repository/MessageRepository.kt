package com.micheledaros.coruttino.service.repository

import com.micheledaros.coruttino.model.Message
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface MessageRepository: ReactiveMongoRepository<Message,String> {

    fun findAllByReceiverIdOrderByCreatedAtDesc(receiverId: String, pageable: Pageable): Flux<Message>
    override fun findById(id: String): Mono<Message>

    fun findFirstByIdAndReceiverId(id: String, receiverId: String): Mono<Message>

}
