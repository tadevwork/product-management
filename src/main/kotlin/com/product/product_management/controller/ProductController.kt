package com.product.product_management.controller

import com.product.product_management.service.ProductService
import com.product.product_management.constant.ProductType
import com.product.product_management.dto.CreateProductRequest
import com.product.product_management.dto.toDTO
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import jakarta.validation.Valid
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class ProductController(
    private val productService: ProductService
) {
    @GetMapping("")
    fun home(model: Model): String {
        model.addAttribute("productTypes", ProductType.entries.toTypedArray())
        return "index"
    }

    @GetMapping("/products")
    fun loadProducts(model: Model): String {
        model.addAttribute("products", productService.findAll())
        model.addAttribute("productTypes", ProductType.entries.toTypedArray())
        return "index :: #product-table"
    }

    @PostMapping("/products")
    fun create(
        @Valid @ModelAttribute createProductRequest: CreateProductRequest,
        bindingResult: BindingResult,
        model: Model,
    ): String {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.allErrors.map { it.defaultMessage })
        }

        productService.save(createProductRequest.toDTO())
        model.addAttribute("products", productService.findAll())
        model.addAttribute("productTypes", ProductType.entries.toTypedArray())
        return "index :: #product-table"
    }

    @GetMapping("/products/search")
    fun searchProducts(
        @RequestParam(required = false) keyword: String = "",
        @RequestParam(required = false) productType: String?,
        model: Model
    ): String {
        val results = productService.search(keyword, productType)
        model.addAttribute("products", results)
        model.addAttribute("productTypes", ProductType.entries.toTypedArray())
        return "index :: #product-table"
    }


    @DeleteMapping("/products/{id}")
    fun deleteProduct(
        @PathVariable id: Long,
        model: Model
    ): String {
        productService.deleteById(id)
        model.addAttribute("products", productService.findAll())
        model.addAttribute("productTypes", ProductType.entries.toTypedArray())
        return "index :: #product-table"
    }

    @ResponseBody
    @GetMapping("/products/suggestions")
    fun suggestTitles(@RequestParam keyword: String): List<String> {
        if (keyword.isBlank()) return emptyList()
        val products = productService.search(keyword, null)
        return products.sortedBy { it.title }.map { it.title }.toSet().take(10)
    }
}
