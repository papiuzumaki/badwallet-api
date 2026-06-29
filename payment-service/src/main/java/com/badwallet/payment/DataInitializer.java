package com.badwallet.payment;

import com.badwallet.payment.model.Facture;
import com.badwallet.payment.repository.FactureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final FactureRepository factureRepository;

    @Override
    public void run(String... args) {
        List<Facture> factures = new ArrayList<>();
        String[] wallets = {"WLT-0000001", "WLT-0000002", "WLT-0000003"};
        String[] services = {"ISM", "WOYAFAL"};
        YearMonth current = YearMonth.now();

        for (String wallet : wallets) {
            int walletNum = Integer.parseInt(wallet.replace("WLT-", ""));
            for (int i = 0; i < 5; i++) {
                for (String service : services) {
                    factures.add(Facture.builder()
                            .reference("FAC-" + service + "-" + walletNum + "-" + (i + 1))
                            .walletCode(wallet)
                            .serviceName(service)
                            .unite(service)
                            .montant(service.equals("ISM") ? 5000.0 : 3000.0)
                            .payee(false)
                            .dateFacture(current.atDay(1).plusDays(i))
                            .mois(current.toString())
                            .build());
                }
            }
        }
        factureRepository.saveAll(factures);
    }
}
