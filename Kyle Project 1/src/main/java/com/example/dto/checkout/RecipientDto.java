package com.example.dto.checkout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipientDto {

    private int id;
    private int orderId;

    private String name;
    private String phone;
    private String email;
    private String address;
    private String time;

}
