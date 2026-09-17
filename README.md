# Bank Account Management System (BAMS)

**Subject:** Programming in Java | 2nd Year, Semester 3  
**Student Name:** Ankit Pathak  
**Institution:** VIT, School of Computer Science and Engineering  

---

## What is this project?

BAMS is a command-line banking system built entirely in Java. It lets you open bank accounts, do transactions, and view history — all saved to a real database that persists even after closing the app.

---

## Menu Options

```
[1]  Open Savings Account        (Rs.1000 min balance, earns interest)
[2]  Open Current Account        (overdraft allowed)
[3]  Check Account Details       (view balance and status)
[4]  Deposit Money
[5]  Withdraw Money
[6]  Transfer Between Accounts
[7]  View Transaction History
[8]  Close an Account
[9]  List All Accounts
[0]  Exit
```

---

## Project Structure

```
BAMS/
├── LAUNCH.bat                            <- Double-click to run
├── src/
│   └── com/bank/
│       ├── main/
│       │   └── BankApp.java              <- Entry point (main method)
│       ├── model/
│       │   ├── Account.java              <- Abstract base class
│       │   ├── SavingsAccount.java       <- Extends Account
│       │   ├── CurrentAccount.java       <- Extends Account
│       │   ├── Transaction.java          <- Represents one transaction
│       │   └── TransactionType.java      <- Enum (DEPOSIT, WITHDRAWAL, etc.)
│       ├── exception/
│       │   ├── AccountNotFoundException.java
│       │   ├── InsufficientBalanceException.java
│       │   ├── InvalidAmountException.java
│       │   └── OverdraftLimitExceededException.java
│       ├── service/
│       │   ├── AccountOperations.java    <- Interface
│       │   └── BankService.java          <- Business logic
│       ├── dao/
│       │   ├── AccountDAO.java           <- DB operations for accounts
│       │   ├── TransactionDAO.java       <- DB operations for transactions
│       │   └── DBConnection.java         <- SQLite connection
│       └── util/
│           └── ConsoleUtils.java         <- Input/output helper
├── lib/
│   ├── sqlite-jdbc-3.45.1.0.jar
│   ├── slf4j-api-1.7.36.jar
│   ├── slf4j-simple-1.7.36.jar
│   └── ecj.jar                           <- Bundled compiler (no JDK needed)
├── bin/                                  <- Compiled .class files
└── data/                                 <- SQLite database file
```

---

## How to Run

Just **double-click `LAUNCH.bat`** — it compiles and runs the app automatically.

To do it manually, open a terminal in the BAMS folder and run:

**Step 1 – Compile:**
```
java -jar lib\ecj.jar -8 -cp "lib\sqlite-jdbc-3.45.1.0.jar;lib\slf4j-api-1.7.36.jar;lib\slf4j-simple-1.7.36.jar" -d bin src\com\bank\model\*.java src\com\bank\exception\*.java src\com\bank\dao\*.java src\com\bank\service\*.java src\com\bank\util\*.java src\com\bank\main\*.java
```

**Step 2 – Run:**
```
java -cp "bin;lib\*" com.bank.main.BankApp
```

> Note: `ecj.jar` is used instead of `javac` because only JRE (not full JDK) is installed.

---

## Account Number Format

| Type | Format | Example |
|------|--------|---------|
| Savings | SB + number | SB1001, SB1002 |
| Current | CA + number | CA2001, CA2002 |

---

## Java Concepts Used

| Concept | Where |
|---------|-------|
| Abstract class | `Account.java` |
| Inheritance | `SavingsAccount`, `CurrentAccount` extend `Account` |
| Polymorphism | `displayAccountDetails()` called on Account, runs subclass version |
| Interface | `AccountOperations.java` implemented by `BankService` |
| Custom Exceptions | All 4 exception classes |
| try-catch-finally | Every transaction method |
| Synchronized methods | `deposit()`, `withdraw()` in Account |
| Collections (List, Map) | `BankService` uses `ConcurrentHashMap` + `ArrayList` |
| JDBC | `AccountDAO`, `TransactionDAO`, `DBConnection` |
| Enum | `TransactionType` |
| Scanner / Input | `ConsoleUtils.java` |
