package com.badwallet.payment.service;

import com.badwallet.payment.dto.PaymentRequest;
import com.badwallet.payment.dto.PaymentResponse;
import com.badwallet.payment.model.Facture;
import com.badwallet.payment.repository.FactureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FactureService {

    private final FactureRepository factureRepository;

    public List<Facture> getFacturesMoisCourant(String walletCode) {
        YearMonth current = YearMonth.now();
        LocalDate debut = current.atDay(1);
        LocalDate fin = current.atEndOfMonth();
        return factureRepository.findByWalletCodeAndPayeeFalseAndDateFactureBetween(walletCode, debut, fin);
    }

    public List<Facture> getFacturesMoisCourantByUnite(String walletCode, String unite) {
        YearMonth current = YearMonth.now();
        LocalDate debut = current.atDay(1);
        LocalDate fin = current.atEndOfMonth();
        return factureRepository.findByWalletCodeAndPayeeFalseAndUniteAndDateFactureBetween(walletCode, unite, debut, fin);
    }

    public List<Facture> getFacturesByPeriode(String walletCode, LocalDate debut, LocalDate fin) {
        return factureRepository.findByWalletCodeAndPayeeFalseAndDateFactureBetween(walletCode, debut, fin);
    }

    @Transactional
    public PaymentResponse payCurrentMonthBill(PaymentRequest request) {
        List<Facture> factures = getFacturesMoisCourant(request.getWalletCode());
        List<Facture> filtered = factures.stream()
                .filter(f -> f.getServiceName().equalsIgnoreCase(request.getServiceName()))
                .toList();
        return payFactures(filtered);
    }

    @Transactional
    public PaymentResponse paySpecificFactures(PaymentRequest request) {
        List<Facture> factures = factureRepository.findByWalletCodeAndPayeeFalseAndReferenceIn(
                request.getWalletCode(), request.getFactureReferences());
        return payFactures(factures);
    }

    private PaymentResponse payFactures(List<Facture> factures) {
        if (factures.isEmpty()) {
            return PaymentResponse.builder()
                    .success(false)
                    .message("Aucune facture impayée trouvée")
                    .build();
        }
        double total = factures.stream().mapToDouble(Facture::getMontant).sum();
        factures.forEach(f -> f.setPayee(true));
        factureRepository.saveAll(factures);
        List<String> refs = factures.stream().map(Facture::getReference).toList();
        return PaymentResponse.builder()
                .success(true)
                .message("Paiement effectué avec succès")
                .paidReferences(refs)
                .totalAmount(total)
                .build();
    }
}
