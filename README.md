Here's the cleaned up final version:

```markdown
# NanoBank — Account Service

Microservice 1 of 9 in NanoBank, a SARB-compliant distributed banking platform.

## What it does

- Create a bank account with an owner name and opening balance
- Deposit money into an account
- Withdraw money from an account (blocked if insufficient funds)
- Check balance and list all accounts

## Tech Stack

**Backend:** Java 21 + Spring Boot 4.1.0 + Spring Data JPA
**Database:** H2 (in-memory, resets on restart — Postgres coming in a later phase)
**Frontend:** HTML + CSS + Vanilla JavaScript (served directly by Spring Boot)

## Run it locally

```bash
git clone https://github.com/yottabyte-java/nanobank-account-service.git
cd nanobank-account-service
./mvnw spring-boot:run
```

Open `http://localhost:8080` in your browser.

## API

| Method | Path | Description | Response |
------------------------------------------------------------------------------------
| POST | /api/v1/accounts | Create account | 201 + created account |
| GET | /api/v1/accounts | List all accounts | 200 + array of accounts |
| GET | /api/v1/accounts/{id} | Get one account | 200 or 404 |
| POST | /api/v1/accounts/{id}/deposit | Deposit | 200 or 400 |
| POST | /api/v1/accounts/{id}/withdraw | Withdraw | 200, 400 insufficient funds |
