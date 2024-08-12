package com.example.response.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

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
    private List<ColorResponse> colors;
    private List<String> sizes;
    private List<VariantResponse> variants;
    @JsonProperty("main_image")
    private String mainImage;
    @JsonProperty("images")
    private List<String> images;

}
