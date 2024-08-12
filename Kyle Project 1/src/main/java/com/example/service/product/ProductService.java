package com.example.service.product;

import com.example.dto.product.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    Object saveProduct(ProductDto productDto, MultipartFile file, List<MultipartFile> files);

    Object showProductsPage(Integer paging);

    Object showProductsPageByCategory(Integer paging, String category);

    Object showProductsByKeyword(Integer paging, String keyword);

    Object showProductById(Integer id);

}
