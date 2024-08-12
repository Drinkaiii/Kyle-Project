package com.example.mapper.product;

import com.example.dto.product.ProductDto;
import com.example.dto.product.Result;

public interface ProductMapper {

    //save product information method

    Result setProductTable(ProductDto productDto);

    Result setColorTable(ProductDto productDto);

    Result setProductColorTable(ProductDto productDto);

    Result setProductSizeTable(ProductDto productDto);

    Result setImageTable(ProductDto productDto);

    Result setVariantTable(ProductDto productDto);

    //Get product information method

    Result getProductTableCount();

    Result getProductTableCount(int categoryId);

    Result getProductTableByRow(int startRow, int numberOfRows);//from number ZERO start

    Result getProductTableByRow(int startRow, int numberOfRows, int categoryId);

    Result getProductTableBySearchTitle(int startRow, int numberOfRows, String keyword);

    Result getProductTableById(int id);

    Result getProductColorTableByProductId(int productId);

    Result getColorTableById(int id);

    Result getProductSizeTableByProductId(int productId);

    Result getSizeTableById(int id);

    Result getCategoryTableById(int id);

    Result getCategoryTableByType(String type);

    Result getPlaceTableById(int id);

    Result getImageTableByProductId(int productId);

    Result getVariantTableByProductId(int productId);

}
