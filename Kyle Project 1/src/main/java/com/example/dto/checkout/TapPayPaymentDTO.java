package com.example.dto.checkout;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TapPayPaymentDTO {

    private String prime;
    @JsonProperty("partner_key")
    private String partnerKey;
    @JsonProperty("merchant_id")
    private String merchantId;
    private String details;
    private int amount;
    @JsonProperty("cardholder")
    private CardholderDto cardholderDto;
    private boolean remember;

}
