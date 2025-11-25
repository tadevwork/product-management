package com.product.product_management.service

import com.product.product_management.dto.*
import com.product.product_management.entity.Product
import com.product.product_management.entity.Variant
import com.product.product_management.repository.ProductRepository
import com.product.product_management.repository.VariantRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val variantRepository: VariantRepository
) {
    
    fun findAll(): List<ProductDTO> {
        return productRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).map { p -> p.toDTO() }
    }
    
    @Transactional
    fun save(productToSave: ProductDTO) {
        val savedProduct = productRepository.save(productToSave.toEntity())

        val variantsToSave = productToSave.variants?.map { v -> v.toEntity(savedProduct) }.orEmpty()
        variantRepository.saveAll(variantsToSave)
    }

    fun importAll(products: List<ProductDTO>) {
        val allVariantsToSave = mutableListOf<Variant>()
        for (product in products) {
            val productToSave = product.toEntity()
            productToSave.id = null
            val savedProduct = productRepository.save(productToSave)
            val variantsToSave = product.variants?.map { v -> v.toEntity(savedProduct) }.orEmpty()
            allVariantsToSave.addAll(variantsToSave)
        }
        variantRepository.saveAll(allVariantsToSave)
    }

    fun search(keyword: String, productType: String?): List<ProductDTO> {
        val found = productRepository.findByTitleContainingIgnoreCaseAndProductType(keyword, productType)
        return found.map { it.toDTO() }
    }


    @Transactional
    fun deleteById(id: Long) {
        variantRepository.deleteAllByProductId(id)
        productRepository.deleteById(id)
    }


    fun Product.toDTO(): ProductDTO {
        return ProductDTO(
            id = this.id,
            title = this.title,
            handle = this.handle,
            vendor = this.vendor,
            productType = this.productType,
            variants = this.variants.map { v -> VariantDTO(
                id = v.id,
                title = v.title,
                option1 = v.option1,
                option2 = v.option2,
            )}
        )
    }

    fun ProductDTO.toEntity(): Product {
        return Product(
            id = this.id,
            title = this.title,
            handle = this.handle,
            vendor = this.vendor,
            productType = this.productType,
            variants = mutableListOf(),
            images = this.images
        )
    }

    fun VariantDTO.toEntity(product: Product): Variant {
        return Variant(
            id = null,
            title = this.title,
            option1 = this.option1,
            option2 = this.option2,
            option3 = this.option3,
            sku = this.sku,
            requiresShipping = this.requiresShipping,
            taxable = this.taxable,
            available = this.available,
            price = this.price,
            grams = this.grams,
            product = product,
            productId = product.id,
            featuredImage = this.featuredImage
        )
    }
} 