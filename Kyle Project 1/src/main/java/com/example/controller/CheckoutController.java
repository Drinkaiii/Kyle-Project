package com.example.controller;

import com.example.dto.checkout.CheckoutDto;
import com.example.dto.checkout.CheckoutProductDto;
import com.example.service.checkout.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/1.0/order")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping("/checkout")
    public ResponseEntity sendOrder(@RequestBody CheckoutDto checkoutDto, @RequestHeader("Authorization") String authorizationHeader) {
        return checkoutService.sendOrder(checkoutDto, authorizationHeader);
    }


}
