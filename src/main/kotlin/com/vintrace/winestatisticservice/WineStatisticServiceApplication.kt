package com.vintrace.winestatisticservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.vintrace")
class WineStatisticServiceApplication

fun main(args: Array<String>) {
	runApplication<WineStatisticServiceApplication>(*args)
}
