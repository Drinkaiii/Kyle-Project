package com.example.service.product;

import com.example.dto.product.ColorDto;
import com.example.dto.product.ProductDto;
import com.example.mapper.product.ProductMapper;
import com.example.dto.product.Result;
import com.example.dto.product.Variant;
import com.example.response.ErrorResponse;
import com.example.response.product.*;
import com.example.util.UploadS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UploadS3 fileSave;

    private final int pageShowProductNumber = 6;

    @Override
    public Object saveProduct(ProductDto productDto, MultipartFile file, List<MultipartFile> files) {

        //generate image url and save file
        productDto.setImageMainUrl(fileSave.oneFile(file));
        productDto.setImagesUrl(fileSave.multiFile(files));

        //save product data into database
        try {
            productMapper.setProductTable(productDto);
            productRegularization(productDto);
            productMapper.setColorTable(productDto);
            productMapper.setProductColorTable(productDto);
            productMapper.setProductSizeTable(productDto);
            productMapper.setImageTable(productDto);
            productMapper.setVariantTable(productDto);
            return generateProductResponse(new ArrayList<ProductDto>(Arrays.asList(productDto)));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("Something went wrong");
        }

    }

    @Override
    public Object showProductsPage(Integer paging) {
        //get target productDto objects
        int numberOfProduct = (int) productMapper.getProductTableCount().getData();
        int numberOfPage = (int) Math.ceil(numberOfProduct / (double) pageShowProductNumber);
        List<ProductDto> productDtos = (List<ProductDto>) productMapper.getProductTableByRow((paging - 0) * pageShowProductNumber, pageShowProductNumber).getData();
        //generate product list result
        if (numberOfPage - 1 == paging && paging >= 0) {
            ProductNoPagingResponse plngr = new ProductNoPagingResponse();
            plngr.setData(generateProductResponse(productDtos));
            return plngr;
        } else if (numberOfPage - 1 > paging && paging >= 0) {
            ProductWithPagingResponse plr = new ProductWithPagingResponse();
            plr.setData(generateProductResponse(productDtos));
            plr.setNextPaging(paging + 1);
            return plr;
        } else
            return ErrorResponse.error("No more products");
    }

    @Override
    public Object showProductsPageByCategory(Integer paging, String category) {

        if (!"all".equals(category)) {
            int categoryId = Integer.valueOf((String) productMapper.getCategoryTableByType(category).getData());
            //get target productDto objects
            int numberOfProduct = (int) productMapper.getProductTableCount(categoryId).getData();
            int numberOfPage = (int) Math.ceil(numberOfProduct / (double) pageShowProductNumber);
            List<ProductDto> productDtos = (List<ProductDto>) productMapper.getProductTableByRow((paging - 0) * pageShowProductNumber, pageShowProductNumber, categoryId).getData();
            //generate product list result
            if (numberOfPage - 1 == paging && paging >= 0) {
                ProductNoPagingResponse pngr = new ProductNoPagingResponse();
                pngr.setData(generateProductResponse(productDtos));
                return pngr;
            } else if (numberOfPage - 1 > paging && paging >= 0) {
                ProductWithPagingResponse pwpr = new ProductWithPagingResponse();
                pwpr.setData(generateProductResponse(productDtos));
                pwpr.setNextPaging(paging + 1);
                return pwpr;
            } else
                return ErrorResponse.error("No more products");
        } else
            return showProductsPage(paging);
    }

    @Override
    public Object showProductsByKeyword(Integer paging, String keyword) {
        if (paging == null || paging < 0)
            paging = 0;
        if (keyword != null && keyword.length() > 0) {
            //get proudcts which fit the condition
            List<ProductDto> productDtos = (List<ProductDto>) productMapper.getProductTableBySearchTitle((paging - 0) * pageShowProductNumber, pageShowProductNumber + 1, keyword).getData();
            if (productDtos.size() > pageShowProductNumber) {
                //delete the 7th product
                productDtos.remove(productDtos.size() - 1);
                ProductWithPagingResponse pwpr = new ProductWithPagingResponse();
                pwpr.setData(generateProductSearchResponse(productDtos));
                pwpr.setNextPaging(paging + 1);
                return pwpr;
            } else if (productDtos.size() > 0) {
                ProductNoPagingResponse pngr = new ProductNoPagingResponse();
                pngr.setData(generateProductSearchResponse(productDtos));
                return pngr;
            } else {
                return new ResponseEntity<>(ErrorResponse.error("No more products"), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(ErrorResponse.error("Keyword is null"), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Object showProductById(Integer id) {
        if (id != null && id > 0) {
            List<ProductDto> productDtos = (List<ProductDto>) productMapper.getProductTableById(id).getData();
            if (productDtos.size() > 0) {
                Map<String, Object> response = new HashMap<>();
                response.put("data", generateProductResponse(productDtos).get(0));
                return response;
            } else
                return new ResponseEntity<>(ErrorResponse.error("No product found for id: " + id), HttpStatus.BAD_REQUEST);
        } else
            return new ResponseEntity<>(ErrorResponse.error("The ID is incorrectly formatted or missing"), HttpStatus.BAD_REQUEST);
    }

    private void productRegularization(ProductDto productDto) {
        List<Variant> variants = productDto.getVariants();
        ArrayList<ColorDto> colorDtos = new ArrayList<>();
        HashSet<String> sizes = new HashSet<>();
        for (int i = 0; i < variants.size(); i++) {
            ColorDto colorDto = new ColorDto();
            colorDto.setCode(variants.get(i).getColorCode());
            colorDto.setName(variants.get(i).getColorName());
            colorDtos.add(colorDto);
            for (int j = 0; j < variants.get(i).getSizes().size(); j++)
                sizes.add(variants.get(i).getSizes().get(j));
        }
        ArrayList<String> sizes_lisst = new ArrayList<>(sizes);
        productDto.setColorDtos(colorDtos);
        productDto.setSizes(sizes_lisst);
    }

    private List<ProductResponse> generateProductResponse(List<ProductDto> productDtos) {
        List<ProductResponse> productResponses = new ArrayList<>();
        for (int i = 0; i < productDtos.size(); i++) {

            //some basic data
            ProductDto productDto = productDtos.get(i);
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(productDto.getId());
            productResponse.setCategory(productDto.getCategory());
            productResponse.setTitle(productDto.getTitle());
            productResponse.setDescription(productDto.getDescription());
            productResponse.setTexture(productDto.getTexture());
            productResponse.setPrice(productDto.getPrice());
            productResponse.setWash(productDto.getWash());
            productResponse.setPlace(productDto.getPlace());
            productResponse.setNote(productDto.getNote());
            productResponse.setStory(productDto.getStory());
            productResponse.setMainImage(productDto.getImageMainUrl());

            //from color and product_color table
            List<Integer> colorIds = (List<Integer>) productMapper.getProductColorTableByProductId(productDto.getId()).getData();
            List<ColorResponse> colorResponses = new ArrayList<>();
            for (Integer colorId : colorIds) {
                ColorResponse colorResponse = new ColorResponse();
                ColorDto colorDto = (ColorDto) productMapper.getColorTableById(colorId).getData();
                colorResponse.setCode(colorDto.getCode());
                colorResponse.setName(colorDto.getName());
                colorResponses.add(colorResponse);
            }
            productResponse.setColors(colorResponses);

            //from size and product_size table
            List<Integer> sizeIds = (List<Integer>) productMapper.getProductSizeTableByProductId(productDto.getId()).getData();
            List<String> sizes = new ArrayList<>();
            for (int j = 0; j < sizeIds.size(); j++) {
                sizes.add((String) productMapper.getSizeTableById(sizeIds.get(j)).getData());
            }
            productResponse.setSizes(sizes);

            //from variant table
            List<Variant> variants = (List<Variant>) productMapper.getVariantTableByProductId(productDto.getId()).getData();
            List<VariantResponse> variantResponses = new ArrayList<>();
            for (Variant variant : variants) {
                VariantResponse variantResponse = new VariantResponse();
                variantResponse.setColorCode(variant.getColorCode());
                String size = (String) productMapper.getSizeTableById(Integer.valueOf(variant.getSize())).getData();
                variantResponse.setSize(size);
                variantResponse.setStock(variant.getStock());
                variantResponses.add(variantResponse);
            }
            productResponse.setVariants(variantResponses);

            //from image table
            productResponse.setImages((List<String>) productMapper.getImageTableByProductId(productDto.getId()).getData());

            //convert code to text about category and place
            String categoryId = productDto.getCategory();
            String category = (String) productMapper.getCategoryTableById(Integer.valueOf(categoryId)).getData();
            productResponse.setCategory(category);
            String placeId = productDto.getPlace();
            String place = (String) productMapper.getPlaceTableById(Integer.valueOf(placeId)).getData();
            productResponse.setPlace(place);

            //add to list
            productResponses.add(productResponse);
        }

        return productResponses;
    }

    private List<ProductSearchResponse> generateProductSearchResponse(List<ProductDto> productDtos) {
        List<ProductSearchResponse> productSearchResponse = new ArrayList<>();
        for (int i = 0; i < productDtos.size(); i++) {

            //some basic data
            ProductDto productDto = productDtos.get(i);
            ProductSearchResponse productResponse = new ProductSearchResponse();
            productResponse.setId(productDto.getId());
            productResponse.setCategory(productDto.getCategory());
            productResponse.setTitle(productDto.getTitle());
            productResponse.setDescription(productDto.getDescription());
            productResponse.setTexture(productDto.getTexture());
            productResponse.setPrice(productDto.getPrice());
            productResponse.setWash(productDto.getWash());
            productResponse.setPlace(productDto.getPlace());
            productResponse.setNote(productDto.getNote());
            productResponse.setStory(productDto.getStory());
            productResponse.setMainImage(productDto.getImageMainUrl());

            //from color and product_color table
            List<Integer> colorIds = (List<Integer>) productMapper.getProductColorTableByProductId(productDto.getId()).getData();
            List<ColorResponse> colorResponses = new ArrayList<>();
            for (Integer colorId : colorIds) {
                ColorResponse colorResponse = new ColorResponse();
                ColorDto colorDto = (ColorDto) productMapper.getColorTableById(colorId).getData();
                colorResponse.setCode(colorDto.getCode());
                colorResponse.setName(colorDto.getName());
                colorResponses.add(colorResponse);
            }
            productResponse.setColors(colorResponses);

            //from size and product_size table
            List<Integer> sizeIds = (List<Integer>) productMapper.getProductSizeTableByProductId(productDto.getId()).getData();
            List<String> sizes = new ArrayList<>();
            for (int j = 0; j < sizeIds.size(); j++) {
                sizes.add((String) productMapper.getSizeTableById(sizeIds.get(j)).getData());
            }
            productResponse.setSizes(sizes);

            //from variant table
            List<Variant> variants = (List<Variant>) productMapper.getVariantTableByProductId(productDto.getId()).getData();
            List<VariantResponse> variantResponses = new ArrayList<>();
            for (Variant variant : variants) {
                VariantResponse variantResponse = new VariantResponse();
                variantResponse.setColorCode(variant.getColorCode());
                String size = (String) productMapper.getSizeTableById(Integer.valueOf(variant.getSize())).getData();
                variantResponse.setSize(size);
                variantResponse.setStock(variant.getStock());
                variantResponses.add(variantResponse);
            }
            productResponse.setVariants(variantResponses);

            //from image table
            productResponse.setImages((List<String>) productMapper.getImageTableByProductId(productDto.getId()).getData());

            //convert code to text about category and place
            String categoryId = productDto.getCategory();
            String category = (String) productMapper.getCategoryTableById(Integer.valueOf(categoryId)).getData();
            productResponse.setCategory(category);
            String placeId = productDto.getPlace();
            String place = (String) productMapper.getPlaceTableById(Integer.valueOf(placeId)).getData();
            productResponse.setPlace(place);

            //add to list
            productSearchResponse.add(productResponse);
        }

        return productSearchResponse;
    }


}
