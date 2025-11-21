package com.product.product_management.dto

data class FetchProductResponse(
    var products: List<ProductDTO> = emptyList()
) 