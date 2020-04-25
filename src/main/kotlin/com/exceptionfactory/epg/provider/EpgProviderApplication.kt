package com.exceptionfactory.epg.provider

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootApplication
class EpgProviderApplication

fun main(args: Array<String>) {
    runApplication<EpgProviderApplication>(*args)
}
