package com.product.product_management.service

import com.product.product_management.dto.*
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate

@Service
class FetchDataService(
    private val productService: ProductService
) {
    private val restTemplate = RestTemplate()
    
    companion object {
        private const val FETCH_PRODUCT_URL = "https://famme.no/products.json"
        private const val LIMIT_FETCH_PRODUCTS = 50
    }
    
    @Scheduled(
        initialDelayString = "\${import.initial-delay}",
        fixedDelayString = "\${import.fixed-delay}"
    )
    @Transactional
    fun fetchProducts() {
        val fetchProductsResult = restTemplate.getForObject(FETCH_PRODUCT_URL, FetchProductResponse::class.java)
        if (fetchProductsResult?.products == null) {
            return
        }
        
        val products = fetchProductsResult.products
        val limit = minOf(products.size, LIMIT_FETCH_PRODUCTS)
        val limitedProducts = products.take(limit)

        if (limitedProducts.isNotEmpty()) {
            productService.importAll(limitedProducts)
        }
    }
} 