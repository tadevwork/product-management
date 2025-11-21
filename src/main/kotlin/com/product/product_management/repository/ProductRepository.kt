package com.product.product_management.repository

import com.product.product_management.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    fun findByTitleContainingIgnoreCase(title: String): List<Product>
}