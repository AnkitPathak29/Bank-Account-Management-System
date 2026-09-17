# Problem Statement – Bank Account Management System (BAMS)

**Course:** Programming in Java (2nd Year B.Tech)  
**Project Type:** Console-based Java Application  

---

## 1. Problem Statement

Managing bank accounts manually is slow and error-prone. This project builds a simple digital banking system in Java where you can:

- Open savings and current accounts
- Deposit and withdraw money
- Transfer money between accounts
- View all past transactions
- Close accounts

The system makes sure data is never lost (saved to a database), invalid inputs are handled properly (custom exceptions), and multiple transactions don't mess up the balance (thread-safe code).

---

## 2. Scope of the Project

This project covers the following banking operations:

1. **Account Management**
   - Open a Savings Account (must keep Rs.1000 minimum balance, earns interest)
   - Open a Current Account (can go below zero up to an overdraft limit)
   - View account details and close accounts

2. **Transactions**
   - Deposit money into an account
   - Withdraw money (with balance validation)
   - Transfer money from one account to another

3. **Transaction History**
   - View all past transactions for any account (passbook view)

4. **Data Storage**
   - All accounts and transactions are saved to a database using JDBC
   - Data is not lost when the app is closed and reopened

---

## 3. Who Uses This System?

- **Bank staff**: Open accounts, do deposits/withdrawals, view all accounts
- **Customers**: Check balance, view transaction history
- **Students/Evaluators**: See how Java concepts like OOP, exceptions, threads, and JDBC work in a real project

---

## 4. Key Features

- **OOP Design**: Abstract `Account` class extended by `SavingsAccount` and `CurrentAccount`
- **Custom Exceptions**: Clear error messages when something goes wrong (e.g., insufficient balance, account not found)
- **Thread Safety**: Synchronized methods prevent balance corruption during concurrent access
- **SQLite Database**: Data is saved automatically, no manual setup needed
- **Simple Menu Interface**: Easy-to-use numbered menu, input is validated so the app never crashes on bad input
