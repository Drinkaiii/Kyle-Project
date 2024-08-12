package com.example.response.product;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"data", "next_paging"})
public class ProductWithPagingResponse<T> {

    private List<T> data;
    @JsonProperty("next_paging")
    private int nextPaging;

}
