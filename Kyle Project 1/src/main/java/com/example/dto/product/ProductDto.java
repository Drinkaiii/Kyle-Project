package com.example.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private int id;
    private String category;
    private String title;
    private String description;
    private int price;
    private String texture;
    private String wash;
    private String place;
    private String note;
    private String story;
    private List<ColorDto> colorDtos;
    private List<String> sizes;
    private List<Integer> stocks;
    private List<Variant> variants;
    @JsonProperty("image_main_url")
    private String imageMainUrl;
    @JsonProperty("images_url")
    private List<String> imagesUrl;

}
