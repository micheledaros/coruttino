package com.micheledaros.coruttino.authentication

import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.ReactorContext
import org.springframework.stereotype.Service
import kotlin.coroutines.coroutineContext

@Service
class UserIdProvider {

    suspend fun get() = coroutineScope {
        coroutineContext[ReactorContext]?.context?.get<UserContext>(UserContext)?.requestId
            ?: throw Exception("user id not specified in this context")
    }

}