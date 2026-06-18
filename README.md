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


 POST 
 /api/v1/account/ **Create account**
 GET 
 /api/v1/accounts/  **List all accounts**
 GET 
 /api/v1/accounts/{id}/  **Get one account**
 POST 
 /api/v1/accounts/{id}/deposit/ **Deposit**
 POST 
 /api/v1/accounts/{id}/withdraw/** withdraw**
