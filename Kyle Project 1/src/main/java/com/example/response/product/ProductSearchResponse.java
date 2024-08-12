package com.example.response.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({
        "id",
        "category",
        "title",
        "description",
        "price",
        "texture",
        "wash",
        "place",
        "note",
        "story",
        "main_image",
        "images",
        "variants",
        "colors",
        "sizes"
})
public class ProductSearchResponse {

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
    @JsonProperty("main_image")
    private String mainImage;
    @JsonProperty("images")
    private List<String> images;
    private List<VariantResponse> variants;
    private List<ColorResponse> colors;
    private List<String> sizes;

}
