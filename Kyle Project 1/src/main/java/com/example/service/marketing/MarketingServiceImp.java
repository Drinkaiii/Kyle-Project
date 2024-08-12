package com.example.service.marketing;

import com.example.dto.marketing.CampaignDto;
import com.example.dto.marketing.HotDto;
import com.example.dto.product.ColorDto;
import com.example.dto.product.ProductDto;
import com.example.dto.product.Variant;
import com.example.mapper.marketing.MarketingMapper;
import com.example.mapper.product.ProductMapper;
import com.example.response.ErrorResponse;
import com.example.response.marketing.CampaignResponse;
import com.example.response.marketing.HotResponse;
import com.example.response.product.ColorResponse;
import com.example.response.product.ProductResponse;
import com.example.response.product.VariantResponse;
import com.example.util.RedisUtil;
import com.example.util.UploadS3;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
@Log4j2
public class MarketingServiceImp implements MarketingService {

    final private MarketingMapper marketingMapper;
    final private ProductMapper productMapper;
    final private UploadS3 fileSave;
    final private RedisUtil redisUtil;

    @Override
    public Object setCampaign(CampaignDto campaignDto) {
        campaignDto.setPictureUrl(fileSave.oneFile(campaignDto.getPicture()));
        campaignDto = marketingMapper.setCampaign(campaignDto);
        CampaignResponse campaignResponse = generateCampaignResponse(campaignDto);
        redisUtil.delete("campaigns");
        return campaignResponse;
    }

    @Override
    public Object showAllCampaigns() {

        // get fata from Redis cache
        Object result = redisUtil.get("campaigns", List.class);
        if (result != null) {
            log.info("get campaigns data from Redis");
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("data", result);
            return resultMap;
        }
        // get data from MySLQ database
        List<CampaignDto> campaignDtos = marketingMapper.getAllCampaigns();
        if (campaignDtos != null) {
            List<CampaignResponse> campaignResponse = new ArrayList<>();
            for (CampaignDto campaignDto : campaignDtos)
                campaignResponse.add(generateCampaignResponse(campaignDto));
            Map<String, List> resultMap = new HashMap<>();
            resultMap.put("data", campaignResponse);
            // save data to Redis cache
            redisUtil.set("campaigns", campaignResponse);
            log.info("get campaigns data from Mysql");
            return resultMap;
        } else
            return new ResponseEntity<>(new ErrorResponse("No any campaign."), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public Object showAllHots() {
        List<HotDto> hotDtos = marketingMapper.getAllHots();
        if (hotDtos != null) {
            HotResponse hotResponse = new HotResponse();
            hotResponse.setTitle(hotDtos.get(0).getTitle());
            List<ProductResponse> productResponses = new ArrayList<>();
            for (HotDto hotDto : hotDtos) {
                ProductDto productDto = ((ArrayList<ProductDto>) productMapper.getProductTableById(hotDto.getProductId()).getData()).get(0);
                ProductResponse productResponse = generateProductResponse(productDto);
                productResponses.add(productResponse);
            }
            hotResponse.setProductResponses(productResponses);
            Map<String, HotResponse> resultMap = new HashMap<>();
            resultMap.put("data", hotResponse);
            return resultMap;
        } else
            return new ResponseEntity<>(new ErrorResponse("No any hots."), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private CampaignResponse generateCampaignResponse(CampaignDto campaignDto) {
        CampaignResponse campaignResponse = new CampaignResponse();
        campaignResponse.setProductId(campaignDto.getProductId());
        campaignResponse.setPicture(campaignDto.getPictureUrl());
        campaignResponse.setStory(campaignDto.getStory());
        return campaignResponse;
    }

    private ProductResponse generateProductResponse(ProductDto productDto) {
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

        return productResponse;
    }
}