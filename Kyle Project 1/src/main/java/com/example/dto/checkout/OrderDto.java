package com.example.dto.checkout;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    private int id;
    private int checkoutId;
    private int userId;

    private String shipping;
    private String payment;
    private int subtotal;
    private int freight;
    private int total;
    @JsonAlias("recipient")
    private RecipientDto recipientDto;
    private List<CheckoutProductDto> list;


}
