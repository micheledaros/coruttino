package com.micheledaros.coruttino.authentication

import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

class UserContext(
    val requestId: String
) : AbstractCoroutineContextElement(UserContext) {
    companion object Key : CoroutineContext.Key<UserContext>
}