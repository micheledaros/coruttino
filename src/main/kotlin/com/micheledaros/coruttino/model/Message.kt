package com.micheledaros.coruttino.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Message(
    @Id
    val id: String,
    val senderId: String,
    val receiverId: String,
    val text: String,
    var read: Boolean = false
) {
    fun notifyRead () {
        read = true
    }
}