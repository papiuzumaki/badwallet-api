package com.badwallet.payment.repository;

import com.badwallet.payment.model.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FactureRepository extends JpaRepository<Facture, Long> {

    List<Facture> findByWalletCodeAndPayeeFalseAndDateFactureBetween(
            String walletCode, LocalDate debut, LocalDate fin);

    List<Facture> findByWalletCodeAndPayeeFalseAndUniteAndDateFactureBetween(
            String walletCode, String unite, LocalDate debut, LocalDate fin);

    List<Facture> findByWalletCodeAndPayeeFalseAndReferenceIn(
            String walletCode, List<String> references);

    List<Facture> findByWalletCodeAndPayeeFalseAndDateFactureBetweenAndServiceName(
            String walletCode, LocalDate debut, LocalDate fin, String serviceName);
}
