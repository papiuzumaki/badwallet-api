package com.badwallet.payment.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;

@Entity
@Table(name = "factures")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;
    private String walletCode;
    private String serviceName; // ISM, WOYAFAL
    private String unite;
    private Double montant;
    private Boolean payee;
    private LocalDate dateFacture;
    private String mois;
}
