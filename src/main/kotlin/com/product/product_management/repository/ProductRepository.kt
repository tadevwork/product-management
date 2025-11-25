package com.product.product_management.repository

import com.product.product_management.dto.ProductDTO
import com.product.product_management.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    fun findByTitleContainingIgnoreCase(title: String): List<Product>

    @Query("""
    SELECT p FROM Product p
    WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%'))
      AND (:productType IS NULL OR p.productType = :productType)
    """)
    fun findByTitleContainingIgnoreCaseAndProductType(
        title: String,
        productType: String?
    ): List<Product>

}