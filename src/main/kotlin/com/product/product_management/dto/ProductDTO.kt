package com.product.product_management.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ProductDTO(

    var id: Long? = null,

    var title: String,

    var handle: String,

    var vendor: String,

    @JsonProperty("product_type")
    var productType: String,

    val variants: List<VariantDTO>? = emptyList(),

    val images: MutableList<ImageDTO> = mutableListOf()
)
