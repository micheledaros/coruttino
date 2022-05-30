package com.micheledaros.coruttino

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CoruttinoApplication

fun main(args: Array<String>) {
	runApplication<CoruttinoApplication>(*args)
}
