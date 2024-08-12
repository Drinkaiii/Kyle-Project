package com.example.service.checkout;

import com.example.dto.checkout.CheckoutDto;
import com.example.dto.checkout.CheckoutProductDto;
import org.springframework.http.ResponseEntity;

public interface CheckoutService {

    ResponseEntity sendOrder(CheckoutDto checkoutDto, String authorizationHeader);


}
