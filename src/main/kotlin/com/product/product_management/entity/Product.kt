package com.product.product_management.entity

import com.product.product_management.dto.ImageDTO
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val handle: String,

    @Column(nullable = false)
    val vendor: String,

    @Column(name = "product_type", nullable = false)
    val productType: String,

    @OneToMany(mappedBy = "product")
    val variants: MutableList<Variant> = mutableListOf(),

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "images", columnDefinition = "jsonb")
    val images: MutableList<ImageDTO> = mutableListOf()
)
