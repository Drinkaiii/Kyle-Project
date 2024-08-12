package com.example.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Variant {

    private int id;
    private int product_id;
    @JsonProperty("color_name")
    private String colorName;
    @JsonProperty("color_code")
    private String colorCode;

    private ArrayList<String> sizes;
    private ArrayList<Integer> stocks;

    private String size;
    private Integer stock;
}
