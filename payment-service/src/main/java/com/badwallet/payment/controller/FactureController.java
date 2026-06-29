package com.badwallet.payment.controller;

import com.badwallet.payment.dto.PaymentRequest;
import com.badwallet.payment.dto.PaymentResponse;
import com.badwallet.payment.model.Facture;
import com.badwallet.payment.service.FactureService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/factures")
@RequiredArgsConstructor
public class FactureController {

    private final FactureService factureService;

    @GetMapping("/{walletCode}/current")
    public ResponseEntity<List<Facture>> getCurrentMonthFactures(
            @PathVariable String walletCode,
            @RequestParam(required = false) String unite) {
        if (unite != null) {
            return ResponseEntity.ok(factureService.getFacturesMoisCourantByUnite(walletCode, unite));
        }
        return ResponseEntity.ok(factureService.getFacturesMoisCourant(walletCode));
    }

    @GetMapping("/{walletCode}/periode")
    public ResponseEntity<List<Facture>> getFacturesByPeriode(
            @PathVariable String walletCode,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(factureService.getFacturesByPeriode(walletCode, debut, fin));
    }

    @PostMapping("/pay/current")
    public ResponseEntity<PaymentResponse> payCurrentMonth(@RequestBody PaymentRequest request) {
        return ResponseEntity.ok(factureService.payCurrentMonthBill(request));
    }

    @PostMapping("/pay/specific")
    public ResponseEntity<PaymentResponse> paySpecific(@RequestBody PaymentRequest request) {
        return ResponseEntity.ok(factureService.paySpecificFactures(request));
    }
}
