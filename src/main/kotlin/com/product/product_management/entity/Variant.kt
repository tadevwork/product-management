package com.product.product_management.entity

import com.product.product_management.dto.ImageDTO
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.math.BigDecimal

@Entity
@Table(name = "variants")
data class Variant(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "product_id")
    var productId: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, updatable = false, insertable = false)
    var product: Product? = null,

    val title: String? = null,
    val option1: String? = null,
    val option2: String? = null,
    val option3: String? = null,
    val sku: String? = null,
    val requiresShipping: Boolean? = null,
    val taxable: Boolean? = null,
    val available: Boolean? = null,
    val price: BigDecimal? = null,
    val grams: Int? = null,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "featured_image", columnDefinition = "jsonb")
    val featuredImage: ImageDTO? = null
)
