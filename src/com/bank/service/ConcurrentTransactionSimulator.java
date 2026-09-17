package com.bank.service;

import com.bank.model.Account;

import java.util.ArrayList;
import java.util.List;

/*
 * ConcurrentTransactionSimulator.java
 *
 * Unit 3: Multithreading and Synchronization - DEMO
 *
 * This class exists only to demonstrate how Java threads work.
 * It creates 4 threads that all try to access the same bank account at the same time.
 *
 * Without synchronization, this would cause a "Race Condition":
 *   - Two threads read balance = 5000 at the same time
 *   - Thread A withdraws 2000 -> balance = 3000
 *   - Thread B also withdraws 2000 (using the old balance!) -> balance = 3000 again (wrong!)
 *   - Expected: 5000 - 2000 - 2000 = 1000, but we got 3000. DATA CORRUPTED.
 *
 * With synchronized methods (which we use in Account.java), only one thread
 * can access the balance at a time - so the above bug cannot happen.
 *
 * Thread concepts shown:
 *   - Creating threads using the Runnable interface (Unit 3)
 *   - Thread lifecycle: NEW -> RUNNABLE -> TIMED_WAITING -> TERMINATED
 *   - Thread.sleep() to simulate network delay
 *   - thread.start() to begin execution
 *   - thread.join() to wait for all threads to finish before showing results
 *   - Inner class implementing Runnable (private class inside another class)
 */
public class ConcurrentTransactionSimulator {

    private final BankService bankService;

    public ConcurrentTransactionSimulator(BankService bankService) {
        this.bankService = bankService;
    }

    /*
     * Unit 3: Inner Class implementing Runnable.
     *
     * TransactionWorker is a class that can be run as a thread.
     * It implements the Runnable interface which requires a run() method.
     * The run() method is the code that executes when the thread starts.
     *
     * Think of Runnable like a set of instructions given to a thread:
     * "Hey thread, when you start, do what's written in run()."
     */
    private class TransactionWorker implements Runnable {
        private final String threadName;      // label for identifying this thread in output
        private final String accountNumber;   // which account to operate on
        private final boolean isDeposit;      // true = deposit, false = withdrawal
        private final double amount;          // how much to deposit/withdraw
        private final long sleepDelayMs;      // simulated network delay in milliseconds

        public TransactionWorker(String threadName, String accountNumber,
                                 boolean isDeposit, double amount, long sleepDelayMs) {
            this.threadName = threadName;
            this.accountNumber = accountNumber;
            this.isDeposit = isDeposit;
            this.amount = amount;
            this.sleepDelayMs = sleepDelayMs;
        }

        /*
         * run() is called automatically when thread.start() is called.
         * This is where the actual work happens.
         * Thread.sleep() simulates a delay (like a slow network request).
         */
        @Override
        public void run() {
            try {
                System.out.printf("  [Thread: %-22s] -> Started! Simulating %d ms network delay...%n",
                                  threadName, sleepDelayMs);

                // Thread.sleep() puts this thread in TIMED_WAITING state (Unit 3: Thread lifecycle)
                Thread.sleep(sleepDelayMs);

                if (isDeposit) {
                    bankService.deposit(accountNumber, amount, "Simulated deposit by " + threadName);
                    System.out.printf("  [Thread: %-22s] [OK] Deposited Rs. %.2f successfully%n",
                                      threadName, amount);
                } else {
                    bankService.withdraw(accountNumber, amount, "Simulated withdrawal by " + threadName);
                    System.out.printf("  [Thread: %-22s] [OK] Withdrew Rs. %.2f successfully%n",
                                      threadName, amount);
                }

            } catch (InterruptedException e) {
                // Thread was interrupted mid-sleep (Unit 3: Exception in threads)
                System.err.printf("  [Thread: %-22s] Thread was interrupted: %s%n", threadName, e.getMessage());
                Thread.currentThread().interrupt(); // restore the interrupted flag
            } catch (Exception e) {
                // Transaction was rejected (e.g., insufficient balance)
                System.out.printf("  [Thread: %-22s] [REJECTED] %s%n", threadName, e.getMessage());
            }
        }
    }

    /*
     * This method kicks off the demo.
     * It creates 4 threads, starts them all at the same time,
     * then waits for them all to finish using join().
     *
     * join() = "wait for this thread to finish before I continue"
     */
    public void runSimulation(String targetAccountNo) {
        System.out.println("\n================================================================================");
        System.out.println("            MULTITHREADING DEMO - Unit 3: Concurrent Transactions              ");
        System.out.println("================================================================================");

        try {
            Account target = bankService.getAccountDetails(targetAccountNo);
            double startingBalance = target.getBalance();

            System.out.printf("Target Account : %s  |  Holder: %s%n", target.getAccountNumber(), target.getHolderName());
            System.out.printf("Starting Balance: Rs. %.2f%n", startingBalance);
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("Launching 4 threads simultaneously on the same account:");
            System.out.println("  Thread 1 [ATM-Withdrawal]       -> Withdraws Rs. 3,000");
            System.out.println("  Thread 2 [UPI-Payment]          -> Withdraws Rs. 2,500");
            System.out.println("  Thread 3 [Salary-Credit]        -> Deposits  Rs. 6,000");
            System.out.println("  Thread 4 [Auto-Bill-Debit]      -> Withdraws Rs. 1,500");
            System.out.println("--------------------------------------------------------------------------------");

            // Unit 4: Using ArrayList to hold Thread objects (Collections + Multithreading)
            List<Thread> threads = new ArrayList<>();

            // Create thread objects - each wraps a TransactionWorker (Runnable)
            threads.add(new Thread(new TransactionWorker("ATM-Withdrawal",  targetAccountNo, false, 3000.0, 150)));
            threads.add(new Thread(new TransactionWorker("UPI-Payment",     targetAccountNo, false, 2500.0, 100)));
            threads.add(new Thread(new TransactionWorker("Salary-Credit",   targetAccountNo, true,  6000.0, 200)));
            threads.add(new Thread(new TransactionWorker("Auto-Bill-Debit", targetAccountNo, false, 1500.0,  80)));

            long startTime = System.currentTimeMillis();

            // Start all threads (Thread state changes: NEW -> RUNNABLE)
            for (Thread t : threads) {
                t.start();
            }

            // Wait for all threads to finish before printing results
            // join() blocks the main thread until each child thread is done
            for (Thread t : threads) {
                t.join();
            }

            long timeTaken = System.currentTimeMillis() - startTime;
            double finalBalance = target.getBalance();
            double expectedBalance = startingBalance - 3000.0 - 2500.0 + 6000.0 - 1500.0;

            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("RESULTS & THREAD SYNCHRONIZATION CHECK:");
            System.out.printf("  Starting Balance          : Rs. %.2f%n", startingBalance);
            System.out.printf("  Expected Final Balance    : Rs. %.2f%n", expectedBalance);
            System.out.printf("  Actual Final Balance      : Rs. %.2f%n", finalBalance);
            System.out.printf("  All threads finished in   : %d ms%n", timeTaken);

            // Verify no race condition happened (balances should match)
            if (Math.abs(finalBalance - expectedBalance) < 0.001) {
                System.out.println("\n  [PASS] Synchronization worked! No race condition. Balance is correct.");
                System.out.println("         The 'synchronized' keyword prevented data corruption.");
            } else {
                System.out.println("\n  [FAIL] Race condition detected! Balance is inconsistent. Bug!");
            }
            System.out.println("================================================================================\n");

        } catch (Exception e) {
            System.err.println("Error during threading demo: " + e.getMessage());
        }
    }
}
