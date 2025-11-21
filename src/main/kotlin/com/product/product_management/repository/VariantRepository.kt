package com.product.product_management.repository

import com.product.product_management.entity.Variant
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VariantRepository : JpaRepository<Variant, Long> {
    fun deleteAllByProductId(productId: Long)
}