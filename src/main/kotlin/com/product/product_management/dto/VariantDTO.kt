package com.product.product_management.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class VariantDTO(
    val id: Long? = null,
    val title: String? = null,
    val option1: String? = null,
    val option2: String? = null,
    val option3: String? = null,
    val sku: String? = null,
    val requiresShipping: Boolean? = null,
    val taxable: Boolean? = null,

    @JsonProperty("featured_image")
    val featuredImage: ImageDTO? = null,
    val available: Boolean? = null,
    val price: BigDecimal? = null,
    val grams: Int? = null,
    val productId: Long? = null
) 