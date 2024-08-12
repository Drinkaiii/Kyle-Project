package com.example.dto.checkout;

import com.example.dto.product.ColorDto;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckoutProductDto {

    private long id;
    private int orderId;

    private int productId;
    private String name;
    private int price;
    @JsonAlias("color")
    private ColorDto colorDto;
    private String size;
    private int qty;

    private int colorId;

}
