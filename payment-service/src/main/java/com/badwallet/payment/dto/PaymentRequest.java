package com.badwallet.payment.dto;

import lombok.Data;
import java.util.List;

@Data
public class PaymentRequest {
    private String walletCode;
    private String serviceName;
    private Double amount;
    private List<String> factureReferences;
}
