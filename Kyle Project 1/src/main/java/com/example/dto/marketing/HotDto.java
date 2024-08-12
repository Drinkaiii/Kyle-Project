package com.example.dto.marketing;

import com.example.response.product.ProductResponse;
import com.fasterxml.jackson.annotation.JsonAlias;
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
public class HotDto {

    private int id;
    private String title;
    @JsonAlias({"product_id"})
    private int productId;

}
