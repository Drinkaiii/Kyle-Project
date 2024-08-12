package com.example.response.marketing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({"product_id", "picture","story"})
public class CampaignResponse {

    @JsonProperty("product_id")
    private int productId;
    private String picture;
    private String story;

}
