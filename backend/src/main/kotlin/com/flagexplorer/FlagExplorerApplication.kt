package com.flagexplorer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FlagExplorerApplication

fun main(args: Array<String>) {
    runApplication<FlagExplorerApplication>(*args)
} 