package com.product.product_management.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class CreateProductRequest(

    @field:NotBlank(message = "Title is required")
    val title: String,

    @field:NotBlank(message = "Handle is required")
    val handle: String,

    @field:NotBlank(message = "Vendor is required")
    val vendor: String,

    @field:NotBlank(message = "Product type is required")
    @JsonProperty("product_type")
    val productType: String,

    val variants: List<VariantDTO>? = emptyList(),

    @JsonProperty("images")
    val images: MutableList<ImageDTO> = mutableListOf()
)

fun CreateProductRequest.toDTO(): ProductDTO {
    return ProductDTO(
        title = this.title,
        handle = this.handle,
        vendor = this.vendor,
        productType = this.productType,
        variants = this.variants,
        images = this.images
    )
}


