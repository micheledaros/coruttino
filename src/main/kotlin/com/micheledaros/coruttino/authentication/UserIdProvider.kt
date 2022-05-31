package com.micheledaros.coruttino.authentication

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.ReactorContext
import org.springframework.stereotype.Service

@Service
class UserIdProvider {

    suspend fun get() = coroutineScope {
        coroutineContext[ReactorContext]?.context?.get<UserContext>(UserContext)?.requestId
            ?: throw Exception("user id not specified in this context")
    }

}