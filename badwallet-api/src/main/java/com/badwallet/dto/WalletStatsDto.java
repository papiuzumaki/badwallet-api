package com.badwallet.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WalletStatsDto {
    private String phoneNumber;
    private String code;
    private Double balance;
    private String currency;
    private long totalTransactions;
    private Double totalDepose;
    private Double totalRetire;
    private Double totalTransfere;
    private Double totalPaye;
}
