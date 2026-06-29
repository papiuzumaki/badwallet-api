# BadWallet API - Examen Design Pattern L3 S2 2026

## Architecture

Deux microservices Spring Boot :

- **badwallet-api** (port 8080) — Gestion des portefeuilles mobiles
- **payment-service** (port 8081) — Service externe de paiement de factures

## Design Patterns implémentés

| Pattern | Localisation | Description |
|---|---|---|
| **Strategy** | `pattern/strategy/` | Stratégies de dépôt : `CREDIT_CARD`, `WALLET_TARGET` |
| **Factory** | `pattern/factory/` | Sélection dynamique de la stratégie de dépôt |
| **Proxy** | `pattern/proxy/` | Accès centralisé au payment-service |
| **Observer** | `pattern/observer/` | Événements async sur les opérations wallet |

## Lancer les projets

```bash
# Terminal 1 - payment-service
cd payment-service && ./mvnw spring-boot:run

# Terminal 2 - badwallet-api
cd badwallet-api && ./mvnw spring-boot:run
```

## Endpoints principaux (port 8080)

- `POST /api/wallets/seed` — Seeder la BDD (async)
- `POST /api/wallets` — Créer un wallet
- `GET /api/wallets?page=0&size=10` — Lister (paginé)
- `GET /api/wallets/{phone}` — Consulter par téléphone
- `GET /api/wallets/{phone}/balance` — Solde
- `POST /api/wallets/{id}/deposit` — Dépôt
- `POST /api/wallets/withdraw` — Retrait (frais 1%, plafonné 5000 XOF)
- `POST /api/wallets/transfer` — Transfert
- `POST /api/wallets/pay` — Payer facture mois courant
- `POST /api/wallets/pay-factures` — Payer factures spécifiques
- `GET /api/wallets/{phone}/transactions` — Historique

## Proxy API (port 8080 → 8081)

- `GET /api/external/factures/{code}/current`
- `GET /api/external/factures/{code}/current?unite=WOYAFAL`
- `GET /api/external/factures/{code}/periode?debut=...&fin=...`

## Stratégie Git (Feature Branching)

- `main` → production stable
- `develop` → intégration
- `feature/*` → une branche par endpoint
