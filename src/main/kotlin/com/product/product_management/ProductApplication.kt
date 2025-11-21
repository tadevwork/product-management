package com.product.product_management

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class ProductApplication

fun main(args: Array<String>) {
    runApplication<com.product.product_management.ProductApplication>(*args)
}
