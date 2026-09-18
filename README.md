# Bank Account Management System (BAMS)

**Subject:** Programming in Java
**Student Name:** Ankit Pathak  
**Institution:** VIT, School of Computer Science and Engineering  

---

## What is this project?

BAMS is a robust, command-line banking system built in Java. It allows users and bank administrators to open savings and current accounts, perform deposits, withdrawals, and fund transfers, inspect account records, and export official statement reports — all backed by a persistent SQLite database.

---

## Menu Options

```
[1]  Open Savings Account        (Rs.1000 min balance, earns interest)
[2]  Open Current Account        (overdraft allowed, business use)
[3]  Check Account Details       (view balance, status, and details)
[4]  Deposit Money               (add funds to an account)
[5]  Withdraw Money              (withdraw funds with balance/overdraft validation)
[6]  Transfer Between Accounts   (atomic fund transfer between two accounts)
[7]  View Transaction History    (passbook view with optional statement export)
[8]  Close an Account            (deactivate an active account)
[9]  List All Accounts           (admin view - see every account)
[0]  Exit
```

---

## Project Structure

```
BAMS/
├── LAUNCH.bat                            <- Double-click launcher (auto-compiles and runs)
├── README.md                             <- Project documentation
├── statement.md                          <- Academic problem statement and scope
├── src/
│   └── com/bank/
│       ├── main/
│       │   └── BankApp.java              <- Entry point (console UI and menu routing)
│       ├── model/
│       │   ├── Account.java              <- Abstract base class
│       │   ├── SavingsAccount.java       <- Extends Account (min balance & interest)
│       │   ├── CurrentAccount.java       <- Extends Account (overdraft support)
│       │   ├── Transaction.java          <- Represents an immutable transaction record
│       │   └── TransactionType.java      <- Enum (DEPOSIT, WITHDRAWAL, TRANSFER_IN, etc.)
│       ├── exception/
│       │   ├── AccountNotFoundException.java
│       │   ├── InsufficientBalanceException.java
│       │   ├── InvalidAmountException.java
│       │   └── OverdraftLimitExceededException.java
│       ├── service/
│       │   ├── AccountOperations.java    <- Core banking interface
│       │   └── BankService.java          <- Business logic, cache, & concurrency control
│       ├── dao/
│       │   ├── AccountDAO.java           <- JDBC database operations for accounts
│       │   ├── TransactionDAO.java       <- JDBC database operations for transactions
│       │   └── DBConnection.java         <- SQLite database connection and schema initialization
│       └── util/
│           ├── ConsoleUtils.java         <- Scanner & input validation helper
│           └── StatementGenerator.java   <- File I/O statement generator (.txt exporter)
├── lib/
│   ├── sqlite-jdbc-3.45.1.0.jar          <- SQLite JDBC Driver
│   ├── slf4j-api-1.7.36.jar              <- Logging API dependency
│   └── slf4j-simple-1.7.36.jar           <- Logger implementation
├── bin/                                  <- Compiled Java bytecode (.class files)
├── data/                                 <- SQLite database file storage (bank.db)
└── statements/                           <- Generated account statement files (.txt)
```

---

## How to Run

### Automatic (Recommended)
Just **double-click `LAUNCH.bat`** — it configures the environment, compiles all source packages, and launches the application.

### Manual via Terminal
Open a terminal inside the `BAMS` folder and run:

**Step 1 – Compile:**
```bash
javac -cp "lib\*" -d bin src\com\bank\model\*.java src\com\bank\exception\*.java src\com\bank\dao\*.java src\com\bank\service\*.java src\com\bank\util\*.java src\com\bank\main\*.java
```

**Step 2 – Run:**
```bash
java -cp "bin;lib\*" com.bank.main.BankApp
```

---

## Account Number Format

| Type | Format | Example | Description |
|------|--------|---------|-------------|
| **Savings** | `SB` + 4 digits | `SB1001`, `SB1002` | Minimum balance Rs. 1,000; earns interest |
| **Current** | `CA` + 4 digits | `CA2001`, `CA2002` | Overdraft facility enabled for businesses |

---

## Java Concepts Used

| Concept | Location in Code | Purpose |
|---------|------------------|---------|
| **Abstract Class** | `Account.java` | Base template enforcing common account properties and abstract behaviors |
| **Inheritance** | `SavingsAccount`, `CurrentAccount` extend `Account` | Specialized withdrawal logic and interest/charge computation |
| **Polymorphism** | `displayAccountDetails()`, `withdraw()` | Dynamic method dispatch based on runtime account type |
| **Interfaces** | `AccountOperations.java` | Decouples business contracts from `BankService` implementation |
| **Custom Exceptions** | `exception/` package | Specific, descriptive error handling (`AccountNotFoundException`, etc.) |
| **Exception Handling** | `try-catch-finally`, `try-with-resources` | Resource safety and graceful error recovery across the app |
| **Thread Synchronization** | `synchronized` methods & lock ordering | Prevents race conditions and deadlocks during concurrent transfers |
| **Concurrency Utilities** | `ConcurrentHashMap`, `AtomicInteger` | Fast thread-safe caching and unique transaction ID generation |
| **Collections Framework** | `List`, `ArrayList`, `Map` | In-memory data management and ledger sorting |
| **Database Persistence (JDBC)** | `AccountDAO`, `TransactionDAO`, `DBConnection` | Relational storage with `PreparedStatement` and SQL transactions |
| **File I/O & Character Streams** | `StatementGenerator.java` | Chained `FileWriter` -> `BufferedWriter` -> `PrintWriter` for statements |
| **Enums** | `TransactionType.java` | Type-safe financial transaction classifications |
| **User Input Sanitization** | `ConsoleUtils.java` | Clean parsing and validation using `Scanner` |
