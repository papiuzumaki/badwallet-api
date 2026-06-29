package com.badwallet.payment.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class PaymentResponse {
    private boolean success;
    private String message;
    private List<String> paidReferences;
    private Double totalAmount;
}
