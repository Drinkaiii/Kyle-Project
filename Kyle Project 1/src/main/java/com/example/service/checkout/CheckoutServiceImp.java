package com.example.service.checkout;

import com.example.dto.checkout.*;
import com.example.dto.user.UserDto;
import com.example.mapper.checkout.CheckoutMapper;
import com.example.response.ErrorResponse;
import com.example.util.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class CheckoutServiceImp implements CheckoutService {

    final private CheckoutMapper checkoutMapper;
    final private JwtUtil jwtUtil;

    @Override
    @Transactional
    public ResponseEntity sendOrder(CheckoutDto checkoutDto, String authorizationHeader) {
        //todo add try to catch null
        try {
            // set checkout table
            checkoutDto = checkoutMapper.setCheckout(checkoutDto);
            // set order_info table
            checkoutDto.getOrderDto().setCheckoutId(checkoutDto.getId());
            Map<String, Object> map = jwtUtil.getClaims(authorizationHeader.substring(7));
            checkoutDto.getOrderDto().setUserId((Integer) map.get("id"));
            checkoutDto = checkoutMapper.setOrder(checkoutDto);
            // set recipient table
            checkoutDto.getOrderDto().getRecipientDto().setOrderId(checkoutDto.getOrderDto().getId());
            checkoutDto = checkoutMapper.setRecipient(checkoutDto);
            // set product_list table
            for (CheckoutProductDto checkoutProductDto : checkoutDto.getOrderDto().getList()) {
                checkoutProductDto.setOrderId(checkoutDto.getOrderDto().getId());
            }
            checkoutDto = checkoutMapper.setList(checkoutDto);
        } catch (DataIntegrityViolationException | NullPointerException e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity<>(new ErrorResponse("input is wrong."), HttpStatus.BAD_REQUEST);
        }

        //send transaction to Tappay
        RecipientDto recipientDto = checkoutDto.getOrderDto().getRecipientDto();
        CardholderDto cardholderDto = CardholderDto.builder()
                .phoneNumber(recipientDto.getPhone())
                .name(recipientDto.getName())
                .email(recipientDto.getEmail())
                .build();

        TapPayPaymentDTO tapPayPaymentDTO = TapPayPaymentDTO.builder()
                .prime(checkoutDto.getPrime())
                .partnerKey("partner_ZMJtWBQOMQRnK5UdV1zZ3kA9I2Z80NCyDLiAZRJxOpCpdpG4VwmhXBps")
                .merchantId("kai410705_FUBON_POS_3")
                .details("TapPay Test")
                .amount(checkoutDto.getOrderDto().getTotal())
                .cardholderDto(cardholderDto)
                .remember(true)
                .build();


        String url = "https://sandbox.tappaysdk.com/tpc/payment/pay-by-prime";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", "partner_ZMJtWBQOMQRnK5UdV1zZ3kA9I2Z80NCyDLiAZRJxOpCpdpG4VwmhXBps");
        HttpEntity<TapPayPaymentDTO> request = new HttpEntity<>(tapPayPaymentDTO, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> resultMap = objectMapper.readValue(response.getBody(), Map.class);
            int responseStatus = (int) resultMap.get("status");

            if (responseStatus == 0) {
                // success
                checkoutMapper.confirmCheckoutStatus(checkoutDto, 1);
                Map<String, String> responseInnerMap = new HashMap<>();
                responseInnerMap.put("number", String.valueOf(checkoutDto.getOrderDto().getId()));
                Map<String, Object> responseOuterMap = new HashMap<>();
                responseOuterMap.put("data", responseInnerMap);
                return new ResponseEntity(responseOuterMap, HttpStatus.OK);
            } else {
                // failed
                checkoutMapper.confirmCheckoutStatus(checkoutDto, 2);
                return new ResponseEntity<>(new ErrorResponse("Payment verification process was unsuccessful"), HttpStatus.BAD_REQUEST);
            }
        } catch (JsonProcessingException e) {
            log.warn(response.getBody());
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse("The input is incorrect"), HttpStatus.BAD_REQUEST);
        }
    }
}
