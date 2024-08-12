package com.example.response.marketing;

import com.example.response.product.ProductResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotResponse {

    private String title;
    @JsonProperty("products")
    private List<ProductResponse> productResponses;

}
