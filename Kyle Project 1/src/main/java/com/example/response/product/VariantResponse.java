package com.example.response.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"color_code", "size", "stock"})
public class VariantResponse {
    @JsonProperty("color_code")
    private String colorCode;
    private String size;
    private int stock;
}
