package com.micheledaros.coruttino.service

import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TimeProvider {
    fun get() = LocalDateTime.now()
}