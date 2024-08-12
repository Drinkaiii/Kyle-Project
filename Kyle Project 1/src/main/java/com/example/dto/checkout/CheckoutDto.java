package com.example.dto.checkout;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckoutDto {

    private int id;
    private String prime;
    @JsonAlias("order")
    private OrderDto orderDto;

}
