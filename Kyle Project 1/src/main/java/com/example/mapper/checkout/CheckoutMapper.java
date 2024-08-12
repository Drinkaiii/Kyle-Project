package com.example.mapper.checkout;

import com.example.dto.checkout.CheckoutDto;
import com.example.dto.checkout.CheckoutProductDto;
import com.example.dto.checkout.OrderDto;
import com.example.dto.checkout.RecipientDto;
import org.springframework.http.ResponseEntity;

import javax.sound.midi.Receiver;

public interface CheckoutMapper {

    CheckoutDto setCheckout(CheckoutDto checkoutDto);

    CheckoutDto setOrder(CheckoutDto checkoutDto);

    CheckoutDto setRecipient(CheckoutDto checkoutDto);

    CheckoutDto setList(CheckoutDto checkoutDto);

    void confirmCheckoutStatus(CheckoutDto checkoutDto, int statusCode);

    CheckoutDto getCheckout(int checkoutId);

    OrderDto getOrder(int checkoutId);

    RecipientDto getRecipient(int orderId);

    CheckoutProductDto getList(int orderId);

}
