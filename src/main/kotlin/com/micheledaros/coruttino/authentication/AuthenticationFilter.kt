package com.micheledaros.coruttino.authentication

import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

private const val headerKey = "user-id"

@Component
class AuthenticationFilter : WebFilter {


    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val userId = exchange.request.headers[headerKey]?.firstOrNull()

        return if (userId != null) {
            chain.filter(exchange)
                .contextWrite {
                    it.put(UserContext, UserContext(userId))
                }
        } else {
            chain.filter(exchange)
        }
    }


}