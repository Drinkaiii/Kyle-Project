package com.example.controller;

import com.example.dto.product.ProductDto;
import com.example.response.ErrorResponse;
import com.example.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/1.0/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public Object createProduct(ProductDto productDto, @RequestParam("image_main_file") MultipartFile file, @RequestParam("images_file") ArrayList<MultipartFile> files) {
        return productService.saveProduct(productDto, file, files);
    }

    @GetMapping("/details")
    public Object getDetails(@RequestParam(required = false) Integer id) {
        return productService.showProductById(id);
    }

    @GetMapping("/search")
    public Object search(@RequestParam(required = false) String keyword, @RequestParam(required = false) Integer paging) {
        return productService.showProductsByKeyword(paging, keyword);
    }

    @GetMapping("/{category:^(?!details$|search$).*$}")
    public Object getProductList(@PathVariable("category") String category, @RequestParam(required = false) Integer paging) {
        if ("all".equalsIgnoreCase(category) || "women".equalsIgnoreCase(category) || "men".equalsIgnoreCase(category) || "accessories".equalsIgnoreCase(category))
            if (paging != null && paging >= 0)
                return productService.showProductsPageByCategory(paging, category);
            else
                return productService.showProductsPageByCategory(0, category);
        else
            return ErrorResponse.error("url was wrong.");
    }
}
